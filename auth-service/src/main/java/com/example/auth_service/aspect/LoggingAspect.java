package com.example.auth_service.aspect;

import com.example.auth_service.dto.request.AuthRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Around("within(com.example.auth_service.service..*)")
    public Object logServiceDuration(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        long startTime = System.nanoTime();
        log.info("Mulai {} args={}", methodName, formatArguments(joinPoint.getArgs()));

        try {
            return joinPoint.proceed();
        } catch (Throwable exception) {
            // Log only the exception type to avoid leaking credentials from exception messages.
            log.error("Exception di {}: {}", methodName, exception.getClass().getSimpleName());
            throw exception;
        } finally {
            long durationMs = (System.nanoTime() - startTime) / 1_000_000;
            log.info("Selesai {} dalam {} ms", methodName, durationMs);
        }
    }

    private String formatArguments(Object[] arguments) {
        return Arrays.stream(arguments)
                .map(argument -> {
                    if (argument instanceof AuthRequestDto authRequest) {
                        return "AuthRequestDto(username=" + authRequest.getUsername() + ", password=***)";
                    }
                    return String.valueOf(argument);
                })
                .collect(Collectors.joining(", ", "[", "]"));
    }
}
