package com.first.flash.global.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.first.flash.account.auth.application.dto.CustomUserDetails;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Slf4j
@Component
@RequiredArgsConstructor
public class LoggingAspect {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ThreadLocal<Map<String, Object>> logContext = ThreadLocal.withInitial(
        HashMap::new);

    @PostConstruct
    public void setUp() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // ISO-8601 형식으로 출력
    }

    @AfterReturning(pointcut = "execution(* com.first.flash.account.auth.application.CustomUserDetailsService.loadUserByUsername(..))", returning = "userDetails")
    public void logUserDetails(final CustomUserDetails userDetails) {
        Map<String, Object> logData = logContext.get();
        logData.put("requester", userDetails.getNickName());
        logContext.set(logData);
    }

    @Before("com.first.flash.global.aspect.PointCuts.allController()")
    public void logRequestInfo(final JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        Map<String, Object> logData = logContext.get();
        logData.put("request URI", request.getRequestURI());
        logData.put("HTTP method", request.getMethod());
        logContext.set(logData);
    }

//    @Before("com.first.flash.global.aspect.PointCuts.allService() || com.first.flash.global.aspect.PointCuts.allRepository()")
//    public void logMethodDetails(final JoinPoint joinPoint) {
//        if (log.isDebugEnabled()) {
//            String key = generateLogKey(joinPoint);
//
//            Map<String, Object> methodDetails = new HashMap<>();
//            Object[] args = joinPoint.getArgs();
//            if (args.length > 0) {
//                methodDetails.put("params", args);
//            } else {
//                methodDetails.put("params", "no params");
//            }
//
//            updateLogContext(key, methodDetails);
//        }
//    }
//
//    @AfterReturning(pointcut = "com.first.flash.global.aspect.PointCuts.allService() || com.first.flash.global.aspect.PointCuts.allRepository()", returning = "result")
//    public void logMethodReturn(final JoinPoint joinPoint, final Object result) {
//        if (log.isDebugEnabled()) {
//            String key = generateLogKey(joinPoint);
//
//            Map<String, Object> methodDetails = new HashMap<>();
//            methodDetails.put("returnValue", result);
//
//            updateLogContext(key, methodDetails);
//        }
//    }

    @AfterReturning(pointcut = "com.first.flash.global.aspect.PointCuts.allController()", returning = "result")
    public void logResponse(final Object result) {
        Map<String, Object> logData = logContext.get();
        logData.put("type", "log");

        if (result instanceof ResponseEntity<?> responseEntity) {
            logData.put("response status", responseEntity.getStatusCode().value());
            logData.put("response body", responseEntity.getBody());
        } else {
            logData.put("response", result);
        }

        try {
            log.info(objectMapper.writeValueAsString(logData));
        } catch (Exception e) {
            log.error("Error converting log to JSON", e);
        } finally {
            logContext.remove();
        }
    }

    @AfterReturning(pointcut = "com.first.flash.global.aspect.PointCuts.allExceptionHandler()", returning = "exception")
    public void logException(final Object exception) {
        Map<String, Object> logData = logContext.get();
        logData.put("type", "error");

        if (exception instanceof ResponseEntity<?> exceptionResponse) {
            logData.put("error status", exceptionResponse.getStatusCode().value());
            logData.put("error body", exceptionResponse.getBody());
        } else {
            logData.put("error", exception);
        }

        try {
            log.info(objectMapper.writeValueAsString(logData));
        } catch (Exception e) {
            log.error("Error converting log to JSON", e);
        } finally {
            logContext.remove();
        }
    }

//    private String generateLogKey(final JoinPoint joinPoint) {
//        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//        String className = joinPoint.getTarget().getClass().getSimpleName();
//        String methodName = signature.getMethod().getName();
//        return className + "." + methodName;
//    }
//
//    private void updateLogContext(final String key, final Map<String, Object> methodDetails) {
//        Map<String, Object> logData = logContext.get();
//
//        if (logData.containsKey(key)) {
//            Map<String, Object> existingDetails = (Map<String, Object>) logData.get(key);
//            existingDetails.putAll(methodDetails);
//            logData.put(key, existingDetails);
//        } else {
//            logData.put(key, methodDetails);
//        }
//
//        logContext.set(logData);
//    }
}
