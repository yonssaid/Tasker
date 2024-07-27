package com.Tasker.Models;

import jakarta.persistence.*;

/**
 * Represents a user role within the application.
 * <p>
 * The Role entity defines the roles that can be assigned to users, such as ADMIN or USER.
 * </p>
 * <p>
 * Each role has a unique name and an auto-generated ID.
 * </p>
 * <p>
 * This class uses JPA annotations for ORM mapping.
 * </p>
 *
 * @author Yons Said
 */
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    /**
     * Default constructor.
     * <p>
     * Required by JPA.
     * </p>
     */
    public Role() {
    }

    /**
     * Constructs a Role with the specified name.
     *
     * @param name the name of the role
     */
    public Role(String name) {
        this.name = name;
    }

    /**
     * Returns the ID of the role.
     *
     * @return the role ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the role.
     *
     * @param id the role ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the name of the role.
     *
     * @return the role name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the role.
     *
     * @param name the role name
     */
    public void setName(String name) {
        this.name = name;
    }
}
