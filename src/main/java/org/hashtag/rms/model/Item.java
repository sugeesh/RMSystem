package org.hashtag.rms.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Collection;

/**
 * This is the model class for the Item.
 *
 * @author Sugeesh Chandraweera
 */
@Entity
@Table(name = "Item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ItemID")
    private Integer itemId;

    @Column(name = "SKUCode")
    private String skuCode;

    @Column(name = "ItemName")
    private String name;

    @Column(name = "TaxCode")
    private String taxCode;

    @Column(name = "Price")
    private Double price;

    @Column(name = "Portion")
    private String portion;

    @Column(name = "Comment")
    private String comment;

    @Column(name = "Priority")
    private Integer priority;

    @Column(name = "Active")
    private Boolean active;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CategoryID",
            referencedColumnName = "CategoryID",
            foreignKey = @ForeignKey(name = "ITEM_CATEGORY_FK")
    )
    @JsonBackReference
    private Category category;

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY,orphanRemoval = false)
//    @JsonManagedReference
    private Collection<OrderDetail> orderDetailList;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "KitchenID",
            referencedColumnName = "KitchenID",
            foreignKey = @ForeignKey(name = "ITEM_KITCHEN_FK")
    )
    private Kitchen kitchen;

    @Column(name = "isTakeAway")
    private Boolean isTakeAway;

    public Item() {
    }

    public Item(String skuCode, String name, String taxCode, Double price, String portion, String comment, Integer priority, Boolean active, Category category, Kitchen kitchen, Boolean isTakeAway) {
        this.skuCode = skuCode;
        this.name = name;
        this.taxCode = taxCode;
        this.price = price;
        this.portion = portion;
        this.comment = comment;
        this.priority = priority;
        this.active = active;
        this.category = category;
        this.kitchen = kitchen;
        this.isTakeAway = isTakeAway;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPortion() {
        return portion;
    }

    public void setPortion(String portion) {
        this.portion = portion;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Kitchen getKitchen() {
        return kitchen;
    }

    public void setKitchen(Kitchen kitchen) {
        this.kitchen = kitchen;
    }

    public Collection<OrderDetail> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(Collection<OrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

    public Boolean getTakeAway() {
        return isTakeAway;
    }

    public void setTakeAway(Boolean takeAway) {
        isTakeAway = takeAway;
    }
}
