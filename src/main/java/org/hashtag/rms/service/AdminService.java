package org.hashtag.rms.service;


import org.hashtag.rms.resource.AdminResource;
import org.hashtag.rms.util.AdminDataUtil;

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
