package org.hashtag.rms.controller;


import org.hashtag.rms.resource.AdminResource;
import org.hashtag.rms.resource.ItemResource;
import org.hashtag.rms.service.AdminService;
import org.hashtag.rms.service.ItemService;
import org.hashtag.rms.util.exceptions.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
@Path("/admin")
public class AdminController extends AbstractController {

    @Autowired
    private AdminService adminService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveSettings(AdminResource adminResource) {
        try {
            return sendSuccessResponse(adminService.saveSettings(adminResource));
        } catch (Exception e) {
            e.printStackTrace();
            return handleServiceException(e);
        }
    }

    @GET
    @Path("/tax")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTax() {
        try {
            return sendSuccessResponse(adminService.getTax());
        } catch (Exception e) {
            e.printStackTrace();
            return handleServiceException(e);
        }
    }

    @GET
    @Path("/s_charge")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSCharge() {
        try {
            return sendSuccessResponse(adminService.getSCharge());
        } catch (Exception e) {
            e.printStackTrace();
            return handleServiceException(e);
        }
    }
}