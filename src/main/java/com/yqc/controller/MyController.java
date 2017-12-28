package com.yqc.controller;

import com.yqc.annotation.Controller;
import com.yqc.annotation.Qualifier;
import com.yqc.annotation.RequestMapping;
import com.yqc.service.MyService;

/**
 * <p>title:</p>
 * <p>description:</p>
 *
 * @author yangqc
 * @date Created in 2017-12-27
 * @modified By yangqc
 */
@Controller
@RequestMapping("/mvc")
public class MyController {

  @Qualifier("myServiceImpl")
  private MyService myService;

  @RequestMapping("/eat")
  public String eat() {
    String result = myService.eat();
    System.out.println(result);
    return "this is eat!";
  }
}
