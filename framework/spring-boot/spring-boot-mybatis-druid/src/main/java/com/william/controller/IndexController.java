package com.william.controller;

import com.william.dao.DruidRunningMapper;
import com.william.model.ADruidTmpModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by william on 17-7-24.
 */
@RequestMapping("/")
@RestController("indexController")
public class IndexController {

//    @Autowired
    private DruidRunningMapper druidRunningMapper;

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public ADruidTmpModel getDruidTmp(Integer id) {
        return druidRunningMapper.selectById(id);
    }
}
