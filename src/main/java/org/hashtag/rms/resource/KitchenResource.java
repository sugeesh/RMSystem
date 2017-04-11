package org.hashtag.rms.resource;


import org.hashtag.rms.model.Category;
import org.hashtag.rms.model.Kitchen;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sugeesh Chandraweera
 */
public class KitchenResource {
    private int kitchenId;
    private String name;

    public KitchenResource() {
    }

    public KitchenResource(int kitchenId, String name) {
        this.kitchenId = kitchenId;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getKitchenId() {
        return kitchenId;
    }

    public void setKitchenId(int kitchenId) {
        this.kitchenId = kitchenId;
    }

    public static KitchenResource createResource(Kitchen kitchen) {
        KitchenResource kitchenResource = new KitchenResource();
        kitchenResource.setKitchenId(kitchen.getKitchenId());
        kitchenResource.setName(kitchen.getName());
        return kitchenResource;
    }

}
