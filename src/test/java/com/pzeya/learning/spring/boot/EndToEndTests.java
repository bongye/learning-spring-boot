package com.pzeya.learning.spring.boot;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.interactions.Actions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EndToEndTests {
  static ChromeDriverService service;
  static ChromeDriver driver;

  @LocalServerPort int port;

  @BeforeClass
  public static void setUpBeforeClass() throws IOException {
    System.setProperty("webdriver.chrome.driver", "ext/chromedriver.exe");
    service = ChromeDriverService.createDefaultService();
    driver = new ChromeDriver(service);

    Path testResults = Paths.get("build", "test-results");
    if (!Files.exists(testResults)) {
      Files.createDirectories(testResults);
    }
  }

  @Test
  public void homePageShouldWork() throws IOException {
    driver.get("http://localhost:" + port);

    takeScreenShot("homePageSholudWork-1");
    assertThat(driver.getTitle()).isEqualTo("Learning Spring Boot: Spring-a-Gram");

    String pageContent = driver.getPageSource();

    assertThat(pageContent).contains("<a href=\"/images/bazinga.png/raw\"");
    WebElement element = driver.findElement(By.cssSelector("a[href*=\"bazinga.png\"]"));
    Actions actions = new Actions(driver);
    actions.moveToElement(element).click().perform();

    takeScreenShot("homePageShouldWork-2");
    driver.navigate().back();
  }

  private void takeScreenShot(String name) throws IOException {
    FileCopyUtils.copy(
        driver.getScreenshotAs(OutputType.FILE),
        new File("build/test-results/TEST-" + name + ".png"));
  }

  @AfterClass
  public static void tearDownAfterClass() {
    service.stop();
  }
}