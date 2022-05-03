package com.william.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.william.dto.UserDto;
import io.swagger.annotations.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by william on 17-3-24.
 */
@RestController
@RequestMapping("/api/user/")
@Api(value = "User相关接口|一个用来测试swagger注解的控制器")
public class UserController {
    private static Log logger = LogFactory.getLog(UserController.class);
    private static final Map<Long, UserDto> userDtoMap = Maps.newLinkedHashMap();

    @RequestMapping(value = "registeUser", method = RequestMethod.POST)
    @ApiOperation(value="添加新用户", notes="根据用户名，角色，年龄和性别添加用户")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType="form", name = "username", value = "名称", required = true, dataType = "String"),
        @ApiImplicitParam(paramType="form", name = "role", value = "角色", required = true, dataType = "String"),
        @ApiImplicitParam(paramType="form", name = "age", value = "年龄", required = true, dataType = "Integer"),
        @ApiImplicitParam(paramType="form", name = "gendar", value = "性别", required = true, dataType = "Integer")
    })
    @ApiResponses({
        @ApiResponse(code = 201, message = "用户新增成功!")
    })
    public UserDto registeUser(HttpServletRequest request) {
        UserDto userDto = new UserDto();
        userDto.setUsername(request.getParameter("username"));
        userDto.setRole(request.getParameter("role"));
        userDto.setAge(Integer.valueOf(request.getParameter("age")));
        userDto.setGendar(Integer.valueOf(request.getParameter("gendar")));
        if(userDto == null) {
            return null;
        }
        Long userId = generateUserId();
        userDto.setId(userId);
        userDtoMap.put(userId, userDto);
        return userDto;
    }

    @RequestMapping(value = "deleteUserById", method = RequestMethod.DELETE)
    @ApiOperation(value="删除用户", notes="根据用户名ID删除用户")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="form", name = "userId", value = "用户ID", required = true, dataType = "Integer"),
    })
    @ApiResponses({
            @ApiResponse(code = 201, message = "用户删除成功!")
    })
    public UserDto deleteUserById(HttpServletRequest request) {
        String userIdStr = request.getParameter("userId");
        if(userIdStr == null) {
            return null;
        }
        return userDtoMap.remove(Long.valueOf(userIdStr));
    }

    @RequestMapping(value = "findUserById", method = RequestMethod.GET)
    @ApiOperation(value="根据ID查询用户", notes="时间戳，从控制台查看!")
    @ApiImplicitParam(paramType="query", name = "userId", value = "用户ID", required = true, dataType = "Integer")
    @ApiResponses({
            @ApiResponse(code = 201, message = "查询用户成功!")
    })
    public UserDto findUserById(Long userId) {
        if(userId == null) {
            return null;
        }
        return userDtoMap.get(userId);
    }

    @RequestMapping(value = "findUsersByName", method = RequestMethod.GET)
    @ApiOperation(value="根据用户名查询用户", notes="根据用户名查询某些用户")
    @ApiImplicitParam(paramType="query", name = "username", value = "用户名称", required = true, dataType = "Integer")
    @ApiResponses({
            @ApiResponse(code = 201, message = "查询用户成功!")
    })
    public List<UserDto> findUsersByName(String username) {
        Iterator<UserDto> userDtoIterator = userDtoMap.values().iterator();
        List<UserDto> targetUserList = Lists.newArrayList();
        while (userDtoIterator.hasNext()) {
            UserDto userDto = userDtoIterator.next();
            if(userDto.getUsername().contains(username)) {
                targetUserList.add(userDto);
            }
        }
        return targetUserList;
    }

    @RequestMapping(value = "getAllUsers", method = RequestMethod.GET)
    @ApiOperation(value="获取所有用户", notes="获取所有用户列表")
    @ApiResponses({
            @ApiResponse(code = 201, message = "获取用户成功!")
    })
    public List<UserDto> getAllUsers() {
        return Lists.newArrayList(userDtoMap.values());
    }

    @PostConstruct
    private void initUserList() {
        for (int i = 0; i < 3; i++) {
            Long userId = generateUserId();
            logger.info("userid_" + i + ": " + userId);
            userDtoMap.put(userId, new UserDto(userId, "苹果_" + i, "boss", 21, 1));
        }
    }

    private Long generateUserId() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return System.currentTimeMillis();
    }

}
