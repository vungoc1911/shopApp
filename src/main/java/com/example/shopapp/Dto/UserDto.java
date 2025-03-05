package com.example.shopapp.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto  {

    private String fullName;
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;
    private String address;
    @NotBlank(message = "password is required")
    private String password;
    private Date DateOfBirth;
    private int facebookAccountId;
    private int googleAccountId;
    private Long roleId;
    private String retypePassword;
}
