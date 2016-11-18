package com.escobarmiranda.webservice.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;

import com.escobarmiranda.webservice.models.User;
import com.escobarmiranda.webservice.resources.UserResource;
import com.escobarmiranda.webservice.services.UserService;

public class UserResourceTest extends JerseyTest {

    private final String NAME = "Kevin";

    private UserService userService = UserService.getInstance();

    private List<User> insertTestUsers(int count) {
        List<User> testUsers = new ArrayList<>();
        for (; count > 0; count--) {
            User testUser = new User();
            testUser.setName(NAME);
            userService.saveOrUpdateUser(testUser);
            testUsers.add(testUser);
        }
        return testUsers;
    }

    @Override
    protected Application configure() {
        return new ResourceConfig(UserResource.class);
    }

    @Test
    public void testGetAllUsers() {
        List<User> testUser = insertTestUsers(5);
        Assert.assertTrue(testUser.size() == 5);
        final Response response = target().path("user").request().get();
        Assert.assertEquals(200, response.getStatus());
        List<User> usersList = response.readEntity(new GenericType<List<User>>() {
        });
        Assert.assertEquals(testUser.size(), usersList.size());
    }

    @Test
    public void testGetSingleAddress() {
        List<User> testUser = insertTestUsers(1);
        Assert.assertTrue(testUser.size() > 0);
        User toCompare = testUser.get(0);
        String path = "user/%d";
        final Response response = target().path(String.format(path, toCompare.getId())).request().get();
        Assert.assertEquals(200, response.getStatus());
        User user = response.readEntity(User.class);
        Assert.assertTrue("Object do not match", user.equals(toCompare));
        userService.deleteUser(toCompare.getId());
    }

    @Test
    public void testDeleteUser() {
        List<User> testUser = insertTestUsers(1);
        Assert.assertTrue(testUser.size() > 0);
        User toDelete = testUser.get(0);
        String path = "user/%d";
        final Response response = target().path(String.format(path, toDelete.getId())).request().delete();
        Assert.assertEquals(200, response.getStatus());
        Assert.assertNull("Object still persist", userService.getUser(toDelete.getId()));
    }

    @Test
    public void testEditUser() {
        List<User> testUser = insertTestUsers(1);
        Assert.assertTrue(testUser.size() > 0);
        User toUpdate = testUser.get(0);
        toUpdate.setName("Modified Name");
        final Response response = target().path("user").request().put(Entity.json(toUpdate), Response.class);
        Assert.assertEquals(200, response.getStatus());
        User modifiedUser = UserService.getInstance().getUser(toUpdate.getId());
        Assert.assertTrue("Not the same object", modifiedUser.equals(toUpdate));
        Assert.assertNotEquals("Name not modified", NAME, modifiedUser.getName());
    }
}
