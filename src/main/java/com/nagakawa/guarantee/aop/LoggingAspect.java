package com.nagakawa.guarantee.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("within(com.nagakawa.guarantee.api.controller..*) || within(com.nagakawa.guarantee.service.impl..*)")
    public void logBeforeFunctionPointcut() {

    }

    @Before("logBeforeFunctionPointcut()")
    public void logBeforeFunctionAdvice(JoinPoint joinPoint) {
        logger.info("Class {}. Function {}() with argument[s] = {}", joinPoint.getTarget().getClass().getSimpleName(),
                joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
    }

}
