package com.example.springsecuritydemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ Author     ：lichao.
 * @ Date       ：Created in 15:33 2019/6/27
 * @ Description：
 * @ Modified By：
 * @Version: $
 */
@Controller
public class BasicController {


    @RequestMapping(value = {"/","/home"})
    public String home(){
        return "home";
    }

    @RequestMapping(value = {"/hello"})
    public String hello(){
        return "hello";
    }

    @RequestMapping(value = {"/admin"})
    public String admin(){
        return "admin";
    }

    @RequestMapping(value = {"/user"})
    public String user(){
        return "user";
    }

    @RequestMapping(value = {"/vip"})
    public String vip(){
        return "vip";
    }
    @RequestMapping(value = {"/login"})
    public String login(){
        return "login";
    }

    @RequestMapping(value = {"/super"})
    public String superm(){
        return "super";
    }
    @RequestMapping(value = {"/deny"})
    public String deny(){
        return "deny";
    }
}
