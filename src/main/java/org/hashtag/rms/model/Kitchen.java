package org.hashtag.rms.model;

import javax.persistence.*;

/**
 * @author Sugeesh Chandraweera
 */
@Entity
@Table(name = "Kitchen")
public class Kitchen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "KitchenID")
    private Integer kitchenId;

    @Column(name = "Name")
    private String name;

    public Kitchen() {
    }

    public Kitchen(String name) {
        this.name = name;
    }

    public Integer getKitchenId() {
        return kitchenId;
    }

    public void setKitchenId(Integer kitchenId) {
        this.kitchenId = kitchenId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
