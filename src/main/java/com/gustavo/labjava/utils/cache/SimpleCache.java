package com.gustavo.labjava.utils.cache;

import java.util.*;

public class SimpleCache<K, V> implements GenericCache<K, V> {

  private final HashMap<K, V> cache = new HashMap<>();

  private static final int MAX_SIZE = 100;

  @Override
  public void put(K key, V value) {
    if (cache.size() == MAX_SIZE) {
      clear();
    }
    cache.put(key, value);
  }

  @Override
  public Optional<V> get(K key) {
    return Optional.ofNullable(cache.get(key));
  }

  @Override
  public void remove(K key) {
    cache.remove(key);
  }

  @Override
  public void clear() {
    cache.clear();
  }
}
