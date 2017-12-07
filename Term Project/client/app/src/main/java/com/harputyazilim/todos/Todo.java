package com.harputyazilim.todos;

import java.io.Serializable;

/**
 * Created by furkan on 4.12.2017.
 */

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
