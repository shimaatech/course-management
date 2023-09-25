package com.aiman.coursemanagement.mapper;

import com.aiman.coursemanagement.dto.UserDto;
import com.aiman.coursemanagement.entity.User;

public class UserMapper {

    public static User mapToUser(UserDto dto) {
        return User.builder()
                .name(dto.getId())
                .password(dto.getPassword())
                .roles(dto.getRoles())
                .build();
    }

    public static UserDto mapToUser(User user) {
        return UserDto.builder()
                .id(user.getName())
                .password(user.getPassword())
                .roles(user.getRoles())
                .build();
    }

}
