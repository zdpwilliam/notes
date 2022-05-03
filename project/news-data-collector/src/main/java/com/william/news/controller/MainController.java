package com.william.news.controller;

import com.william.news.dao.ICategoryTagDao;
import com.william.news.dao.INewsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zdpwilliam on 2016-05-06.
 */
@Controller("mainController")
public class MainController {

    @Autowired
    private INewsDao newsDao;
    @Autowired
    private ICategoryTagDao categoryTagDao;

    @RequestMapping("/main")
    public String getMainPage() {
        return "cns-cms";
    }
}
