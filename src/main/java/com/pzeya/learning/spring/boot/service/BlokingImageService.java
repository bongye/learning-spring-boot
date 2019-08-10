package com.pzeya.learning.spring.boot.service;

import com.pzeya.learning.spring.boot.model.Image;
import org.springframework.core.io.Resource;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;

public class BlokingImageService {
  private final ImageService imageService;

  public BlokingImageService(ImageService imageService) {
    this.imageService = imageService;
  }

  public List<Image> findAllImages() {
    return imageService.findAllImages().collectList().block(Duration.ofSeconds(10));
  }

  public Resource findOneImage(String filename) {
    return imageService.findOneImage(filename).block(Duration.ofSeconds(30));
  }

  public void createImage(List<FilePart> files) {
    imageService.createImage(Flux.fromIterable(files)).block(Duration.ofMinutes(1));
  }
}
