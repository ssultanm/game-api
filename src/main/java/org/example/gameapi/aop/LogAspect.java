package org.example.gameapi.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class LogAspect {
    @Before("execution(* org.example.gameapi.service.*.*(..))")
    public void hi(JoinPoint yusif){
        log.info("метод вызван c помощью aop " + yusif.getSignature().getName());
    }
//
//    @After("execution(* org.example.gameapi.service.*.*(..))")
//    public void bye(JoinPoint jp){
//        log.info("метод закончился c помощью aop " + jp.getSignature().getName());
//    }
    @Around("execution(* org.example.gameapi.service.*.*(..))")
    public void beforeAndAfterMethod(ProceedingJoinPoint jp) throws Throwable {
        log.info("1" + jp.getSignature().getName());

        Object out=jp.proceed();
        log.info("2" + jp.getSignature().getName());
  }
}
