package com.william.dao.impl;

import com.william.dao.IUserDao;
import com.william.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by william.zhang on 2016/3/18.
 */
@Component
public class UserDaoImpl implements IUserDao {

    private static Map<Integer, User> userMap = new LinkedHashMap<Integer, User>();

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<User>(userMap.values());
    }

    @Override
    public void createUser(User user) {
        int userId = Long.valueOf(System.currentTimeMillis()).intValue();
        user.setId(userId);
        userMap.put(userId, user);
    }

    @Override
    public void updateUser(User user) {
        userMap.put(user.getId(), user);
    }
}
