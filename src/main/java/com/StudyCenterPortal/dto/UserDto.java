package com.StudyCenterPortal.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserDto {
    private long id;
    private String email;
    private String address;
    private String firstName;
    private String lastName;
    private String password;
    private LocalDateTime createDate;
}
