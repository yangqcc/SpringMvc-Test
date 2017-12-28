package com.yqc.service;

import com.yqc.annotation.Service;

/**
 * <p>title:</p>
 * <p>description:</p>
 *
 * @author yangqc
 * @date Created in 2017-12-27
 * @modified By yangqc
 */
@Service("myServiceImpl")
public class MyServiceImpl implements MyService {

  @Override
  public String eat() {
    System.out.println("this is service!");
    return "eat";
  }

  public static void main(String[] args) {
    String url="/mvc/eat";
    System.out.println(url.split("/")[1]);
  }
}
