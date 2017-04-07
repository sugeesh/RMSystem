package org.hashtag.rms.model;

import javax.persistence.*;

/**
 * Created by Buddhi on 4/5/2017.
 */

@Entity
@Table(name = "ItemRelation")
public class ItemRelation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ItemRelationID")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ParentItem",
            referencedColumnName = "ItemID",
            foreignKey = @ForeignKey(name = "PARENT_ITEM_FK")
    )
    private Item parentItem;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ChildItem",
            referencedColumnName = "ItemID",
            foreignKey = @ForeignKey(name = "CHILD_ITEM_FK")
    )
    private Item childItem;

    public ItemRelation() {
    }

    public ItemRelation(Item parentItem, Item childItem) {
        this.parentItem = parentItem;
        this.childItem = childItem;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
