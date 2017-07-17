package org.hashtag.rms.service;


import org.hashtag.rms.model.Payment;
import org.hashtag.rms.repository.PaymentRepository;
import org.hashtag.rms.resource.PaymentResource;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;

/**
 * @author Sugeesh Chandraweera
 */
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderService orderService;

    @Transactional(propagation = Propagation.REQUIRED)
    public Payment create(PaymentResource paymentResource) throws ServiceException, ParseException {
        Payment payment = new Payment();
        payment.setType(paymentResource.getType());
        payment.setAmount(paymentResource.getAmount());
        payment.setDate(paymentResource.getDate());
        payment.setServiceCharge(paymentResource.getServiceCharge());
        payment.setTax(paymentResource.getTax());
        payment.setDiscount(paymentResource.getDiscount());
        payment.setTotalAmount(paymentResource.getTotalAmount());
        int orderId = paymentResource.getOrderId();
        //payment.setOrder(orderService.getPlainOrderObject(orderId));
        payment.setOrderForP(orderId);
        try{
            Payment save = paymentRepository.save(payment);
//            orderService.updateState(orderId,"COMPLETED");
            return save;
        } catch (DataAccessException e) {
            throw new org.hibernate.service.spi.ServiceException("Failed to save the Payment", e);
        }


    }

    public PaymentResource getPaymentByOrderId(int orderId) {
        Payment byOrderForP = paymentRepository.findByOrderForP(orderId);
        PaymentResource paymentResource = new PaymentResource();
        paymentResource = paymentResource.convertToResource(byOrderForP);
        return  paymentResource;
    }
}
