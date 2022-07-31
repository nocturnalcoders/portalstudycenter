package com.StudyCenterPortal.service;

import com.StudyCenterPortal.model.User;
import com.StudyCenterPortal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TeacherService {

    private final UserRepository userRepository;

    public List<User> findByAllTeacherRole() {
        return userRepository.findByAllTeacherRole();
    }
}
