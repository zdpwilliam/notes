package com.william.news.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zdpwilliam on 2016-04-28.
 */
@RequestMapping("/")
@Controller("indexController")
public class Index {


    @RequestMapping("index")
    public String indexOfPage() {
        return "index";
    }



}
