package com.Tasker.Repositories;

import com.Tasker.Models.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<MyUser, Long> {
}
