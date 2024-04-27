package com.gustavo.labjava.serviceimpl;

import com.gustavo.labjava.service.CounterService;
import org.springframework.stereotype.Service;

@Service
public class CounterServiceImpl implements CounterService {
  @Override
  public void increment() {
    requestCounter.incrementAndGet();
  }
}
