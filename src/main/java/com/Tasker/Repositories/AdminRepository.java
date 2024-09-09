package com.Tasker.Repositories;

import com.Tasker.Models.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing {@link MyUser} entities as Admin.
 * <p>
 * This interface extends {@link JpaRepository} to provide CRUD operations for {@link MyUser} entities.
 * </p>
 *
 * @author Yons Said
 */
public interface AdminRepository extends JpaRepository<MyUser, Long> {
}
