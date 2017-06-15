package com.yqc.controller;

import com.yqc.config.SpringWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.socket.TextMessage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by yangqc on 2017/6/15.
 */
@Controller
public class WebsocketController {

    @Bean//这个注解会从Spring容器拿出Bean
    public SpringWebSocketHandler infoHandler() {
        return new SpringWebSocketHandler();
    }

    @RequestMapping("/websocket/login")
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String username = request.getParameter("username");
        username = "qicheng";
//        System.out.println(username + "登录");
        HttpSession session = request.getSession(false);
//        session.setAttribute("SESSION_USERNAME", username);
        //response.sendRedirect("/quicksand/jsp/websocket.jsp");
        return new ModelAndView("websocket");
    }

    @RequestMapping("/websocket/send")
    @ResponseBody
    public String send(HttpServletRequest request) throws InterruptedException {
        String username = request.getParameter("username");
        int a = 0;
        while (true) {
            Thread.sleep(1000);
            infoHandler().sendMessageToUser(username, new TextMessage("你好，测试！！！！"));
            if (a != 0) {
                break;
            }
        }
        return null;
    }
}
