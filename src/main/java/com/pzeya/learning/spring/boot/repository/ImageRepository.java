package com.pzeya.learning.spring.boot.repository;

import com.pzeya.learning.spring.boot.model.Image;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ImageRepository extends ReactiveCrudRepository<Image, String> {
  Mono<Image> findByName(String name);
}
