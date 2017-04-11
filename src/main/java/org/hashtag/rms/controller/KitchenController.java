package org.hashtag.rms.controller;

import org.hashtag.rms.resource.CategoryResource;
import org.hashtag.rms.resource.KitchenResource;
import org.hashtag.rms.service.CategoryService;
import org.hashtag.rms.service.KitchenService;
import org.hashtag.rms.util.exceptions.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Sugeesh Chandraweera
 */

@Component
@Path("/kitchen")
public class KitchenController extends AbstractController {

    @Autowired
    private KitchenService kitchenService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllKitchens(
            @QueryParam("search") String search,
            @QueryParam("page") int page,
            @QueryParam("size") int size,
            @QueryParam("asc") Boolean asc,
            @QueryParam("column") String column
    ) {
        try {
            if (search == null && page == 0 && size == 0 && asc == null && column == null)
                return sendSuccessResponse(kitchenService.getAllKitchensWithoutPagination());
            else
                return sendSuccessResponse(kitchenService.getAllCategories(search, page, size, asc, column));
        } catch (Exception e) {
            e.printStackTrace();
            return handleServiceException(e);
        }
    }

    @POST
    @Path("/save_kitchen")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveKitchen(KitchenResource kitchenResource) {
        try {
            return sendSuccessResponse(kitchenService.saveKitchen(kitchenResource));
        } catch (Exception e) {
            e.printStackTrace();
            return handleServiceException(e);
        }
    }

    @GET
    @Path("/all_kitchen")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllKitchens() {
        try {
            return sendSuccessResponse(kitchenService.getAllKitchensWithoutPagination());
        } catch (Exception e) {
            e.printStackTrace();
            return handleServiceException(e);
        }
    }

}
