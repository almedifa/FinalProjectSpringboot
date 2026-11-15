package com.example.scheduler_service.aspect;

import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Around("within(com.example.scheduler_service.service..*)")
    public Object logServiceDuration(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        long startTime = System.nanoTime();
        log.info("Mulai {} args={}", methodName, Arrays.toString(joinPoint.getArgs()));

        try {
            return joinPoint.proceed();
        } catch (Throwable exception) {
            log.error("Exception di {}: {}", methodName, exception.getClass().getSimpleName());
            throw exception;
        } finally {
            long durationMs = (System.nanoTime() - startTime) / 1_000_000;
            log.info("Selesai {} dalam {} ms", methodName, durationMs);
        }
    }
}
