package org.hashtag.rms.resource;

import javax.persistence.Column;
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
    private String state;
    private List<ItemResource> itemResourceList;
    private Object itemResourceList1[];
    private String kotNumber;
    private int type;
    private String comment;

    public OrderResource() {
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


    public void autoCorrectModel(){
        ArrayList<ItemResource> itemResourceList = new ArrayList<>();
        Arrays.stream(itemResourceList1).forEach(item -> {
            LinkedHashMap item1 = (LinkedHashMap) item;
            ItemResource itemResource = new ItemResource();
            itemResource.setItemId((Integer) item1.get("itemId"));
            itemResource.setName((String) item1.get("name"));
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
}
