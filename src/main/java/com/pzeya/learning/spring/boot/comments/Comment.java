package com.pzeya.learning.spring.boot.comments;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Comment {
  @Id private String id;
  private String imageId;
  private String comment;
}
