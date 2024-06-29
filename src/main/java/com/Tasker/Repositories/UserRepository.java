package com.Tasker.Repositories;

import com.Tasker.Models.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<MyUser, Long> {
    Optional<MyUser> findByUsername(String username);
    Boolean existsByUsername(String username);

}
