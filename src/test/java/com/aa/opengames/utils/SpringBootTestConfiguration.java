package com.aa.opengames.utils;

import com.aa.opengames.event.EventSender;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class SpringBootTestConfiguration {

  @Bean
  @Primary
  public EventSender eventSender() {
    return Mockito.mock(EventSender.class);
  }

}
