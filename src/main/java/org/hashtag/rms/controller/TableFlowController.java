package org.hashtag.rms.controller;


import org.hashtag.rms.resource.CategoryResource;
import org.hashtag.rms.resource.ItemResource;
import org.hashtag.rms.resource.TableFlowResource;
import org.hashtag.rms.service.ItemService;
import org.hashtag.rms.service.TableFlowService;
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
@Path("/table")
public class TableFlowController extends AbstractController {

    @Autowired
    private TableFlowService tableFlowService;


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveTableFlow(TableFlowResource tableFlowResource) {
        try {
            return sendSuccessResponse(tableFlowService.saveTable(tableFlowResource.getName()));
        } catch (Exception e) {
            e.printStackTrace();
            return handleServiceException(e);
        }
    }

    @GET
    @Path("/all_tables")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTables() {
        try {
            return sendSuccessResponse(tableFlowService.getAllTables());
        } catch (Exception e) {
            e.printStackTrace();
            return handleServiceException(e);
        }
    }


    @GET
    @Path("/all_tables_available")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTablesAvailable() {
        try {
            return sendSuccessResponse(tableFlowService.getAllAvailableTables());
        } catch (Exception e) {
            e.printStackTrace();
            return handleServiceException(e);
        }
    }

    @PUT
    @Path("/update_table/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePriority(TableFlowResource tableResource) {
        try {
            tableFlowService.updateAvailability(tableResource.getTableId(),true);
            return sendSuccessResponse(tableResource);
        } catch (ServiceException e) {
            return handleServiceException(e);
        }
    }


}