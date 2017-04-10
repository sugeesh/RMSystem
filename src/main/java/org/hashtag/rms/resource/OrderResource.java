package org.hashtag.rms.resource;

import java.text.ParseException;
import java.util.*;

/**
 * @author Sugeesh Chandraweera
 */
public class OrderResource {
    private int orderId;
    private Date orderTime;
    private double amount;
    private String customerName;
    private String tableId;

    // To check order is in "PENDING", "COMPLETED" OR "WAIT" state
    private String state;
    private Boolean voidOrder;
    private Boolean openOrder;

    private List<ItemResource> itemResourceList;
    private Object itemResourceList1[];
    private Object paymentDetails1;
    private PaymentResource paymentDetails;
    private String kotNumber;
    private int type;
    private String comment;
    private String userId;
    private String userName;

    private int kId;

    public OrderResource() {
    }

    public OrderResource(String kotNumber) {
        this.kotNumber = kotNumber;
    }

    public OrderResource(Date orderTime, double amount) {
        this.orderTime = orderTime;
        this.amount = amount;
    }

    public OrderResource(Date orderTime, double amount, Object[] itemResourceList1) {
        this.orderTime = orderTime;
        this.amount = amount;
        this.itemResourceList1 = itemResourceList1;
    }

    public OrderResource(Date orderTime, double amount, String customerName, String tableId, Object[] itemResourceList1) {
        this.orderTime = orderTime;
        this.amount = amount;
        this.customerName = customerName;
        this.tableId = tableId;
        this.itemResourceList1 = itemResourceList1;
    }


    public void autoCorrectModel() throws ParseException {
        ArrayList<ItemResource> itemResourceList = new ArrayList<>();
        Arrays.stream(itemResourceList1).forEach(item -> {
            LinkedHashMap item1 = (LinkedHashMap) item;
            ItemResource itemResource = new ItemResource();
            itemResource.setItemId((Integer) item1.get("itemId"));
            itemResource.setName((String) item1.get("name"));
            itemResource.setComment((String) item1.get("comment"));
            try{
                itemResource.setPrice(Double.parseDouble(item1.get("price").toString()));
            }catch (java.lang.ClassCastException e){
                itemResource.setPrice(Integer.parseInt(item1.get("price").toString()));
            }
            try{
                itemResource.setQuantity(Double.parseDouble(item1.get("quantity").toString()));
            }catch (java.lang.ClassCastException e){
                itemResource.setQuantity(Integer.parseInt(item1.get("quantity").toString()));
            }
            itemResourceList.add(itemResource);
        });
        PaymentResource paymentResource = new PaymentResource();
        LinkedHashMap paymentDetailsHash = (LinkedHashMap) this.paymentDetails1;

        paymentResource.setDate(new java.util.Date(Long.parseLong(paymentDetailsHash.get("date").toString())*1000));
        try{
            paymentResource.setAmount((Double) paymentDetailsHash.get("amount"));
        }catch (java.lang.ClassCastException e){
            paymentResource.setAmount((Integer) paymentDetailsHash.get("amount"));
        }
        try{
            paymentResource.setTax((Double) paymentDetailsHash.get("tax"));
        }catch (java.lang.ClassCastException e){
            paymentResource.setTax((Integer) paymentDetailsHash.get("tax"));
        }
        try{
            paymentResource.setDiscount((Double) paymentDetailsHash.get("discount"));
        }catch (java.lang.ClassCastException e){
            paymentResource.setDiscount((Integer) paymentDetailsHash.get("discount"));
        }
        try{
            paymentResource.setServiceCharge((Double) paymentDetailsHash.get("serviceCharge"));
        }catch (java.lang.ClassCastException e){
            paymentResource.setServiceCharge((Integer) paymentDetailsHash.get("serviceCharge"));
        }
        try{
            paymentResource.setTotalAmount((Double) paymentDetailsHash.get("totalAmount"));
        }catch (java.lang.ClassCastException e){
            paymentResource.setTotalAmount((Integer) paymentDetailsHash.get("totalAmount"));
        }

        paymentResource.setType((Integer) paymentDetailsHash.get("type"));
        this.paymentDetails = paymentResource;
        //paymentResource.setDate();
        this.itemResourceList = itemResourceList;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public List<ItemResource> getItemResourceList() {
        return itemResourceList;

    }

    public void setItemResourceList(List<ItemResource> itemResourceList) {
        this.itemResourceList = itemResourceList;
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

    public Object[] getItemResourceList1() {
        return itemResourceList1;
    }

    public void setItemResourceList1(Object[] itemResourceList1) {
        this.itemResourceList1 = itemResourceList1;
    }


    public String getKotNumber() {
        return kotNumber;
    }

    public void setKotNumber(String kotNumber) {
        this.kotNumber = kotNumber;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Object getPaymentDetails1() {
        return paymentDetails1;
    }

    public void setPaymentDetails1(Object paymentDetails1) {
        this.paymentDetails1 = paymentDetails1;
    }

    public PaymentResource getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(PaymentResource paymentDetails) {
        this.paymentDetails = paymentDetails;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getkId() {
        return kId;
    }

    public void setkId(int kId) {
        this.kId = kId;
    }
}
