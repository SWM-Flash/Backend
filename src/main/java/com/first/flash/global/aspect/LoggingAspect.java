package com.first.flash.global.aspect;

import com.first.flash.global.util.AuthUtil;
import jakarta.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
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

    @Before("com.first.flash.global.aspect.PointCuts.allController()")
    public void doBeforeController() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
            .getRequest();

        log.info("======= Controller Start =======");
        log.info("Request URI: {}", request.getRequestURI());
        log.info("HTTP Method: {}", request.getMethod());

        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            log.info("Header: {} = {}", headerName, request.getHeader(headerName));
        }

        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            log.info("Parameter: {} = {}", parameterName, request.getParameter(parameterName));
        }
    }

    @Before("com.first.flash.global.aspect.PointCuts.verifiedControllers()")
    public void doBeforeVerifiedController() {
        log.info("user id: {}", AuthUtil.getId());
    }

    @AfterReturning(value = "com.first.flash.global.aspect.PointCuts.allController()", returning = "result")
    public void afterReturnController(final Object result) {
        if (result instanceof ResponseEntity<?> responseEntity) {
            int statusCode = responseEntity.getStatusCode().value();
            Object responseBody = responseEntity.getBody();

            log.info("Response Status Code: {}", statusCode);
            if (!Objects.isNull(responseBody)) {
                log.info("Response Body: {}", responseBody);
            } else {
                log.info("no Response Body");
            }
        } else {
            if (!Objects.isNull(result)) {
                log.info("Response: {}", result);
            } else {
                log.info("no Response");
            }
        }
        log.info("======= Controller End =======");
    }

    @Before("com.first.flash.global.aspect.PointCuts.allService() || com.first.flash.global.aspect.PointCuts.allRepository()")
    public void doBefore(final JoinPoint joinPoint) {
        Method method = getMethod(joinPoint);
        String objectName = joinPoint.getTarget()
                                     .getClass()
                                     .getSimpleName();
        log.info("======= {} Start =======", objectName);
        log.info("method name = {}", method.getName());
        Object[] args = joinPoint.getArgs();
        if (Objects.isNull(args) || args.length == 0) {
            log.info("no parameter");
            return;
        }
        for (Object arg : args) {
            if (Objects.isNull(arg)) {
                continue;
            }
            log.info("parameter type = {}", arg.getClass().getSimpleName());
            log.info("parameter value = {}", arg);
        }
    }

    @AfterReturning(value = "com.first.flash.global.aspect.PointCuts.allService() || com.first.flash.global.aspect.PointCuts.allRepository()", returning = "result")
    public void afterReturnLog(final JoinPoint joinPoint, final Object result) {
        if (Objects.isNull(result)) {
            return;
        }
        log.info("return type = {}", result.getClass().getSimpleName());
        log.info("return value = {}", result);
        String objectName = joinPoint.getTarget()
                                     .getClass()
                                     .getSimpleName();
        log.info("======= {} End =======", objectName);
    }

    private Method getMethod(final JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getMethod();
    }
}
