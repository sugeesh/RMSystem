package org.hashtag.rms.controller;

import org.hashtag.rms.model.Order;
import org.hashtag.rms.model.Payment;
import org.hashtag.rms.resource.OrderResource;
import org.hashtag.rms.resource.PaymentResource;
import org.hashtag.rms.service.ItemService;
import org.hashtag.rms.service.PaymentService;
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
@Path("/payment")
public class PaymentController extends AbstractController{

    @Autowired
    private PaymentService paymentService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response savePayment(PaymentResource paymentResource) throws ParseException {
        Payment payment = paymentService.create(paymentResource);
        return sendSuccessResponse(payment);
    }

    @GET
    @Path("/get_payment_from_order/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPaymentByOrder(@PathParam("id") int id) throws ParseException {
        PaymentResource paymentResource = paymentService.getPaymentByOrderId(id);
        return sendSuccessResponse(paymentResource);
    }
}


