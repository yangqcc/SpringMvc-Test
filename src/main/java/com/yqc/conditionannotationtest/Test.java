package com.yqc.conditionannotationtest;

import com.yqc.config.AopAutoConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by yangqc on 2017/6/4.
 */
public class Test {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AopAutoConfiguration.class);
        ListService service = context.getBean(ListService.class);
        System.out.println("操作符为:" + service.showListCmd() + ",系统为:" + context.getEnvironment().getProperty("os.name"));
        context.close();
    }
}
