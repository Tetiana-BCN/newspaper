package com.newspaper.newspaper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.newspaper.newspaper.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByName(String name);

    User findByEmail(String email);

    boolean existsByName(String name);

    boolean existsByEmail(String email);

    void deleteById(long id);
}
