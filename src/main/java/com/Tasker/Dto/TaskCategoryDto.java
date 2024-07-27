package com.Tasker.Dto;

/**
 * Data Transfer Object for Task-Category relationships.
 *
 * @author Yons Said
 */
public class TaskCategoryDto {
    private Long taskId;
    private Long categoryId;

    /**
     * Gets the ID of the task.
     *
     * @return the task ID.
     */
    public Long getTaskId() {
        return taskId;
    }

    /**
     * Sets the ID of the task.
     *
     * @param taskId the task ID to set.
     */
    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    /**
     * Gets the ID of the category.
     *
     * @return the category ID.
     */
    public Long getCategoryId() {
        return categoryId;
    }

    /**
     * Sets the ID of the category.
     *
     * @param categoryId the category ID to set.
     */
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
