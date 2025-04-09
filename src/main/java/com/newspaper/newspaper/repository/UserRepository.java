package com.newspaper.newspaper.repository;

import org.springframework.stereotype.Repository;
import com.newspaper.newspaper.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
}  






