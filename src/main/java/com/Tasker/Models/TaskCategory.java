package com.Tasker.Models;

import jakarta.persistence.*;

/**
 * Represents the association between a task and a category.
 * <p>
 * The TaskCategory entity maps the many-to-many relationship between tasks and categories,
 * allowing a task to belong to multiple categories and a category to contain multiple tasks.
 * </p>
 *
 * @author Yons Said
 */
@Entity
@Table(name = "task_category")
public class TaskCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_category_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "task_id", referencedColumnName = "task_id")
    private Task task;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private Category category;

    /**
     * Sets the ID of the task-category association.
     *
     * @param id the task-category ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the ID of the task-category association.
     *
     * @return the task-category ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the category in the task-category association.
     *
     * @param category the category
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * Returns the category in the task-category association.
     *
     * @return the category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Sets the task in the task-category association.
     *
     * @param task the task
     */
    public void setTask(Task task) {
        this.task = task;
    }

    /**
     * Returns the task in the task-category association.
     *
     * @return the task
     */
    public Task getTask() {
        return task;
    }
}
