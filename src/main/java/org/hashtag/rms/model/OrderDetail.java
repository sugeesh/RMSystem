package org.hashtag.rms.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

/**
 * @author Sugeesh Chandraweera
 */
@Entity
@Table(name = "OrderDetail")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderDetailID")
    private Integer id;

    @Column(name = "Quantity")
    private Double quantity;

    @Column(name = "UnitPrice")
    private Double price;

    @Column(name = "ItemName")
    private String itemName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ItemID",
            referencedColumnName = "ItemID",
            foreignKey = @ForeignKey(name = "ITEM_ODD_FK")
    )
//    @JsonBackReference
    private Item item;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "OrderID",
            referencedColumnName = "OrderID",
            foreignKey = @ForeignKey(name = "ITEM_ORDER_FK")
    )
//    @JsonBackReference
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "KitchenID",
            referencedColumnName = "KitchenID",
            foreignKey = @ForeignKey(name = "ORDERDETAIL_KITCHEN_FK")
    )
    private Kitchen kitchen;

    @Column(name = "comment")
    private String comment;

    @Column(name = "isServed")
    private Boolean isServed;

    public OrderDetail() {
    }

    public OrderDetail(Double quantity, Double price, String itemName, Item item, Order order, Kitchen kitchen, String comment, Boolean isServed) {
        this.quantity = quantity;
        this.price = price;
        this.itemName = itemName;
        this.item = item;
        this.order = order;
        this.kitchen = kitchen;
        this.comment = comment;
        this.isServed = isServed;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean getServed() {
        return isServed;
    }

    public void setServed(Boolean served) {
        isServed = served;
    }

    public Kitchen getKitchen() {
        return kitchen;
    }

    public void setKitchen(Kitchen kitchen) {
        this.kitchen = kitchen;
    }
}
