package com.StudyCenterPortal.repository;

import com.StudyCenterPortal.dto.UserDto;
import com.StudyCenterPortal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<User, Long> {


}
