package org.hashtag.rms.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Collection;

/**
 * This is the model class for the Category.
 *
 * @author Sugeesh Chandraweera
 */

@Entity
@Table(name = "Category")
public class Category{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CategoryID")
    private Integer categoryId;

    @Column(name = "Name")
    private String name;

    @Column(name = "ColorCode")
    private String colorCode;

    @Column(name = "Priority")
    private Integer priority;

    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER,orphanRemoval = false)
//    @JsonManagedReference
    private Collection<Item> itemList;

    public Category() {
    }

    public Category(String name, String colorCode, Integer priority) {
        this.name = name;
        this.colorCode = colorCode;
        this.priority = priority;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
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

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Collection<Item> getItemList() {
        return itemList;
    }

    public void setItemList(Collection<Item> itemList) {
        this.itemList = itemList;
    }
}
