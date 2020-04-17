package com.example.website.web.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @RequestMapping("ajax")
    public String hello(){
        return "ajax/ajax";
    }
    @Value("${server.tomcat.prg:}")
    private String prg;

    @RequestMapping("fuck")
    @ResponseBody
    public String fuck(){
        System.out.println(prg);
        System.out.println("热部署 upupup");
        return "fuck";
    }
}
