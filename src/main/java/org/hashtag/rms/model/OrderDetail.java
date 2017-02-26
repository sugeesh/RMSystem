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

    public OrderDetail() {
    }

    public OrderDetail(Double quantity, Double price, Item item, Order order) {
        this.quantity = quantity;
        this.price = price;
        this.item = item;
        this.order = order;
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
}
