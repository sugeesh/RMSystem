package org.hashtag.rms.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Collection;

/**
 * This is the model class for the Item.
 *
 * @author Sugeesh Chandraweera
 */
@Entity
@Table(name = "TableFlow")
public class TableFlow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TableID")
    private Integer tableId;

    @Column(name = "Name")
    private String name;

    @Column(name = "availability")
    private Boolean availability;


    public TableFlow() {
    }

    public TableFlow(Boolean availability) {
        this.availability = availability;
    }

    public Integer getTableId() {
        return tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
