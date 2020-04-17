package com.example.web.controller;

import com.example.event.SendMsgEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    @Autowired
    private ApplicationContext ctx;

    @RequestMapping("fuck")
    @ResponseBody
    public String fuck() {
        System.out.println(2333);
        ctx.publishEvent(new SendMsgEvent(9527L, this));
        return "fuck";
    }

}
