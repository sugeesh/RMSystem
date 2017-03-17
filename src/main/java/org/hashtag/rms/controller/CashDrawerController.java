package org.hashtag.rms.controller;

import org.hashtag.rms.model.CashDrawer;
import org.hashtag.rms.model.Payment;
import org.hashtag.rms.resource.CashDrawerResource;
import org.hashtag.rms.resource.PaymentResource;
import org.hashtag.rms.service.CashDrawerService;
import org.hashtag.rms.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author Sugeesh Chandraweera
 */
@Component
@Path("/cashdrawer")
public class CashDrawerController extends AbstractController {

    @Autowired
    private CashDrawerService cashDrawerService;

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/get_cash_drawer_for_date/{date}")
    public Response getCashDrawerForDate(@PathParam("date") String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        CashDrawer cashDrawer = cashDrawerService.getCashDrawerForDate(dateFormat.parse(date));
        return sendSuccessResponse(cashDrawer);
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/get_cash_drawer_for_date_range/{startDate}/{endDate}")
    public Response saveCashDrawer(@PathParam("startDate") String startDate, @PathParam("endDate") String endDate) {
        try {
            return sendSuccessResponse(cashDrawerService.getCashDrawerForDateRange(startDate, endDate));
        } catch (Exception e) {
            e.printStackTrace();
            return handleServiceException(e);
        }
    }
}


