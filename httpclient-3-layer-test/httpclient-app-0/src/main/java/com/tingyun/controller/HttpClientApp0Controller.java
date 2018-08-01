package com.tingyun.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/HttpClientApp0Controller")
public class HttpClientApp0Controller {

    @RequestMapping("/HttpClientApp0Method")
    @ResponseBody
    public String HttpClientApp0Method(){
        System.out.println("0000000000000000000000000000000000000000000000000000000000000000000000");
        return "HttpClientApp0Controller-HttpClientApp0Method-return-message";
    }

}
