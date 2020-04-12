package com.securityapp.securityapp.jwt.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UserRepository extends JpaRepository<MyUser, Long> {
	 
    MyUser findByUsername(String username);
    
}