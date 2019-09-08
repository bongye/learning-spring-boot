package com.pzeya.learning.spring.boot.model;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class ImageTests {
  @Test
  public void imagesManagedByLombokShouldWork() {
    Image image = new Image("id", "file-name.jpg");
    Assertions.assertThat(image.getId()).isEqualTo("id");
    Assertions.assertThat(image.getName()).isEqualTo("file-name.jpg");
  }
}
