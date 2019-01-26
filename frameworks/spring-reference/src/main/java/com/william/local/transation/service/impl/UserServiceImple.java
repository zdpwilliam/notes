package com.william.local.transation.service.impl;

import com.william.local.transation.model.User;
import com.william.local.transation.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * Created by william.zhang on 2016/2/28.
 */
@Service("userService")
public class UserServiceImple implements IUserService{

    public void createUser(User user) {

        System.out.println("--------createUser : " + user);
    }
}
