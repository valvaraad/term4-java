package com.gustavo.labjava.service;

import java.util.concurrent.atomic.AtomicInteger;

public interface CounterService {
  static AtomicInteger requestCounter = new AtomicInteger(0);

  public void increment();

  public static int get() {
    return requestCounter.get();
  }
}
