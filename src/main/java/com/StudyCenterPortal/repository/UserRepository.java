package com.StudyCenterPortal.repository;

import com.StudyCenterPortal.dto.UserRegistrationDto;
import com.StudyCenterPortal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    User findByEmail(String email);

    @Query(nativeQuery = true,value = "select * from user inner join users_roles on user.id = users_roles.user_id inner join role on users_roles.role_id = role.id where role.id = 2 desc LIMIT 1")
    List<User> findByAllStudentRole();

}
