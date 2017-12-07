package com.harputyazilim.todos;

import java.io.Serializable;

public class Todo implements Serializable {
    private static final long serialVersionUID = 5950169519310163575L;
    private int id;
    private String creator;
    private String assignee;
    private String title;
    private String description;
    private String date;
    private String deadline;
    private String lastUpdate;

    public Todo(int id, String creator, String assignee, String title, String description, String date, String deadline, String lastUpdate) {
        this.id = id;
        this.creator = creator;
        this.assignee = assignee;
        this.title = title;
        this.description = description;
        this.date = date;
        this.deadline = deadline;
        this.lastUpdate = lastUpdate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getAssignee() {
        return assignee;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getDeadline() {
        return deadline;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public int getId() {
        return id;

    }
}
