package org.hashtag.rms.service;


import org.hashtag.rms.model.Category;
import org.hashtag.rms.model.Item;
import org.hashtag.rms.model.Kitchen;
import org.hashtag.rms.repository.ItemRepository;
import org.hashtag.rms.resource.AdminResource;
import org.hashtag.rms.resource.ItemResource;
import org.hashtag.rms.util.AdminDataUtil;
import org.hashtag.rms.util.rest.DataTableResponse;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * AdminService is for providing services for the item table
 *
 * @author Sugeesh Chandraweera
 */
public class AdminService {

    public Object saveSettings(AdminResource adminResource) {
        AdminDataUtil.save(Double.parseDouble(adminResource.getTax_dinein()),Double.parseDouble(adminResource.getS_charge_dinein()),Double.parseDouble(adminResource.getTax_takeaway()), Double.parseDouble(adminResource.getS_charge_takeaway()));
        return adminResource;
    }

    public Object getTaxDineIn() {
        return AdminDataUtil.getTaxDineIn();
    }

    public Object getSChargeDineIn() {
        return AdminDataUtil.getSChargeDineIn();
    }

    public Object getTaxTakeAway() {
        return AdminDataUtil.getTaxTakeAway();
    }

    public Object getSChargeTakeAway() {
        return AdminDataUtil.getSChargeTakeAway();
    }
}
