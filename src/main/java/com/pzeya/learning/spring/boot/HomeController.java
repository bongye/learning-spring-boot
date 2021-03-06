package com.pzeya.learning.spring.boot;

import com.pzeya.learning.spring.boot.comments.CommentReaderRepository;
import com.pzeya.learning.spring.boot.images.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.HashMap;

@Controller
@Slf4j
public class HomeController {
  private static final String BASE_PATH = "/images";
  private static final String FILENAME = "{filename:.+}";
  private final ImageService imageService;
  private final CommentReaderRepository repository;

  public HomeController(ImageService imageService, CommentReaderRepository repository) {
    this.imageService = imageService;
    this.repository = repository;
  }

  @GetMapping(value = BASE_PATH + "/" + FILENAME + "/raw", produces = MediaType.IMAGE_JPEG_VALUE)
  @ResponseBody
  public Mono<ResponseEntity<?>> oneRawImage(@PathVariable String filename) {
    return imageService
        .findOneImage(filename)
        .map(
            resource -> {
              try {
                return ResponseEntity.ok()
                    .contentLength(resource.contentLength())
                    .body(new InputStreamResource(resource.getInputStream()));
              } catch (IOException e) {
                return ResponseEntity.badRequest()
                    .body("Couldn't find " + filename + " => " + e.getMessage());
              }
            });
  }

  @PostMapping(value = BASE_PATH)
  public Mono<String> createFile(@RequestPart(name = "file") Flux<FilePart> files) {
    return imageService.createImage(files).then(Mono.just("redirect:/"));
  }

  @DeleteMapping(BASE_PATH + "/" + FILENAME)
  public Mono<String> deleteFile(@PathVariable String filename) {
    return imageService.deleteImage(filename).then(Mono.just("redirect:/"));
  }

  @GetMapping("/")
  public Mono<String> index(Model model) {
    model.addAttribute(
        "images",
        imageService
            .findAllImages()
            .flatMap(
                image ->
                    Mono.just(image)
                        .zipWith(repository.findByImageId(image.getId()).collectList())
                        .map(
                            imageAndComments ->
                                new HashMap<String, Object>() {
                                  {
                                    put("id", imageAndComments.getT1().getId());
                                    put("name", imageAndComments.getT1().getName());
                                    put("comments", imageAndComments.getT2());
                                  }
                                })));
    model.addAttribute("extra", "DevTools can also detect code changes too");
    return Mono.just("index");
  }
}
