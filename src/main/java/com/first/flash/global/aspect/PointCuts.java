package com.first.flash.global.aspect;

import org.aspectj.lang.annotation.Pointcut;

public class PointCuts {

    @Pointcut("execution(* com.first.flash..*Controller.*(..))")
    public void allController() {
    }

    @Pointcut("execution(* com.first.flash..*Service*.*(..))")
    public void allService() {
    }

    @Pointcut("execution(* com.first.flash..*Repository*.*(..))")
    public void allRepository() {
    }

    @Pointcut("execution(* com.first.flash.climbing..*Controller.*(..))")
    public void verifiedControllers() {

    }
}
