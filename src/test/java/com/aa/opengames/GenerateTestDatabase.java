package com.aa.opengames;

import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;

import org.junit.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GenerateTestDatabase {
  @Test
  public void generate() {
    assertThat("a", sameBeanAs("a"));
  }
}
