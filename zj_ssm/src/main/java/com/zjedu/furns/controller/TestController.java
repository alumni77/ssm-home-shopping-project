package com.zjedu.furns.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {
    @RequestMapping(value = "/hi")
    public String hi() {
        System.out.println("ok...");
        return "hi";
    }
}
