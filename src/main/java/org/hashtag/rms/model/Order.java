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

    @Column(name = "Amount")
    private Double amount;

    @Column(name = "Status")
    private String status;

    @Column(name = "KOTNumber")
    private String kotNumber;

    @Column(name = "Type")
    private Integer type;

    @Column(name = "VoidOrder")
    private Boolean voidOrder;

    @Column(name = "VoidOrderCompleted")
    private Boolean voidOrderCompleted;

    @Column(name = "OpenOrder")
    private Boolean openOrder;

    @Column(name = "Comment")
    private String comment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TableId",
            referencedColumnName = "TableID",
            foreignKey = @ForeignKey(name = "Table_ORDER_FK")
    )
    private TableFlow table;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "UserID",
            referencedColumnName = "UserID",
            foreignKey = @ForeignKey(name = "USER_ORDER_FK")
    )
    private User user;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
//    @JsonManagedReference
    private Collection<OrderDetail> orderDetailList;

    public Order() {
    }

    public Order(Date orderTime, String customerName, Double amount, String status, String kotNumber, Integer type, Boolean voidOrder, Boolean voidOrderCompleted, Boolean openOrder, String comment, TableFlow table, User user) {
        this.orderTime = orderTime;
        this.customerName = customerName;
        this.amount = amount;
        this.status = status;
        this.kotNumber = kotNumber;
        this.type = type;
        this.voidOrder = voidOrder;
        this.voidOrderCompleted = voidOrderCompleted;
        this.openOrder = openOrder;
        this.comment = comment;
        this.table = table;
        this.user = user;
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

    public Boolean getVoidOrder() {
        return voidOrder;
    }

    public void setVoidOrder(Boolean voidOrder) {
        this.voidOrder = voidOrder;
    }

    public Boolean getOpenOrder() {
        return openOrder;
    }

    public void setOpenOrder(Boolean openOrder) {
        this.openOrder = openOrder;
    }

    public Boolean getVoidOrderCompleted() {
        return voidOrderCompleted;
    }

    public void setVoidOrderCompleted(Boolean voidOrderCompleted) {
        this.voidOrderCompleted = voidOrderCompleted;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TableFlow getTable() {
        return table;
    }

    public void setTable(TableFlow table) {
        this.table = table;
    }
}
