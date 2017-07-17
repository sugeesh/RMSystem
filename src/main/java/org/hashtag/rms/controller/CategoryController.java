package org.hashtag.rms.controller;

import org.hashtag.rms.resource.CategoryResource;
import org.hashtag.rms.service.CategoryService;
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
@Path("/category")
public class CategoryController extends AbstractController {

    @Autowired
    private CategoryService categoryService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCategories(
            @QueryParam("search") String search,
            @QueryParam("page") int page,
            @QueryParam("size") int size,
            @QueryParam("asc") Boolean asc,
            @QueryParam("column") String column
    ) {
        try {
            if (search == null && page == 0 && size == 0 && asc == null && column == null)
                return sendSuccessResponse(categoryService.getAllCategoriesWithoutPagination());
            else
                return sendSuccessResponse(categoryService.getAllCategories(search, page, size, asc, column));
        } catch (Exception e) {
            e.printStackTrace();
            return handleServiceException(e);
        }
    }

    @GET
    @Path("/all_categories_with_items")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCategories() {
        try {
            return sendSuccessResponse(categoryService.getAllCategoriesWithItems());
        } catch (Exception e) {
            e.printStackTrace();
            return handleServiceException(e);
        }
    }

    @POST
    @Path("/save_category")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveCategory(CategoryResource categoryResource) {
        try {
            return sendSuccessResponse(categoryService.saveCategory(categoryResource));
        } catch (Exception e) {
            e.printStackTrace();
            return handleServiceException(e);
        }
    }

  /*  @POST
    @Path("/update_category_priority")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCategoryPriority(CategoryResource categoryResource) {
        try {
            return sendSuccessResponse(categoryService.updateCategoryPriority(categoryResource));
        } catch (Exception e) {
            e.printStackTrace();
            return handleServiceException(e);
        }
    }*/

    @DELETE
    @Path("/delete_category")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCategory( @QueryParam("id") int id) {
        try {
            return sendSuccessResponse(categoryService.deleteCategory(id));
        } catch (Exception e) {
            e.printStackTrace();
            return handleServiceException(e);
        }
    }


    @GET
    @Path("/get_items_for_category/{categoryId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItemsForCategory(@PathParam("categoryId") int categoryId) {
        try {
            return sendSuccessResponse(categoryService.getItemsForCategory(categoryId));
        } catch (Exception e) {
            e.printStackTrace();
            return handleServiceException(e);
        }

    }

    @PUT
    @Path("/update_priority/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePriority(CategoryResource array[]) {
        try {
            categoryService.updateAllCategoryPriority(array);
            return sendSuccessResponse(array);
        } catch (ServiceException e) {
            return handleServiceException(e);
        }
    }


    @PUT
    @Path("/update_category/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePriority(CategoryResource categoryResource) {
        try {
            categoryService.updateCategory(categoryResource);
            return sendSuccessResponse(categoryResource);
        } catch (ServiceException e) {
            return handleServiceException(e);
        }
    }
}
