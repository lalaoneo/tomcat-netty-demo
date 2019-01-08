package com.example.tomcatNetty.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/index")
public class IndexController {

    @GetMapping("/helloword")
    public String helloWord(){

        System.out.println("调用此接口当前时间："+System.currentTimeMillis());

        return "hello word";
    }
}
