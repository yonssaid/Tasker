package com.Tasker.Dto;

import java.time.LocalDate;

/**
 * Data Transfer Object for Task entity.
 *
 * @author Yons Said
 */
public class TaskDto {

    private Long id;
    private String title;
    private String description;
    private String status;
    private String priority;
    private LocalDate deadline;

    /**
     * Gets the task ID.
     *
     * @return the task ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the task ID.
     *
     * @param id the task ID to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the task title.
     *
     * @return the task title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the task title.
     *
     * @param title the task title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the task description.
     *
     * @return the task description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the task description.
     *
     * @param description the task description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the task status.
     *
     * @return the task status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the task status.
     *
     * @param status the task status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the task priority.
     *
     * @return the task priority
     */
    public String getPriority() {
        return priority;
    }

    /**
     * Sets the task priority.
     *
     * @param priority the task priority to set
     */
    public void setPriority(String priority) {
        this.priority = priority;
    }

    /**
     * Gets the task deadline.
     *
     * @return the task deadline
     */
    public LocalDate getDeadline() {
        return deadline;
    }

    /**
     * Sets the task deadline.
     *
     * @param deadline the task deadline to set
     */
    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }
}
