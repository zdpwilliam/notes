package com.william.controller;

import com.william.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by william.zhang on 2016/3/18.
 */
@EnableAutoConfiguration
@Controller("userController")
@RequestMapping("/*")
public class UserController {

    @Autowired
    private IUserService userService;

    @RequestMapping("/users")
    public ModelAndView getAllUser() {
        System.out.println("UserController------------------users");
        ModelAndView mav = new ModelAndView("users");
        mav.addObject("users", userService.getAllUsers());
        return mav;
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.GET)
    public String addUser() {
        return "addUser";
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.GET)
    public String updateUser() {
        return "updateUser";
    }
}
