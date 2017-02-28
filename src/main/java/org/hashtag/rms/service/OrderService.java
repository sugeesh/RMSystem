package org.hashtag.rms.service;

import org.hashtag.rms.model.Category;
import org.hashtag.rms.model.Order;
import org.hashtag.rms.repository.ItemRepository;
import org.hashtag.rms.repository.OrderDetailRepository;
import org.hashtag.rms.repository.OrderRepository;
import org.hashtag.rms.resource.CategoryResource;
import org.hashtag.rms.resource.ItemResource;
import org.hashtag.rms.resource.OrderResource;
import org.hashtag.rms.util.KOTNumberGenerator;
import org.hashtag.rms.util.rest.DataTableResponse;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author Sugeesh Chandraweera
 */
@SuppressWarnings("ALL")
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;


    @Autowired
    private OrderDetailService orderDetailService;

    @Transactional(propagation = Propagation.REQUIRED)
    public Order create(OrderResource orderResource) throws ServiceException, ParseException {

        Order order = new Order();
        order.setOrderTime(orderResource.getOrderTime());
        order.setAmount(orderResource.getAmount());
        order.setCustomerName(orderResource.getCustomerName());
        order.setTableId(orderResource.getTableId());
        order.setType(orderResource.getType());
        order.setComment(orderResource.getComment());

        //Set KOT Number
        List<Order> allByOrderByKotNumber = orderRepository.findAllByOrderByKotNumber();
        String nextKOTNumber = "KOT0001";
        if(allByOrderByKotNumber.size()!=0) {
            nextKOTNumber = KOTNumberGenerator.getNextKOTNumber(allByOrderByKotNumber.get(allByOrderByKotNumber.size() - 1).getKotNumber());
        }
        order.setKotNumber(nextKOTNumber);

        order.setStatus("PENDING");
        try {
            Order ordersaved = orderRepository.save(order);
            ArrayList<ItemResource> itemResourceList = (ArrayList<ItemResource>) orderResource.getItemResourceList();
            itemResourceList.stream().forEach(itemResource -> {
                orderDetailService.create(itemResource,ordersaved);
            });
            return ordersaved;
        } catch (DataAccessException e) {
            throw new org.hibernate.service.spi.ServiceException("Failed to save the Order", e);
        }
    }

    public DataTableResponse<OrderResource> getAllPendingOrders() {
        DataTableResponse<OrderResource> response = new DataTableResponse<>();
        List<OrderResource> orderList = new ArrayList<>();
        for (Order order : orderRepository.findByStatus("PENDING")) {
            OrderResource orderResource = new OrderResource();
            orderResource.setOrderId(order.getOrderId());
            orderResource.setTableId(order.getTableId());
            orderResource.setCustomerName(order.getCustomerName());
            orderResource.setOrderTime(order.getOrderTime());
            orderResource.setKotNumber(order.getKotNumber());
            orderResource.setComment(order.getComment());
            orderResource.setType(order.getType());
            orderResource.setAmount(order.getAmount());
            orderResource.setItemResourceList(orderResource.getItemResourceList());
            orderList.add(orderResource);
        }
        response.setDataRows(orderList);
        response.setEntries(orderList.size());
        return response;
    }

    public DataTableResponse<OrderResource> getAllServedOrders() {
        DataTableResponse<OrderResource> response = new DataTableResponse<>();
        List<OrderResource> orderList = new ArrayList<>();
        for (Order order : orderRepository.findByStatus("SERVED")) {
            OrderResource orderResource = new OrderResource();
            orderResource.setOrderId(order.getOrderId());
            orderResource.setTableId(order.getTableId());
            orderResource.setCustomerName(order.getCustomerName());
            orderResource.setOrderTime(order.getOrderTime());
            orderResource.setKotNumber(order.getKotNumber());
            orderResource.setComment(order.getComment());
            orderResource.setType(order.getType());
            orderResource.setAmount(order.getAmount());
            orderResource.setItemResourceList(orderResource.getItemResourceList());
            orderList.add(orderResource);
        }
        response.setDataRows(orderList);
        response.setEntries(orderList.size());
        return response;
    }

    public Order getPlainOrderObject(int id){
        Order orderNew = orderRepository.findByOrderId(id);
        return orderNew;
    }


    public OrderResource getOrder(int id) {
        OrderResource orderResource = new OrderResource();
        Order orderNew = getPlainOrderObject(id);
        orderResource.setOrderId(orderNew.getOrderId());
        orderResource.setTableId(orderNew.getTableId());
        orderResource.setAmount(orderNew.getAmount());
        orderResource.setOrderTime(orderNew.getOrderTime());
        orderResource.setKotNumber(orderNew.getKotNumber());
        orderResource.setCustomerName(orderNew.getCustomerName());
        orderResource.setComment(orderNew.getComment());
        orderResource.setType(orderNew.getType());
        List<ItemResource> itemResourceList = new ArrayList<>();
        orderNew.getOrderDetailList().stream().forEach(orderDetail -> {
            ItemResource itemResource = new ItemResource();
            itemResource.setItemId(orderDetail.getItem().getItemId());
            itemResource.setPrice(orderDetail.getPrice());
            itemResource.setQuantity(orderDetail.getQuantity());
            itemResource.setName(orderDetail.getItem().getName());
            itemResource.setTaxCode(orderDetail.getItem().getTaxCode());
            itemResource.setComment(orderDetail.getItem().getComment());
            itemResource.setPortion(orderDetail.getItem().getPortion());
            itemResource.setSkuCode(orderDetail.getItem().getSkuCode());
            itemResourceList.add(itemResource);
        });
        orderResource.setItemResourceList(itemResourceList);
        return orderResource;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Order update(OrderResource orderResource, int id) {
        orderRepository.deleteByOrderId(id);
        orderResource.autoCorrectModel();

        Order order = new Order();
        order.setOrderId(orderResource.getOrderId());
        order.setOrderTime(orderResource.getOrderTime());
        order.setAmount(orderResource.getAmount());
        order.setCustomerName(orderResource.getCustomerName());
        order.setTableId(orderResource.getTableId());
        order.setKotNumber(orderResource.getKotNumber());
        order.setType(orderResource.getType());
        order.setComment(orderResource.getComment());

        order.setStatus("PENDING");
        try {
            Order ordersaved = orderRepository.save(order);
            ArrayList<ItemResource> itemResourceList = (ArrayList<ItemResource>) orderResource.getItemResourceList();
            itemResourceList.stream().forEach(itemResource -> {
                orderDetailService.create(itemResource,ordersaved);
            });
            return ordersaved;
        } catch (DataAccessException e) {
            throw new org.hibernate.service.spi.ServiceException("Failed to save the DownStream", e);
        }
    }

    public int updateOrderState(OrderResource orderResource) {
        return updateState(orderResource.getOrderId(),orderResource.getState());
    }

    public int updateState(int id,String state){
        Integer integer = orderRepository.updateOrderState(id,state);
        return integer;
    }

    public DataTableResponse<OrderResource> getAllCompletedOrders() {
        DataTableResponse<OrderResource> response = new DataTableResponse<>();
        List<OrderResource> orderList = new ArrayList<>();
        for (Order order : orderRepository.findByStatus("COMPLETED")) {
            OrderResource orderResource = new OrderResource();
            orderResource.setOrderId(order.getOrderId());
            orderResource.setTableId(order.getTableId());
            orderResource.setCustomerName(order.getCustomerName());
            orderResource.setOrderTime(order.getOrderTime());
            orderResource.setKotNumber(order.getKotNumber());
            orderResource.setComment(order.getComment());
            orderResource.setType(order.getType());
            orderResource.setAmount(order.getAmount());
            orderResource.setItemResourceList(orderResource.getItemResourceList());
            orderList.add(orderResource);
        }
        response.setDataRows(orderList);
        response.setEntries(orderList.size());
        return response;
    }

    public OrderResource getNextKOT() {
        List<Order> allByOrderByKotNumber = orderRepository.findAllByOrderByKotNumber();
        String nextKOTNumber = "KOT0001";
        if(allByOrderByKotNumber.size()!=0) {
            nextKOTNumber = KOTNumberGenerator.getNextKOTNumber(allByOrderByKotNumber.get(allByOrderByKotNumber.size() - 1).getKotNumber());
        }
        return new OrderResource(nextKOTNumber);
    }
}
