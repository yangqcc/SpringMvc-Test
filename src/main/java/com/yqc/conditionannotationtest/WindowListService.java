package com.yqc.conditionannotationtest;

/**
 * Created by yangqc on 2017/6/4.
 */
public class WindowListService implements ListService {

    @Override
    public String showListCmd() {
        return "dir";
    }
}
