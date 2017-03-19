package org.hashtag.rms.controller;

import org.hashtag.rms.model.CashDrawer;
import org.hashtag.rms.model.Payment;
import org.hashtag.rms.resource.CashDrawerResource;
import org.hashtag.rms.resource.PaymentResource;
import org.hashtag.rms.service.CashDrawerService;
import org.hashtag.rms.service.PaymentService;
import org.hashtag.rms.util.exceptions.ServiceException;
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
    @Path("get_cash_drawer_for_today")
    public Response getCashDrawerForToday() throws ParseException {
        CashDrawer cashDrawer = cashDrawerService.getCashDrawerForToday();
        return sendSuccessResponse(cashDrawer);
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("get_cash_drawer_for_yesterday")
    public Response getCashDrawerForYesterday() throws ParseException {
        CashDrawer cashDrawer = cashDrawerService.getCashDrawerForYesterday();
        return sendSuccessResponse(cashDrawer);
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/get_cash_drawer_for_date_range/{startDate}/{endDate}")
    public Response getCashDrawerForDateRange(@PathParam("startDate") String startDate, @PathParam("endDate") String endDate) {
        try {
            return sendSuccessResponse(cashDrawerService.getCashDrawerForDateRange(startDate, endDate));
        } catch (Exception e) {
            e.printStackTrace();
            return handleServiceException(e);
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/save_cash_drawer")
    public Response saveCashDrawer(CashDrawerResource cashDrawerResource) {
        try {
            return sendSuccessResponse(cashDrawerService.saveCashDrawer(cashDrawerResource));
        } catch (Exception e) {
            e.printStackTrace();
            return handleServiceException(e);
        }
    }

    @PUT
    @Path("/update_cash_drawer")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateOrderState(CashDrawerResource cashDrawerResource) {
        try {
            return sendSuccessResponse(cashDrawerService.updateCashDrawer(cashDrawerResource));
        } catch (ServiceException e) {
            return handleServiceException(e);
        }
    }
}


