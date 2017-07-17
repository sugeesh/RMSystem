package org.hashtag.rms.service;

import org.hashtag.rms.model.Item;
import org.hashtag.rms.model.Order;
import org.hashtag.rms.model.OrderDetail;
import org.hashtag.rms.repository.ItemRepository;
import org.hashtag.rms.repository.OrderDetailRepository;
import org.hashtag.rms.resource.ItemResource;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Sugeesh Chandraweera
 */
public class OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ItemRepository itemRepository;


    @Autowired
    private KitchenService kitchenService;


    public void create(ItemResource itemResource, Order ordersaved) {
        OrderDetail orderDetail = new OrderDetail();
        Item item = new Item();
        item.setItemId(itemResource.getItemId());

        orderDetail.setItemName(itemResource.getName());
        orderDetail.setItem(item);
        orderDetail.setOrder(ordersaved);
        orderDetail.setPrice(itemResource.getPrice());
        orderDetail.setQuantity(itemResource.getQuantity());
        orderDetail.setServed(false);
        orderDetail.setKitchen(itemRepository.findByItemId(itemResource.getItemId()).getKitchen());
        orderDetailRepository.save(orderDetail);
    }

    public void createOpenOrder(ItemResource itemResource, Order ordersaved) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setItemName(itemResource.getName());
        orderDetail.setOrder(ordersaved);
        orderDetail.setPrice(itemResource.getPrice());
        orderDetail.setQuantity(itemResource.getQuantity());
        orderDetail.setComment(itemResource.getComment());
        orderDetail.setKitchen(kitchenService.getKitchenById(itemResource.getKitchenId()));
        orderDetail.setServed(false);
        orderDetailRepository.save(orderDetail);
    }


    public void updateOrderServed(int orderDetailId){
        orderDetailRepository.updateOrderState(orderDetailId,true);
    }

    public List<OrderDetail> findByItem(Item item) {
        List<OrderDetail> allByItem = orderDetailRepository.findAllByItem(item);
        return allByItem;
    }

    public List<OrderDetail> findByOrder(Order order) {
        List<OrderDetail> allByOrder = orderDetailRepository.findAllByOrder(order);
        return allByOrder;
    }

    public int getNumberofKitchens(Order order){
        List<OrderDetail> allByOrder = orderDetailRepository.findAllByOrder(order);
        Set<Integer> set = new HashSet<Integer>();
        for(OrderDetail orderDetail : allByOrder){
            set.add(orderDetail.getKitchen().getKitchenId());
        }
        return set.size();
    }

    public int getNumberofServedKitchens(Order order){
        List<OrderDetail> allByOrder = orderDetailRepository.findAllByOrder(order);
        int count = 0;
        for(OrderDetail orderDetail : allByOrder){
            if(orderDetail.getServed())
                count++;
        }
        return count;
    }

}
