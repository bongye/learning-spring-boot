package com.pzeya.learning.spring.boot.comments;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@EnableBinding(CustomProcessor.class)
public class CommentService {
  private CommentWriterRepository repository;
  private MeterRegistry meterRegistry;

  public CommentService(CommentWriterRepository repository, MeterRegistry meterRegistry) {
    this.repository = repository;
    this.meterRegistry = meterRegistry;
  }

  @StreamListener
  @Output(CustomProcessor.OUTPUT)
  public Flux<Void> save(@Input(CustomProcessor.INPUT) Flux<Comment> newComments) {
    return repository
        .saveAll(newComments)
        .flatMap(
            comment -> {
              meterRegistry
                  .counter("comments.consumed", "imageId", comment.getImageId())
                  .increment();
              return Mono.empty();
            });
  }

  //  @Bean
  //  CommandLineRunner setUp(MongoOperations operations) {
  //    return args -> {
  //      operations.dropCollection(Comment.class);
  //    };
  //  }
}
