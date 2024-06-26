package com.Tasker.Models;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "categories")
    private List<Task> tasks;

    public void setID(Long id) {this.id = id;}

    public Long getId(){return id;}

    public void setName(String name){this.name = name;}

    public String getName(){return name;}

    public void setTasks(List<Task> tasks) {this.tasks = tasks;}

    public List<Task> getTasks(){return tasks;}
}

