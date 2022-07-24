package com.StudyCenterPortal.service;

import com.StudyCenterPortal.dto.UserRegistrationDto;
import com.StudyCenterPortal.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

interface IUserService extends UserDetailsService {

    User findByEmail(String email);


    User save_student(UserRegistrationDto registration);

    User save_teacher(UserRegistrationDto registration);
}