package com.william.clientsrv.api;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api")
public class UserController {

    @RequestMapping("/userinfo")
    public ResponseEntity<List<UserInfo>> getAllUsers() {
        return ResponseEntity.ok(getUsers());
    }

    private List<UserInfo> getUsers() {
        List<UserInfo> users = new ArrayList<>();
        users.add(new UserInfo("bobo", "bobo@gmail.com"));
        users.add(new UserInfo("eric", "eric@gmail.com"));
        users.add(new UserInfo("franky", "franky@gmail.com"));
        return users;
    }

}