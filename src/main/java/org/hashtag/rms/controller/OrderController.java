package org.hashtag.rms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hashtag.rms.model.Order;
import org.hashtag.rms.resource.CategoryResource;
import org.hashtag.rms.resource.OrderResource;
import org.hashtag.rms.service.ItemService;
import org.hashtag.rms.service.OrderService;
import org.hashtag.rms.util.exceptions.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;

/**
 * @author Sugeesh Chandraweera
 */
@Component
@Path("/order")
public class OrderController extends AbstractController{
    @Autowired
    private OrderService orderService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllOrders() {
        System.out.println("A");
        return null;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveOrder( OrderResource orderObj) throws ParseException {
        orderObj.autoCorrectModel();
        Order order = orderService.create(orderObj);
        return sendSuccessResponse(order);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/open_order")
    public Response saveOpenOrder( OrderResource orderObj) throws ParseException {
        orderObj.autoCorrectModel();
        Order order = orderService.createOpenOrder(orderObj);
        return sendSuccessResponse(order);
    }

    @GET
    @Path("/all_pending_orders")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPendingOrders() {
        try {
            return sendSuccessResponse(orderService.getAllPendingOrders());
        } catch (Exception e) {
            e.printStackTrace();
            return handleServiceException(e);
        }
    }

    @GET
    @Path("/all_served_orders")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllServedOrders() {
        try {
            return sendSuccessResponse(orderService.getAllServedOrders());
        } catch (Exception e) {
            e.printStackTrace();
            return handleServiceException(e);
        }
    }

    @GET
    @Path("/all_completed_orders")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCompletedOrders() {
        try {
            return sendSuccessResponse(orderService.getAllCompletedOrders());
        } catch (Exception e) {
            e.printStackTrace();
            return handleServiceException(e);
        }
    }


    @GET
    @Path("/get_order_from_id/{id}/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrderById(@PathParam("id") int id) {
        try {
            return sendSuccessResponse(orderService.getOrder(id));
        } catch (Exception e) {
            e.printStackTrace();
            return handleServiceException(e);
        }
    }

    @PUT
    @Path("/update_order/{id}/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(OrderResource orderResource, @PathParam("id") int id) throws ParseException {
        try {
            Order update = orderService.update(orderResource, id);
            return sendSuccessResponse(update);
        } catch (ServiceException e) {
            return handleServiceException(e);
        }
    }

    @PUT
    @Path("/update_status_order/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateOrderState(OrderResource orderResource) {
        try {
            orderService.updateOrderState(orderResource);
            return sendSuccessResponse(orderResource);
        } catch (ServiceException e) {
            return handleServiceException(e);
        }
    }


    @GET
    @Path("/get_next_kot/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNextKOT() {
        try {
            return sendSuccessResponse(orderService.getNextKOT());
        } catch (ServiceException e) {
            return handleServiceException(e);
        }
    }

}
