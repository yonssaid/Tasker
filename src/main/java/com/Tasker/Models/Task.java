package com.Tasker.Models;

import java.time.LocalDate;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

/**
 * Represents a task within the task management application.
 * <p>
 * The Task entity defines a task with attributes such as title, description, status, priority, and deadline.
 * Tasks are associated with a user and can belong to multiple categories.
 * </p>
 * <p>
 * This class uses JPA annotations for ORM mapping and Jackson annotations for JSON serialization.
 * </p>
 *
 * @author Yons Said
 */
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private String status;

    @Column(name = "priority")
    private String priority;

    @Column(name = "deadline")
    private LocalDate deadline;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private MyUser user;

    @JsonBackReference
    @ManyToMany
    @JoinTable(
            name = "task_category",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories;

    /**
     * Sets the ID of the task.
     *
     * @param id the task ID
     */
    public void setID(long id) {
        this.id = id;
    }

    /**
     * Returns the ID of the task.
     *
     * @return the task ID
     */
    public long getID() {
        return id;
    }

    /**
     * Sets the title of the task.
     *
     * @param title the task title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the title of the task.
     *
     * @return the task title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the description of the task.
     *
     * @param description the task description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the description of the task.
     *
     * @return the task description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the status of the task.
     *
     * @param status the task status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Returns the status of the task.
     *
     * @return the task status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the priority of the task.
     *
     * @param priority the task priority
     */
    public void setPriority(String priority) {
        this.priority = priority;
    }

    /**
     * Returns the priority of the task.
     *
     * @return the task priority
     */
    public String getPriority() {
        return priority;
    }

    /**
     * Sets the deadline of the task.
     *
     * @param deadline the task deadline
     */
    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    /**
     * Returns the deadline of the task.
     *
     * @return the task deadline
     */
    public LocalDate getDeadline() {
        return deadline;
    }

    /**
     * Sets the categories associated with the task.
     *
     * @param categories the task categories
     */
    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    /**
     * Returns the categories associated with the task.
     *
     * @return the task categories
     */
    public Set<Category> getCategories() {
        return categories;
    }

    /**
     * Returns the user associated with the task.
     *
     * @return the user
     */
    public MyUser getUser() {
        return user;
    }

    /**
     * Sets the user associated with the task.
     *
     * @param user the user
     */
    public void setUser(MyUser user) {
        this.user = user;
    }
}
