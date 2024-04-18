package com.gustavo.labjava.aspect;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class LoggingAspect {
  @SuppressWarnings("EmptyMethod")
  @Pointcut("@annotation(com.gustavo.labjava.aspect.Logger)")
  private void serviceLogging() {
  }

  @Around("serviceLogging()")
  public Object logging(ProceedingJoinPoint joinPoint) throws Throwable {
    String methodName = joinPoint.getSignature().getName();
    Object[] args = joinPoint.getArgs();
    log.info("Called method {}(); args: {}", methodName, args);
    try {
      Object output = joinPoint.proceed();
      log.info("Method {} is returned; value: {}", methodName, output);
      return output;
    } catch (Throwable exception) {
      log.error(
          "In the method {}() threw exception with message: {}",
          methodName,
          exception.getMessage());
      throw exception;
    }
  }

  @PostConstruct
  public void initAspect() {
    log.info("Aspect is initialized");
  }

  @PreDestroy
  public void destroyAspect() {
    log.info("Aspect is destroyed");
  }
}