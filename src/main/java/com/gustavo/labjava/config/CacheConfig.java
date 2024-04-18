package com.gustavo.labjava.config;

import com.gustavo.labjava.utils.cache.*;
import org.springframework.context.annotation.*;

@Configuration
public class CacheConfig<K, V> {

  @Bean
  @Scope("prototype")
  public GenericCache<K, V> cache() {
    return new SimpleCache<>();
  }
}
