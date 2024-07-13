package com.Tasker.Models;

import java.time.LocalDate;
import java.util.Set;
import jakarta.persistence.*;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_to")
    private MyUser assignedTo;


    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private MyUser user;

    @ManyToMany
    @JoinTable(
            name = "task_category",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )

    private Set<Category> categories;

    public void setID (long id) {this.id = id;}

    public long getID() {return id;}

    public void setTitle (String title) {this.title = title;}

    public String getTitle() {return title;}

    public void setDescription (String description) {this.description = description;}

    public String getDescription() {return description;}

    public void setStatus (String status) {this.status = status;}

    public String getStatus() {return status;}

    public void setPriority(String priority) {this.priority = priority;}

    public String getPriority() {return priority;}

    public void setDeadline(LocalDate deadline) {this.deadline = deadline;}

    public LocalDate getDeadline() {return deadline;}

    public void setAssignedTo(MyUser assignedTo) {this.assignedTo = assignedTo;}

    public MyUser getAssignedTo() {return assignedTo;}

    public void setCategories(Set categories) {this.categories = categories;}

    public Set getCategories() {return categories;}

    public MyUser getUser() {
        return user;
    }

    public void setUser(MyUser user) {
        this.user = user;
    }

}

