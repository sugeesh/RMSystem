package org.hashtag.rms.resource;

import org.hashtag.rms.model.Order;
import org.hashtag.rms.model.Payment;

import java.util.Date;

/**
 * @author Sugeesh Chandraweera
 */
public class PaymentResource {
    private int id;
    private Date date;
    private int type;
    private double amount;
    private double tax;
    private double serviceCharge;
    private double totalAmount;
    private double discount;
    private int orderId;

    public PaymentResource() {
    }

    public PaymentResource(int id, Date date, int type, double amount, double tax, double serviceCharge, double totalAmount, double discount, int orderId) {
        this.id = id;
        this.date = date;
        this.type = type;
        this.amount = amount;
        this.tax = tax;
        this.serviceCharge = serviceCharge;
        this.totalAmount = totalAmount;
        this.discount = discount;
        this.orderId = orderId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(double serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }


    public PaymentResource convertToResource(Payment payment){
        PaymentResource paymentResource = new PaymentResource();
        paymentResource.setOrderId(payment.getOrderForP());
        paymentResource.setType(payment.getType());
        paymentResource.setTotalAmount(payment.getTotalAmount());
        paymentResource.setDiscount(payment.getDiscount());
        paymentResource.setServiceCharge(payment.getServiceCharge());
        paymentResource.setTax(payment.getTax());
        paymentResource.setAmount(payment.getAmount());
        paymentResource.setDate(payment.getDate());
        return paymentResource;
    }



}

