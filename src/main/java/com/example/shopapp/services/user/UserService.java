package com.example.shopapp.services.user;

import ch.qos.logback.core.util.StringUtil;
import com.example.shopapp.compoments.JwtTokenUtils;
import com.example.shopapp.dto.UserDto;
import com.example.shopapp.exception.BadRequestException;
import com.example.shopapp.exception.DataNotFoundException;
import com.example.shopapp.model.Role;
import com.example.shopapp.model.User;
import com.example.shopapp.repositories.RoleRepository;
import com.example.shopapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;
    private final RedisTemplate<String, Object> redisTemplate;

    private final String DEFAULT_KEY_VALUE = "user:";

    @Override
    public User createUser(UserDto userDTO) throws Exception {

        if (userRepository.existsByPhoneNumber(userDTO.getPhoneNumber())) {
            throw new Exception("Phone number already in use");
        }
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new BadRequestException("Email already in use");
        }
        User user = User.builder()
                .fullName(userDTO.getFullName())
                .phoneNumber(userDTO.getPhoneNumber())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .address(userDTO.getAddress())
                .facebookAccountId(userDTO.getFacebookAccountId())
                .googleAccountId(userDTO.getGoogleAccountId())
                .active(true)
                .build();

        Role role = roleRepository.findById(userDTO.getRoleId()).orElseThrow(
                () -> new DataNotFoundException("Role not found")
        );

        user.setRole(role);
        if (userDTO.getFacebookAccountId().equals("0")|| userDTO.getGoogleAccountId().equals("0")) {
            String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
            user.setPassword(encodedPassword);
        }
        userRepository.save(user);
        redisTemplate.opsForValue().set(DEFAULT_KEY_VALUE + user.getId(), user);
        return user;
    }

    @Override
    public String login(String phoneNumber, String password) throws Exception {
        User user = userRepository.findByPhoneNumber(phoneNumber).orElseThrow(
                () -> new DataNotFoundException("User not found")
        );

        if (user.getFacebookAccountId().equals("0")|| user.getGoogleAccountId().equals("0")) {
           if(!passwordEncoder.matches(password, user.getPassword())) {
               throw new Exception("Wrong password");
           }
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(phoneNumber, password, user.getAuthorities());

        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtils.generateToken(user);
    }

    @Override
    public User updateUser(UserDto userDTO) throws Exception {
        User userUpdate = userRepository.findByPhoneNumber(userDTO.getPhoneNumber())
                .orElseThrow(() -> new DataNotFoundException("User not found"));

// Cập nhật từng trường
        userUpdate.setFullName(userDTO.getFullName());
        userUpdate.setEmail(userDTO.getEmail());
        userUpdate.setPassword(userDTO.getPassword());
        userUpdate.setAddress(userDTO.getAddress());
        userUpdate.setFacebookAccountId(userDTO.getFacebookAccountId());
        userUpdate.setGoogleAccountId(userDTO.getGoogleAccountId());
        userUpdate.setActive(true);

// Lưu vào DB
        userRepository.save(userUpdate);

// Lưu lại vào Redis cache
        redisTemplate.opsForValue().set("user:" + userUpdate.getId(), userUpdate);


        Role role = roleRepository.findById(userDTO.getRoleId()).orElseThrow(
                () -> new DataNotFoundException("Role not found")
        );
        userUpdate.setRole(role);
        if (userDTO.getFacebookAccountId().equals("0")|| userDTO.getGoogleAccountId().equals("0")) {
            String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
            userUpdate.setPassword(encodedPassword);
        }
        userRepository.save(userUpdate);
        redisTemplate.opsForValue().set(DEFAULT_KEY_VALUE + userUpdate.getId(), userUpdate);
        return userUpdate;
    }


}
