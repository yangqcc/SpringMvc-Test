package com.yqc.aoptest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

/**
 * Created by yangqc on 2017/6/3.
 */
@Aspect
@Service
public class AspectTest {

    @Pointcut("execution(* *.test(..))")
    public void test() {
        System.out.println("this is test!");
    }

    @Before("test()")
    public void beforeTest() {
    }

    @After("test()")
    public void afterTest() {
    }

    @Around("test()")
    public Object aroundTest(ProceedingJoinPoint p) {
        System.out.println("before!");
        Object object = null;
        try {
            object = p.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        System.out.println("after!");
        return object;
    }

    public static void main(String[] args) {
        ApplicationContext annotationContext = new ClassPathXmlApplicationContext("application.xml");
        TestBean test = (TestBean) annotationContext.getBean("test");
        test.test();
    }
}
