package org.hashtag.rms.service;

import org.hashtag.rms.model.Item;
import org.hashtag.rms.model.Order;
import org.hashtag.rms.model.OrderDetail;
import org.hashtag.rms.repository.OrderDetailRepository;
import org.hashtag.rms.repository.OrderRepository;
import org.hashtag.rms.resource.ItemResource;
import org.hashtag.rms.resource.OrderResource;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * @author Sugeesh Chandraweera
 */
public class OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;


    public void create(ItemResource itemResource, Order ordersaved) {
        OrderDetail orderDetail = new OrderDetail();
        Item item = new Item();
        item.setItemId(itemResource.getItemId());

        orderDetail.setItemName(itemResource.getName());
        orderDetail.setItem(item);
        orderDetail.setOrder(ordersaved);
        orderDetail.setPrice(itemResource.getPrice());
        orderDetail.setQuantity(itemResource.getQuantity());

        orderDetailRepository.save(orderDetail);
    }

    public void createOpenOrder(ItemResource itemResource, Order ordersaved) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setItemName(itemResource.getName());
        orderDetail.setOrder(ordersaved);
        orderDetail.setPrice(itemResource.getPrice());
        orderDetail.setQuantity(itemResource.getQuantity());
        orderDetailRepository.save(orderDetail);
    }


}
