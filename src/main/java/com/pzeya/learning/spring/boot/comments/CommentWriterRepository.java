package com.pzeya.learning.spring.boot.comments;

import org.springframework.data.repository.Repository;
import reactor.core.publisher.Mono;

public interface CommentWriterRepository extends Repository<Comment, String> {

  Mono<Comment> save(Comment newComment);

  Mono<Comment> findById(String id);
}
