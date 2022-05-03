package com.william.service;

import com.william.model.User;

import java.util.List;

/**
 * Created by william.zhang on 2016/3/18.
 */
public interface IUserService {

    public List<User> getAllUsers();

    public void updateUser(User user);

    public void createUser(User user);
}
