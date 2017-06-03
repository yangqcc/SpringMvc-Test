package com.yqc.aoptest;

import org.springframework.stereotype.Component;

/**
 * Created by yangqc on 2017/6/3.
 */
@Component
public class TestBean {

    private String testStr = "testStr";

    public String getTestStr() {
        return testStr;
    }

    public void setTestStr(String testStr) {
        this.testStr = testStr;
    }

    public void test() {
        System.out.println("test");
    }
}
