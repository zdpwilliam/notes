package com.william.dao;

import com.william.model.User;

import java.util.List;

/**
 * Created by william.zhang on 2016/3/18.
 */
public interface IUserDao {

    public List<User> getAllUsers();

    public void createUser(User user);

    public void updateUser(User user);
}
