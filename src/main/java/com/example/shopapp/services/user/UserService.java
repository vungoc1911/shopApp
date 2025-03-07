package com.example.shopapp.services.user;

import ch.qos.logback.core.util.StringUtil;
import com.example.shopapp.dto.UserDto;
import com.example.shopapp.exception.DataNotFoundException;
import com.example.shopapp.model.Role;
import com.example.shopapp.model.User;
import com.example.shopapp.repositories.RoleRepository;
import com.example.shopapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public User createUser(UserDto userDTO) throws Exception {

        if (userRepository.existsByPhoneNumber(userDTO.getPhoneNumber())) {
            throw new Exception("Phone number already in use");
        }
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new Exception("Email already in use");
        }
        User user = User.builder()
                .fullName(userDTO.getFullName())
                .phoneNumber(userDTO.getPhoneNumber())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .address(userDTO.getAddress())
                .dateOfBirth(userDTO.getDateOfBirth())
                .facebookAccountId(userDTO.getFacebookAccountId())
                .googleAccountId(userDTO.getGoogleAccountId())
                .active(true)
                .build();

        Role role = roleRepository.findById(userDTO.getRoleId()).orElseThrow(
                () -> new DataNotFoundException("Role not found")
        );

        user.setRole(role);
        if (StringUtils.isBlank(userDTO.getFacebookAccountId()) || StringUtils.isBlank(userDTO.getGoogleAccountId())) {
            userDTO.getPassword();
        }
        return userRepository.save(user);
    }

    @Override
    public String login(String phoneNumber, String password) throws Exception {
        // đoạn này làm trong security
        return "";
    }

}
