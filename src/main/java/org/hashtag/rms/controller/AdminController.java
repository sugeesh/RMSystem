package org.hashtag.rms.controller;


import org.hashtag.rms.resource.AdminResource;
import org.hashtag.rms.service.AdminService;
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
    @Path("/taxdinein")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTaxDineIn() {
        try {
            return sendSuccessResponse(adminService.getTaxDineIn());
        } catch (Exception e) {
            e.printStackTrace();
            return handleServiceException(e);
        }
    }

    @GET
    @Path("/s_chargedinein")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSChargeDineIn() {
        try {
            return sendSuccessResponse(adminService.getSChargeDineIn());
        } catch (Exception e) {
            e.printStackTrace();
            return handleServiceException(e);
        }
    }

    @GET
    @Path("/taxtakeaway")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTaxTakeAway() {
        try {
            return sendSuccessResponse(adminService.getTaxTakeAway());
        } catch (Exception e) {
            e.printStackTrace();
            return handleServiceException(e);
        }
    }

    @GET
    @Path("/s_chargetakeaway")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSChargeTakeAway() {
        try {
            return sendSuccessResponse(adminService.getSChargeTakeAway());
        } catch (Exception e) {
            e.printStackTrace();
            return handleServiceException(e);
        }
    }
}