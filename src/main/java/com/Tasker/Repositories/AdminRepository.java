package com.Tasker.Repositories;

import com.Tasker.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<User, Long> {
}
