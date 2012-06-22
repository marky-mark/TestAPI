package com.mtt.api.converter;

import com.mtt.api.model.APITask;
import com.mtt.domain.entity.Task;
import org.springframework.core.convert.converter.Converter;

public final class TaskToAPITaskConverter implements Converter<Task, APITask> {

    public APITask convert(Task dbTask) {
        APITask apiTask =  new APITask();

        apiTask.setChecked(dbTask.isChecked());
        apiTask.setDescription(dbTask.getDescription());
        apiTask.setId(dbTask.getId());
        apiTask.setTitle(dbTask.getTitle());
        apiTask.setCreatedDate(dbTask.getCreatedDate());
        apiTask.setUserEmail(dbTask.getUser().getUsername());

        return apiTask;
    }
}
