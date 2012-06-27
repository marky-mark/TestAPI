package com.mtt.service.request;

import com.mtt.validation.NotHtml;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

public class CreateTaskRequest {

    @NotBlank(message = "task.create.title.blank")
    @Size(min = 1, max = 100, message = "task.create.title.length")
    @NotHtml(message = "task.create.title.html")
    private String title;

    @NotBlank(message = "task.create.description.blank")
    @Size(min = 1, max = 100, message = "task.create.description.length")
    @NotHtml(message = "task.create.description.html")
    private String description;

    private boolean checked;

    @JsonProperty("user_id")
    private Long userId;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
