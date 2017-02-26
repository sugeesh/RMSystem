package org.hashtag.rms.model;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Sugeesh Chandraweera
 */
@Entity
@Table(name = "Payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PaymentID")
    private Integer id;

    @Column(name = "Date")
    private Date date;

    @Column(name = "Type")
    private Integer type;

    @Column(name = "Amount")
    private Double amount;

    @Column(name = "Tax")
    private Double tax;

    @Column(name = "ServiceCharge")
    private Double serviceCharge;

    @Column(name = "TotalAmount")
    private Double totalAmount;

    @Column(name = "Discount")
    private Double discount;

    @Column(name = "orderForP")
    private int orderForP;


    public Payment() {
    }

    public Payment(Date date, Integer type, Double amount, Double tax, Double serviceCharge, Double totalAmount, Double discount, int orderForP) {
        this.date = date;
        this.type = type;
        this.amount = amount;
        this.tax = tax;
        this.serviceCharge = serviceCharge;
        this.totalAmount = totalAmount;
        this.discount = discount;
        this.orderForP = orderForP;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public Double getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(Double serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getOrderForP() {
        return orderForP;
    }

    public void setOrderForP(int orderForP) {
        this.orderForP = orderForP;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }
}
