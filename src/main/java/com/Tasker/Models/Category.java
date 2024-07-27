package com.Tasker.Models;

import jakarta.persistence.*;
import java.util.List;

/**
 * Entity representing a category.
 * <p>
 * This class represents a category that can be associated with multiple tasks
 * and a specific user.
 * </p>
 *
 * @author Yons Said
 */
@Entity
@Table(name = "Categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private MyUser user;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "categories")
    private List<Task> tasks;

    /**
     * Constructor with category name.
     *
     * @param name the name of the category
     */
    public Category(String name) {
        this.name = name;
    }

    /**
     * Default constructor.
     */
    public Category() {
    }

    /**
     * Gets the ID of the category.
     *
     * @return the category ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the category.
     *
     * @param id the category ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the name of the category.
     *
     * @return the category name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the category.
     *
     * @param name the category name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the tasks associated with the category.
     *
     * @return the list of tasks
     */
    public List<Task> getTasks() {
        return tasks;
    }

    /**
     * Sets the tasks associated with the category.
     *
     * @param tasks the list of tasks
     */
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Gets the user associated with the category.
     *
     * @return the user
     */
    public MyUser getUser() {
        return user;
    }

    /**
     * Sets the user associated with the category.
     *
     * @param user the user
     */
    public void setUser(MyUser user) {
        this.user = user;
    }
}
