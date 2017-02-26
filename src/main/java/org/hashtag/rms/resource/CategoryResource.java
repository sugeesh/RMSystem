package org.hashtag.rms.resource;


import org.hashtag.rms.model.Category;
import org.hashtag.rms.model.Item;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sugeesh Chandraweera
 */
public class CategoryResource {
    private int categoryId;
    private String name;
    private String colorCode;
    private int priority;
    private List<ItemResource> itemResourceList;

    public CategoryResource() {
    }

    public CategoryResource(int categoryId) {
        this.categoryId = categoryId;
    }

    public CategoryResource(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }

    public CategoryResource(int categoryId, String name, String colorCode, int priority) {
        this.categoryId = categoryId;
        this.name = name;
        this.colorCode = colorCode;
        this.priority = priority;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public static CategoryResource createResource(Category category) {
        CategoryResource categoryResource = new CategoryResource();
        categoryResource.setCategoryId(category.getCategoryId());
        categoryResource.setName(category.getName());
        categoryResource.setColorCode(category.getColorCode());
        categoryResource.setPriority(category.getPriority());
        return categoryResource;
    }

    public CategoryResource addItems(Category category) {
        ArrayList<ItemResource> itemResourceList = new ArrayList<>();
        category.getItemList().stream().forEach(item -> itemResourceList.add(ItemResource.createResource(item)));
        this.itemResourceList = itemResourceList;
        return this;
    }

    public List<ItemResource> getItemResourceList() {
        return itemResourceList;
    }

    public void setItemResourceList(List<ItemResource> itemResourceList) {
        this.itemResourceList = itemResourceList;
    }
}
