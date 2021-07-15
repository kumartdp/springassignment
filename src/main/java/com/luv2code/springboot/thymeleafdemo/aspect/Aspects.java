package com.luv2code.springboot.thymeleafdemo.aspect;

import com.luv2code.springboot.thymeleafdemo.entity.Employee;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

@Aspect
@Component
public class Aspects {

    private Logger logger = Logger.getLogger(getClass().getName());


    @Before("execution(* com.luv2code.springboot.thymeleafdemo.controller.*.*(..))")
    public void before(JoinPoint theJoinPoint) {


        String theMethod = theJoinPoint.getSignature().toShortString();
        logger.info("=====>> in @Before: calling method: " + theMethod);

        Object[] args = theJoinPoint.getArgs();
        for (Object tempArg : args) {
            logger.info("=====>> argument: " + tempArg);
        }
    }
    @Before("execution(* com.luv2code.springboot.thymeleafdemo.service.*.*.deleteById(..))")
    public void beforeDelete(){
        logger.info("\n\nDeleting Employee\n\n");
    }

    @AfterReturning(
            pointcut="execution(* com.luv2code.springboot.thymeleafdemo.service.*.*.find*(..))",
            returning="result")
    public void afterReturningFindAccountsAdvice(
            JoinPoint theJoinPoint, List<Employee> result) {



        String method = theJoinPoint.getSignature().toShortString();
        logger.info("\n=====>>> Executing @AfterReturning on method: " + method);
        logger.info("\n=====>>> result is: " + result);
    }
    @AfterReturning(
            pointcut ="execution(* com.luv2code.springboot.thymeleafdemo.service.*.*.save(..))", returning="result")
    public void afterAddingEmployee(
            JoinPoint theJoinPoint, List<Employee> result) {


        String method = theJoinPoint.getSignature().toShortString();

        logger.info("\n=====>>> Executing @AfterReturning on method: " + method);
        logger.info("\n=====>>> result is: " + result);
    }




}
