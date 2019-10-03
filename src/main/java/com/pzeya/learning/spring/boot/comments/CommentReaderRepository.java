package com.pzeya.learning.spring.boot.comments;

import org.springframework.data.repository.Repository;
import reactor.core.publisher.Flux;

public interface CommentReaderRepository extends Repository<Comment, String> {
  Flux<Comment> findByImageId(String imageId);
}
