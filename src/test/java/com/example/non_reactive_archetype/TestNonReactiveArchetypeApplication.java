package com.example.non_reactive_archetype;

import com.example.non_reactive_archetype.config.TestcontainersConfig;
import org.springframework.boot.SpringApplication;

public class TestNonReactiveArchetypeApplication {
  public static void main(String[] args) {
    SpringApplication.from(NonReactiveArchetypeApplication::main)
        .with(TestcontainersConfig.class)
        .run(args);
  }
}
