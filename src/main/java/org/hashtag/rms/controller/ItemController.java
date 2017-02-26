package org.hashtag.rms.controller;


import org.hashtag.rms.resource.CategoryResource;
import org.hashtag.rms.resource.ItemResource;
import org.hashtag.rms.service.ItemService;
import org.hashtag.rms.util.exceptions.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import javax.print.attribute.standard.Media;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * ItemController is for handling the item requests
 *
 * @author Sugeesh Chandraweera
 */

@Component
@Path("/item")
public class ItemController extends AbstractController {

    @Autowired
    private ItemService itemService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllItems(
            @QueryParam("search") String search,
            @QueryParam("page") int page,
            @QueryParam("size") int size,
            @QueryParam("asc") Boolean asc,
            @QueryParam("column") String column
    ) {
        try {
            if (search == null && page == 0 && size == 0 && asc == null && column == null)
                return sendSuccessResponse(itemService.getAllItemsWithoutPagination());
            else
                return sendSuccessResponse(itemService.getAllItems(search, page, size, asc, column));
        } catch (Exception e) {
            e.printStackTrace();
            return handleServiceException(e);
        }
    }

    @POST
    @Path("/save_item")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveItem(ItemResource itemResource) {
        try {
            return sendSuccessResponse(itemService.saveItem(itemResource));
        } catch (Exception e) {
            e.printStackTrace();
            return handleServiceException(e);
        }
    }


    @DELETE
    @Path("/delete_item")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCategory( @QueryParam("id") int id) {
        try {
            return sendSuccessResponse(itemService.deleteItem(id));
        } catch (DataIntegrityViolationException e) {
//            return sendSuccessResponse("Item can't be deleted because it used in a order.");
            return handleServiceException(e,"Item can't be deleted because it used in a order.");
        }
    }



    @PUT
    @Path("/update_priority/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePriority(ItemResource array[]) {
        try {
            itemService.updateAllItemPriority(array);
            return sendSuccessResponse(array);
        } catch (ServiceException e) {
            return handleServiceException(e);
        }
    }
}