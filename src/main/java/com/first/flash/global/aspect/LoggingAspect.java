package com.first.flash.global.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.first.flash.account.auth.application.dto.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
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

    @AfterReturning(pointcut = "execution(* com.first.flash.account.auth.application.CustomUserDetailsService.loadUserByUsername(..))", returning = "userDetails")
    public void logUserDetails(final CustomUserDetails userDetails) {
        Map<String, Object> logData = logContext.get();
        logData.put("Requester", userDetails.getNickName());
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

    @Before("com.first.flash.global.aspect.PointCuts.allService() || com.first.flash.global.aspect.PointCuts.allRepository()")
    public void logMethodDetails(final JoinPoint joinPoint) {
        if (log.isDebugEnabled()) {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            String className = joinPoint.getTarget().getClass().getSimpleName();

            Map<String, Object> logData = logContext.get();
            logData.put(className + " Method", method.getName());

            Object[] args = joinPoint.getArgs();
            if (args.length > 0) {
                logData.put("Method Parameters", args);
            } else {
                logData.put("Method Parameters", "No parameters");
            }
            logContext.set(logData);
        }
    }

    @AfterReturning(pointcut = "com.first.flash.global.aspect.PointCuts.allService() || com.first.flash.global.aspect.PointCuts.allRepository()", returning = "result")
    public void logMethodReturn(final JoinPoint joinPoint, final Object result) {
        if (log.isDebugEnabled()) {
            Map<String, Object> logData = logContext.get();
            logData.put("Method Return Value", result);
            logContext.set(logData);
        }
    }

    @AfterReturning(pointcut = "com.first.flash.global.aspect.PointCuts.allController()", returning = "result")
    public void logResponse(final Object result) {
        Map<String, Object> logData = logContext.get();

        if (result instanceof ResponseEntity<?> responseEntity) {
            logData.put("Response Status", responseEntity.getStatusCode().value());
            logData.put("Response Body", responseEntity.getBody());
        } else {
            logData.put("Response", result);
        }

        try {
            log.info("Unified Log: {}", objectMapper.writeValueAsString(logData));
        } catch (Exception e) {
            log.error("Error converting log to JSON", e);
        } finally {
            logContext.remove();
        }
    }

    // ExceptionHandler에서 예외 기록
    @AfterThrowing(pointcut = "execution(* com.first.flash..*ExceptionHandler.*(..))", throwing = "exception")
    public void logException(RuntimeException exception) {
        Map<String, Object> logData = logContext.get();

        logData.put("Error Class", exception.getClass().getSimpleName());
        logData.put("Error Message", exception.getMessage());

        try {
            log.error("Unified Log with Exception: {}", objectMapper.writeValueAsString(logData));
        } catch (Exception e) {
            log.error("Error converting log with exception to JSON", e);
        } finally {
            logContext.remove();
        }
    }

//    @Before("com.first.flash.global.aspect.PointCuts.allController()")
//    public void doBeforeController() {
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
//            .getRequest();
//
//        log.info("======= Controller Start =======");
//        log.info("Request URI: {}", request.getRequestURI());
//        log.info("HTTP Method: {}", request.getMethod());
//
//        Enumeration<String> headerNames = request.getHeaderNames();
//        while (headerNames.hasMoreElements()) {
//            String headerName = headerNames.nextElement();
//            log.info("Header: {} = {}", headerName, request.getHeader(headerName));
//        }
//
//        Enumeration<String> parameterNames = request.getParameterNames();
//        while (parameterNames.hasMoreElements()) {
//            String parameterName = parameterNames.nextElement();
//            log.info("Parameter: {} = {}", parameterName, request.getParameter(parameterName));
//        }
//    }
//
//    @Before("com.first.flash.global.aspect.PointCuts.verifiedControllers()")
//    public void doBeforeVerifiedController() {
//        log.info("user id: {}", AuthUtil.getId());
//    }
//
//    @AfterReturning(value = "com.first.flash.global.aspect.PointCuts.allController()", returning = "result")
//    public void afterReturnController(final Object result) {
//        if (result instanceof ResponseEntity<?> responseEntity) {
//            int statusCode = responseEntity.getStatusCode().value();
//            Object responseBody = responseEntity.getBody();
//
//            log.info("Response Status Code: {}", statusCode);
//            if (!Objects.isNull(responseBody)) {
//                log.info("Response Body: {}", responseBody);
//            } else {
//                log.info("no Response Body");
//            }
//        } else {
//            if (!Objects.isNull(result)) {
//                log.info("Response: {}", result);
//            } else {
//                log.info("no Response");
//            }
//        }
//        log.info("======= Controller End =======");
//    }
//
//    @Before("com.first.flash.global.aspect.PointCuts.allService() || com.first.flash.global.aspect.PointCuts.allRepository()")
//    public void doBefore(final JoinPoint joinPoint) {
//        Method method = getMethod(joinPoint);
//        String objectName = joinPoint.getTarget()
//                                     .getClass()
//                                     .getSimpleName();
//        log.info("======= {} Start =======", objectName);
//        log.info("method name = {}", method.getName());
//        Object[] args = joinPoint.getArgs();
//        if (Objects.isNull(args) || args.length == 0) {
//            log.info("no parameter");
//            return;
//        }
//        for (Object arg : args) {
//            if (Objects.isNull(arg)) {
//                continue;
//            }
//            log.info("parameter type = {}", arg.getClass().getSimpleName());
//            log.info("parameter value = {}", arg);
//        }
//    }
//
//    @AfterReturning(value = "com.first.flash.global.aspect.PointCuts.allService() || com.first.flash.global.aspect.PointCuts.allRepository()", returning = "result")
//    public void afterReturnLog(final JoinPoint joinPoint, final Object result) {
//        if (Objects.isNull(result)) {
//            return;
//        }
//        log.info("return type = {}", result.getClass().getSimpleName());
//        log.info("return value = {}", result);
//        String objectName = joinPoint.getTarget()
//                                     .getClass()
//                                     .getSimpleName();
//        log.info("======= {} End =======", objectName);
//    }
//
//    private Method getMethod(final JoinPoint joinPoint) {
//        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//        return signature.getMethod();
//    }
}
