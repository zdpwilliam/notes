package com.william.test;

import com.william.test.domain.UserInfo;
import com.william.test.dto.UserDto;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

import java.util.Date;

/**
 * Created by william on 17-8-7.
 */
public class OrikaTest {

    public static void main(String[] args) {
        // github地址：http://orika-mapper.github.io/orika-docs/
        UserInfo userInfo = new UserInfo();
        userInfo.setId(100011);
        userInfo.setUsername("william");
        userInfo.setCreatedAt(new Date());
        userInfo.setContact("18217177521");

        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.classMap(UserInfo.class, UserDto.class)
                .field("id", "id")
                .field("username", "name")
                .field("contact", "mobile")
                .field("createdAt", "registerTime")
                .byDefault()
                .register();
        MapperFacade mapperFacade = mapperFactory.getMapperFacade();
        UserDto userDto = mapperFacade.map(userInfo, UserDto.class);
        System.out.println(userDto);

        BoundMapperFacade<UserInfo, UserDto> boundMapper =
                mapperFactory.getMapperFacade(UserInfo.class, UserDto.class);
        System.out.println(boundMapper.map(userInfo));

        System.out.println(boundMapper.mapReverse(userDto));
    }

}
