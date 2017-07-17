package org.hashtag.rms.resource;

import org.hashtag.rms.model.TableFlow;


/**
 * @author Sugeesh Chandraweera
 */
public class TableFlowResource {
    private Integer tableId;
    private String name;
    private Boolean availability;

    public TableFlowResource() {
    }

    public TableFlowResource(Integer tableId) {
        this.tableId = tableId;
    }

    public TableFlowResource(Integer tableId, String name, Boolean availability) {
        this.tableId = tableId;
        this.name = name;
        this.availability = availability;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public static TableFlowResource createResource(TableFlow tableFlow) {
        TableFlowResource tableFlowResource = new TableFlowResource();
        tableFlowResource.setTableId(tableFlow.getTableId());
        tableFlowResource.setAvailability(tableFlow.getAvailability());
        tableFlowResource.setName(tableFlow.getName());
        return tableFlowResource;
    }
}
