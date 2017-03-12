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
public class OrderController extends AbstractController {
    @Autowired
    private OrderService orderService;

    // Save order for normal order
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveOrder(OrderResource orderObj) throws ParseException {
        orderObj.autoCorrectModel();
        Order order = orderService.create(orderObj);
        return sendSuccessResponse(order);
    }

    // Save order for Open Order
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/open_order")
    public Response saveOpenOrder(OrderResource orderObj) throws ParseException {
        orderObj.autoCorrectModel();
        Order order = orderService.createOpenOrder(orderObj);
        return sendSuccessResponse(order);
    }

    // Get all pending orders for view Pending orders.
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

    // Get all waiting orders for view Pending orders.
    @GET
    @Path("/all_waiting_orders")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllWaitingOrders() {
        try {
            return sendSuccessResponse(orderService.getAllWaitingOrders());
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

    @GET
    @Path("/get_orders_for_date_range/{startDate}/{endDate}/{type}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrdersForDateRange(@PathParam("startDate") String startDate,
                                          @PathParam("endDate") String endDate,
                                          @PathParam("type") int type) {
        try {
            return sendSuccessResponse(orderService.getOrdersForDateRange(startDate, endDate, type));
        } catch (Exception e) {
            e.printStackTrace();
            return handleServiceException(e);
        }
    }

    @GET
    @Path("/get_orders_for_date/{date}/{type}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrdersForDateRange(@PathParam("date") String date,
                                          @PathParam("type") int type) {
        try {
            return sendSuccessResponse(orderService.getOrdersForDate(date, type));
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

    @PUT
    @Path("/update_void_order/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateVoidOrder(OrderResource orderResource) {
        try {
            orderService.updateVoidOrder(orderResource);
            return sendSuccessResponse(orderResource);
        } catch (ServiceException e) {
            return handleServiceException(e);
        }
    }


    @PUT
    @Path("/approve_waiting_order/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response approveOrder(OrderResource orderResource) {
        try {
            orderService.approveOrder(orderResource);
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
