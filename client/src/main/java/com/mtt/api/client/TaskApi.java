package com.mtt.api.client;

import com.mtt.api.model.APITask;
import com.mtt.service.request.CreateTaskRequest;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

public interface TaskApi {

    @GET
    @Path("/task/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public APITask getTask(@PathParam("id") Long id);

    @POST
    @Path("task")
    @Produces(MediaType.APPLICATION_JSON)
    public APITask createTask(CreateTaskRequest createTaskRequest);

    @PUT
    @Path("/task/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public APITask updateTask(@PathParam("id") String id);

    @PUT
    @Path("/task/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteTask(@PathParam("id") String id);
}
