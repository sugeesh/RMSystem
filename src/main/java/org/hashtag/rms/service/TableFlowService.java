package org.hashtag.rms.service;

import org.hashtag.rms.model.*;
import org.hashtag.rms.repository.PaymentRepository;
import org.hashtag.rms.repository.TableFlowRepository;
import org.hashtag.rms.resource.ItemResource;
import org.hashtag.rms.resource.PaymentResource;
import org.hashtag.rms.resource.TableFlowResource;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;

/**
 * @author Sugeesh Chandraweera
 */
public class TableFlowService {

    @Autowired
    private TableFlowRepository tableFlowRepository;

    public TableFlow getTableById(int tableId) {
        TableFlow tableFlow = tableFlowRepository.findByTableId(tableId);
        return tableFlow;
    }

    public TableFlowResource saveTable(String name) {
        TableFlow tableFlow = new TableFlow();
        tableFlow.setName(name);
        tableFlow.setAvailability(true);
        TableFlow saveTable = tableFlowRepository.save(tableFlow);
        return TableFlowResource.createResource(saveTable);
    }

    public Object getAllTables() {
        return tableFlowRepository.findAll();
    }

    public Object getAllAvailableTables() {
        return tableFlowRepository.findAllByAvailability(true);
    }

    public void updateAvailability(int tableId, boolean value) {
        tableFlowRepository.updateOrderState(tableId, value);
    }

    public void updateTable(int tableId, String name) {
        tableFlowRepository.updateOrderName(tableId, name);
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public TableFlowResource deleteItem(int id) {
        tableFlowRepository.deleteItemByTableId(id);
        return new TableFlowResource(id);
    }

}
