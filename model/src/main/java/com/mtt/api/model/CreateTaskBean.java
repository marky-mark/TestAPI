package com.mtt.api.model;

import com.mtt.validation.NotHtml;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

public class CreateTaskBean {

    @NotBlank(message = "task must have a title")
    @Size(min = 1, max = 100, message = "title must be between 1 and 100 characters long")
    @NotHtml(message = "task title cannot contain html tags")
    private String title;

    @NotBlank(message = "task must have a description")
    @Size(min = 1, max = 100, message = "description must be between 1 and 100 characters long")
    @NotHtml(message = "task description cannot contain html tags")
    private String description;

    public CreateTaskBean() {
    }

    public CreateTaskBean(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
