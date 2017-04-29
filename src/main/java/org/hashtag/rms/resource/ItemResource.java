package org.hashtag.rms.resource;


import org.hashtag.rms.model.Item;

/**
 * ItemResource is for sending the item details to the frontend.
 *
 * @author Sugeesh Chandraweera
 */
public class ItemResource {
    private int itemId;
    private String skuCode;
    private String name;
    private String taxCode;
    private double price;
    private String portion;
    private String comment;
    private int priority;
    private int categoryId;
    private double quantity;
    private boolean active;
    private Item item;
    private int kitchenId;
    private int isTakeAway;


    public ItemResource() {
    }

    public ItemResource(int itemId, String name, double price) {
        this.itemId = itemId;
        this.name = name;
        this.price = price;
    }

    public ItemResource(int itemId, String skuCode, String name, String taxCode, double price, String portion, String comment, int priority) {
        this.itemId = itemId;
        this.skuCode = skuCode;
        this.name = name;
        this.taxCode = taxCode;
        this.price = price;
        this.portion = portion;
        this.comment = comment;
        this.priority = priority;
    }


    public ItemResource(int id) {
        this.itemId = id;

    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
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

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getKitchenId() {
        return kitchenId;
    }

    public void setKitchenId(int kitchenId) {
        this.kitchenId = kitchenId;
    }
    public void setItem(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public int getIsTakeAway() {
        return isTakeAway;
    }

    public void setIsTakeAway(int isTakeAway) {
        this.isTakeAway = isTakeAway;
    }

    public static ItemResource createResource(Item item) {
        ItemResource itemResource = new ItemResource();
        itemResource.setItemId(item.getItemId());
        itemResource.setName(item.getName());
        itemResource.setPortion(item.getPortion());
        itemResource.setPrice(item.getPrice());
        itemResource.setSkuCode(item.getSkuCode());
        itemResource.setComment(item.getComment());
        itemResource.setTaxCode(item.getTaxCode());
        itemResource.setActive(item.getActive());
        itemResource.setPriority(item.getPriority());
        itemResource.setKitchenId(item.getKitchen().getKitchenId());
        if(item.getTakeAway()){
            itemResource.setIsTakeAway(1);
        }else {
            itemResource.setIsTakeAway(0);
        }

        return itemResource;
    }


}
