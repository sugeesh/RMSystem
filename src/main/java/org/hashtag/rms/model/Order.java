package org.hashtag.rms.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

/**
 * @author Sugeesh Chandraweera
 */
@Entity
@Table(name = "Orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "OrderID")
    private Integer orderId;

    @Column(name = "OrderTime")
    private Date orderTime;

    @Column(name = "CustomerName")
    private String customerName;

    @Column(name = "TableId")
    private String tableId;

    @Column(name = "Amount")
    private Double amount;

    @Column(name = "Status")
    private String status;

    @Column(name = "KOTNumber")
    private String kotNumber;

    @Column(name = "Type")
    private Integer type;

    @Column(name = "Comment")
    private String comment;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
//    @JsonManagedReference
    private Collection<OrderDetail> orderDetailList;

    public Order() {
    }

    public Order(Date orderTime, String customerName, String tableId, Double amount, String status, String kotNumber, Integer type, String comment) {
        this.orderTime = orderTime;
        this.customerName = customerName;
        this.tableId = tableId;
        this.amount = amount;
        this.status = status;
        this.kotNumber = kotNumber;
        this.type = type;
        this.comment = comment;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Collection<OrderDetail> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(Collection<OrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

    public String getKotNumber() {
        return kotNumber;
    }

    public void setKotNumber(String kotNumber) {
        this.kotNumber = kotNumber;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
