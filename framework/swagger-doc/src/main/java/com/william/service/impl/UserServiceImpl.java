package com.william.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.william.dto.UserDto;
import com.william.service.UserService;
import java.util.List;


@Service(version = "1.0.0")
@org.springframework.stereotype.Service
public class UserServiceImpl implements UserService {

  private static List<UserDto> dtoList = Lists.newArrayList();

  static {
      UserDto user1 = new UserDto();
      user1.setId(1L);
      user1.setUsername("苹果1");
      user1.setAge(18);
      user1.setGendar(1);
      user1.setRole("水果");
      dtoList.add(user1);
      UserDto user2 = new UserDto();
      user2.setId(2L);
      user2.setUsername("苹果2");
      user2.setAge(13);
      user2.setGendar(0);
      user2.setRole("水果");
      dtoList.add(user2);
      UserDto user3 = new UserDto();
      user3.setId(3L);
      user3.setUsername("水梨1");
      user3.setAge(19);
      user3.setGendar(1);
      user3.setRole("水果");
      dtoList.add(user3);
      UserDto user4 = new UserDto();
      user4.setId(4L);
      user4.setUsername("水梨2");
      user4.setAge(24);
      user4.setGendar(0);
      user4.setRole("水果");
      dtoList.add(user4);
  }

  @Override
  public List<UserDto> findAll() {
      return dtoList;
  }

  @Override
  public List<UserDto> findAllByName(String name) {
      List<UserDto> result = Lists.newArrayList();
      for (UserDto dto : dtoList) {
          if (dto.getUsername().contains(name)) {
              result.add(dto);
          }
      }
      return result;
  }
}
