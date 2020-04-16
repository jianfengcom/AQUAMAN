package com.example.website.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @RequestMapping("ajax")
    public String hello(){
        return "ajax/ajax";
    }

    @RequestMapping("fuck")
    @ResponseBody
    public String fuck(){
        return "fuck";
    }
}
