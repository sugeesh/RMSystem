package org.hashtag.rms.resource;

import org.hashtag.rms.model.Item;

/**
 * Created by Buddhi on 4/5/2017.
 */
public class ItemRelationResource {
    private int id;
    private Item parentItem;
    private Item childItem;

    public ItemRelationResource() {
    }

    public ItemRelationResource(int id, Item parentItem, Item childItem) {
        this.id = id;
        this.parentItem = parentItem;
        this.childItem = childItem;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Item getParentItem() {
        return parentItem;
    }

    public void setParentItem(Item parentItem) {
        this.parentItem = parentItem;
    }

    public Item getChildItem() {
        return childItem;
    }

    public void setChildItem(Item childItem) {
        this.childItem = childItem;
    }
}
