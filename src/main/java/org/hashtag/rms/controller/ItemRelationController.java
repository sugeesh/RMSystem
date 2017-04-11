package org.hashtag.rms.controller;

import org.hashtag.rms.service.ItemRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;

/**
 * @author Sugeesh Chandraweera
 */
@Component
@Path("/itemrelation")
public class ItemRelationController extends AbstractController {

    @Autowired
    private ItemRelationService itemRelationService;

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/get_child_items_for_item/{id}")
    public Response getChildItemsForItem(@PathParam("id") Integer id) throws ParseException {
        try {
            return sendSuccessResponse(itemRelationService.getChildItemsForItem(id));
        } catch (Exception e) {
            e.printStackTrace();
            return handleServiceException(e);
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/add_new_item_relation/{itemId}/{childId}")
    public Response addNewItemRelation(@PathParam("itemId") Integer itemId, @PathParam("childId") Integer childId) throws ParseException {
        try {
            return sendSuccessResponse(itemRelationService.addNewItemRelation(itemId, childId));
        } catch (Exception e) {
            e.printStackTrace();
            return handleServiceException(e);
        }
    }

    @DELETE
    @Path("/remove_item_relation/{itemId}/{childId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeItemRelation(@PathParam("itemId") Integer itemId, @PathParam("childId") Integer childId) throws ParseException {
        try {
            return sendSuccessResponse(itemRelationService.removeItemRelation(itemId, childId));
        } catch (Exception e) {
            e.printStackTrace();
            return handleServiceException(e);
        }
    }
}


