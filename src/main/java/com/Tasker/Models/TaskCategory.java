package com.Tasker.Models;

import jakarta.persistence.*;

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

    public void setId(Long id) {this.id = id;}

    public Long getId() {return id;}

    public void setCategory(Category category) {this.category = category;}

    public Category getCategory() {return category;}

    public void setTask(Task task) {this.task = task;}

    public Task getTask() {return task;}
}

