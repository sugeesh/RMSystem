package org.hashtag.rms.controller;


import org.hashtag.rms.resource.ItemResource;
import org.hashtag.rms.resource.UserResource;
import org.hashtag.rms.service.ItemService;
import org.hashtag.rms.service.UserService;
import org.hashtag.rms.util.exceptions.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * ItemController is for handling the item requests
 *
 * @author Sugeesh Chandraweera
 */

@Component
@Path("/user")
public class UserController extends AbstractController {

    @Autowired
    private UserService userService;

    @POST
    @Path("/register_user")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerUser(UserResource userResource) {
        try {
            return sendSuccessResponse(userService.registerUser(userResource));
        } catch (Exception e) {
            e.printStackTrace();
            return handleServiceException(e);
        }
    }

    @POST
    @Path("/login_user")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginUser(UserResource userResource) {
        try {
            return sendSuccessResponse(userService.loginUser(userResource));
        } catch (Exception e) {
            e.printStackTrace();
            return handleServiceException(e);
        }
    }

    @POST
    @Path("/update_user")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(UserResource userResource) {
        try {
            return sendSuccessResponse(userService.updateUser(userResource));
        } catch (Exception e) {
            e.printStackTrace();
            return handleServiceException(e);
        }
    }

    @GET
    @Path("/get_all_users")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {
        try {
            return sendSuccessResponse(userService.getAllUsers());
        } catch (Exception e) {
            e.printStackTrace();
            return handleServiceException(e);
        }
    }

}