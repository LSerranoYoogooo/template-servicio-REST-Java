package com.escobarmiranda.webservice.resources;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.escobarmiranda.webservice.models.User;
import com.escobarmiranda.webservice.services.UserService;

@Path("user")
public class UserResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() throws Exception {
        List<User> result = UserService.getInstance().getAllUsers();
        GenericEntity<List<User>> list = new GenericEntity<List<User>>(result) {
        };
        return Response.ok(list).build();
    }

    @GET
    @Path("/{id}")
    public Response getUser(@PathParam("id") int id) throws Exception {
        User user = UserService.getInstance().getUser(id);
        return Response.ok(user).build();
    }

    @POST
    public Response createUser(User user) throws Exception {
        UserService.getInstance().saveOrUpdateUser(user);
        return Response.ok().build();
    }

    @PUT
    public Response update(User user) throws Exception {
        UserService.getInstance().saveOrUpdateUser(user);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") int id) throws Exception {
        UserService.getInstance().deleteUser(id);
        return Response.ok().build();
    }
}
