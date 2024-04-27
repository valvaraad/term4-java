package com.gustavo.labjava.utils.cache;

import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public interface GenericCache<K, V> {

  void put(K key, V value);

  Optional<V> get(K key);

  void remove(K key);

  void clear();
}
