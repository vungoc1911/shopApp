package com.example.shopapp.services.user;

import com.example.shopapp.dto.UserDto;
import com.example.shopapp.model.User;

public interface IUserService {

    User createUser(UserDto userDTO) throws Exception;

    String login(String phoneNumber, String password) throws Exception;

    User updateUser(UserDto userDTO) throws Exception;

    User deleteUser(Long id) throws Exception;

    User filterUser(Long id) throws Exception;
}
