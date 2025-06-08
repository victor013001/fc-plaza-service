package com.example.non_reactive_archetype;

import com.example.non_reactive_archetype.config.TestcontainersConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles
@Import(TestcontainersConfig.class)
class NonReactiveArchetypeApplicationTests {

  @Test
  void contextLoads() {
    // This method is intentionally left empty to verify that the Spring application context loads
  }
}
