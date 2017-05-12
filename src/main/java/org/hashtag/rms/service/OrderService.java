package org.hashtag.rms.service;

import org.hashtag.rms.model.*;
import org.hashtag.rms.repository.ItemRepository;
import org.hashtag.rms.repository.OrderDetailRepository;
import org.hashtag.rms.repository.OrderRepository;
import org.hashtag.rms.resource.CategoryResource;
import org.hashtag.rms.resource.ItemResource;
import org.hashtag.rms.resource.OrderResource;
import org.hashtag.rms.resource.PaymentResource;
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
import java.util.*;

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
    private PaymentService paymentService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private UserService userService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private TableFlowService tableFlowService;

    /**
     * This method is for insert normal order for the table.
     *
     * @param orderResource
     * @return
     * @throws ServiceException
     * @throws ParseException
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public Order create(OrderResource orderResource) throws ServiceException, ParseException {

        Order order = new Order();
        order.setAmount(orderResource.getAmount());
        order.setCustomerName(orderResource.getCustomerName());
        order.setTable(tableFlowService.getTableById(Integer.parseInt(orderResource.getTableId())));
        order.setType(orderResource.getType());
        order.setComment(orderResource.getComment());
        order.setOpenOrder(orderResource.getOpenOrder());
        order.setVoidOrder(orderResource.getVoidOrder());

        // Get User Object
        User userByUserId = userService.getUserByUserId(orderResource.getUserId());
        order.setUser(userByUserId);

        //SetTableFlow UnAvailable
        tableFlowService.updateAvailability(Integer.parseInt(orderResource.getTableId()),false);

        //Set KOT Number
        String nextKOTNumber = KOTNumberGenerator.getNextKOTNumber();
        KOTNumberGenerator.increaseOrderId(nextKOTNumber);

        order.setKotNumber(nextKOTNumber);

        order.setStatus("PENDING");
        order.setOrderTime(new Date());
        try {
            Order ordersaved = orderRepository.save(order);
            ArrayList<ItemResource> itemResourceList = (ArrayList<ItemResource>) orderResource.getItemResourceList();
            itemResourceList.stream().forEach(itemResource -> {
                orderDetailService.create(itemResource, ordersaved);
            });

            PaymentResource paymentDetails = orderResource.getPaymentDetails();
            paymentDetails.setOrderId(ordersaved.getOrderId());
            paymentService.create(paymentDetails);
            return ordersaved;
        } catch (DataAccessException e) {
            throw new org.hibernate.service.spi.ServiceException("Failed to save the Order", e);
        }
    }

    /**
     * This metod will insert open order to the table.
     *
     * @param orderResource
     * @return
     * @throws ServiceException
     * @throws ParseException
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public Order createOpenOrder(OrderResource orderResource) throws ServiceException, ParseException {

        Order order = new Order();
//        order.setOrderTime(orderResource.getOrderTime());
        order.setAmount(orderResource.getAmount());
        order.setCustomerName(orderResource.getCustomerName());
        order.setTable(tableFlowService.getTableById(Integer.parseInt(orderResource.getTableId())));
        order.setType(orderResource.getType());
        order.setComment(orderResource.getComment());
        order.setOpenOrder(orderResource.getOpenOrder());
        order.setVoidOrder(orderResource.getVoidOrder());

        // Get User Object
        User userByUserId = userService.getUserByUserId(orderResource.getUserId());
        order.setUser(userByUserId);

        //SetTableFlow UnAvailable
        tableFlowService.updateAvailability(Integer.parseInt(orderResource.getTableId()),false);

        //Set KOT Number
        String nextKOTNumber = KOTNumberGenerator.getNextKOTNumber();
        KOTNumberGenerator.increaseOrderId(nextKOTNumber);

        order.setKotNumber(nextKOTNumber);
        order.setStatus("WAITING");
        order.setOrderTime(new Date());

        try {
            Order ordersaved = orderRepository.save(order);
            ArrayList<ItemResource> itemResourceList = (ArrayList<ItemResource>) orderResource.getItemResourceList();
            itemResourceList.stream().forEach(itemResource -> {
                if (itemResource.getItemId() == -1) {
                    orderDetailService.createOpenOrder(itemResource, ordersaved);
                } else {
                    orderDetailService.create(itemResource, ordersaved);
                }
            });

            PaymentResource paymentDetails = orderResource.getPaymentDetails();
            paymentDetails.setOrderId(ordersaved.getOrderId());
            paymentService.create(paymentDetails);
            return ordersaved;
        } catch (DataAccessException e) {
            throw new org.hibernate.service.spi.ServiceException("Failed to save the Order", e);
        }
    }


    /**
     * This method will return the all "PENDING" status orders only.
     *
     * @return DataTableResponseObject
     */
    public DataTableResponse<OrderResource> getAllPendingOrders() {
        DataTableResponse<OrderResource> response = new DataTableResponse<>();
        List<OrderResource> orderList = new ArrayList<>();
        for (Order order : orderRepository.findByStatusOrderByOrderTimeDesc("PENDING")) {
            OrderResource orderResource = new OrderResource();
            orderResource.setOrderId(order.getOrderId());
            if(order.getTable()!=null) {
                orderResource.setTableId(String.valueOf(order.getTable().getTableId()));
                orderResource.setTableName(String.valueOf(tableFlowService.getTableById(order.getTable().getTableId()).getName()));
            }
            orderResource.setCustomerName(order.getCustomerName());
            orderResource.setOrderTime(order.getOrderTime());
            orderResource.setKotNumber(order.getKotNumber());
            orderResource.setComment(order.getComment());
            orderResource.setVoidOrder(order.getVoidOrder());
            orderResource.setOpenOrder(order.getOpenOrder());
            orderResource.setType(order.getType());
            orderResource.setAmount(order.getAmount());
//            orderResource.setItemResourceList(orderResource.getItemResourceList());
            orderResource.setUserId(String.valueOf(order.getUser().getUserId()));
            orderResource.setUserName(order.getUser().getUsername());
            orderList.add(orderResource);
        }
        response.setDataRows(orderList);
        response.setEntries(orderList.size());
        return response;
    }

    /**
     * This method will return the all "VOIDED" status orders only.
     *
     * @return DataTableResponseObject
     */
    public DataTableResponse<OrderResource> getAllVoidOrders() {
        DataTableResponse<OrderResource> response = new DataTableResponse<>();
        List<OrderResource> orderList = new ArrayList<>();
        for (Order order : orderRepository.findByStatusOrderByOrderTimeDesc("VOIDED")) {
            OrderResource orderResource = new OrderResource();
            orderResource.setOrderId(order.getOrderId());
            if(order.getTable()!=null) {
                orderResource.setTableId(String.valueOf(order.getTable().getTableId()));
                orderResource.setTableName(String.valueOf(tableFlowService.getTableById(order.getTable().getTableId()).getName()));
            }
            orderResource.setCustomerName(order.getCustomerName());
            orderResource.setOrderTime(order.getOrderTime());
            orderResource.setKotNumber(order.getKotNumber());
            orderResource.setComment(order.getComment());
            orderResource.setVoidOrder(order.getVoidOrder());
            orderResource.setOpenOrder(order.getOpenOrder());
            orderResource.setType(order.getType());
            orderResource.setAmount(order.getAmount());
//            orderResource.setItemResourceList(orderResource.getItemResourceList());
            orderResource.setUserId(String.valueOf(order.getUser().getUserId()));
            orderResource.setUserName(order.getUser().getUsername());
            orderList.add(orderResource);
        }
        response.setDataRows(orderList);
        response.setEntries(orderList.size());
        return response;
    }

    /**
     * This method will return the all open orders only.
     *
     * @return DataTableResponseObject
     */
    public DataTableResponse<OrderResource> getAllOpenOrders() {
        DataTableResponse<OrderResource> response = new DataTableResponse<>();
        List<OrderResource> orderList = new ArrayList<>();
        for (Order order : orderRepository.findByStatusOrderByOrderTimeDesc("COMPLETED")) {
            OrderResource orderResource = new OrderResource();
            orderResource.setOrderId(order.getOrderId());
            if(order.getTable()!=null) {
                orderResource.setTableId(String.valueOf(order.getTable().getTableId()));
                orderResource.setTableName(String.valueOf(tableFlowService.getTableById(order.getTable().getTableId()).getName()));
            }
            orderResource.setCustomerName(order.getCustomerName());
            orderResource.setOrderTime(order.getOrderTime());
            orderResource.setKotNumber(order.getKotNumber());
            orderResource.setComment(order.getComment());
            orderResource.setVoidOrder(order.getVoidOrder());
            orderResource.setOpenOrder(order.getOpenOrder());
            orderResource.setType(order.getType());
            orderResource.setAmount(order.getAmount());
//            orderResource.setItemResourceList(orderResource.getItemResourceList());
            orderResource.setUserId(String.valueOf(order.getUser().getUserId()));
            orderResource.setUserName(order.getUser().getUsername());
            if(order.getOpenOrder())
                orderList.add(orderResource);
        }
        response.setDataRows(orderList);
        response.setEntries(orderList.size());
        return response;
    }


    /**
     * This method will return the all "PENDING" status orders only.
     *
     * @return DataTableResponseObject
     */
    public DataTableResponse<OrderResource> getPendingOrdersForKichen(int kid) {
        DataTableResponse<OrderResource> response = new DataTableResponse<>();
        List<OrderResource> orderList = new ArrayList<>();
        for (Order order : orderRepository.findByStatusOrderByOrderTimeDesc("PENDING")) {
            boolean checker = false;
            for (OrderDetail orderDetail : order.getOrderDetailList()) {
                if (!orderDetail.getServed()) {
                    if (orderDetail.getKitchen().getKitchenId() == kid) {
                        checker = true;
                    }
                }
            }

            if (checker) {
                OrderResource orderResource = new OrderResource();
                orderResource.setOrderId(order.getOrderId());
                if(order.getTable()!=null) {
                    orderResource.setTableId(String.valueOf(order.getTable().getTableId()));
                    orderResource.setTableName(String.valueOf(tableFlowService.getTableById(order.getTable().getTableId()).getName()));
                }
                orderResource.setCustomerName(order.getCustomerName());
                orderResource.setOrderTime(order.getOrderTime());
                orderResource.setKotNumber(order.getKotNumber());
                orderResource.setComment(order.getComment());
                orderResource.setVoidOrder(order.getVoidOrder());
                orderResource.setOpenOrder(order.getOpenOrder());
                orderResource.setType(order.getType());
                orderResource.setAmount(order.getAmount());
                List<ItemResource> itemResourceList = new ArrayList<>();
                order.getOrderDetailList().stream().forEach(orderDetail -> {
                    ItemResource itemResource = new ItemResource();
                    if (orderDetail.getItem() != null) {
                        itemResource.setItemId(orderDetail.getItem().getItemId());
                        itemResource.setTaxCode(orderDetail.getItem().getTaxCode());
                        itemResource.setComment(orderDetail.getItem().getComment());
                        itemResource.setPortion(orderDetail.getItem().getPortion());
                        itemResource.setSkuCode(orderDetail.getItem().getSkuCode());
                        itemResource.setComment(orderDetail.getItem().getComment());
                    } else {
                        itemResource.setItemId(-1);
                        itemResource.setComment(orderDetail.getComment());
                    }
                    itemResource.setPrice(orderDetail.getPrice());
                    itemResource.setQuantity(orderDetail.getQuantity());
                    itemResource.setName(orderDetail.getItemName());
                    itemResourceList.add(itemResource);
                });
                orderResource.setItemResourceList(itemResourceList);
//            orderResource.setItemResourceList(orderResource.getItemResourceList());
                orderResource.setUserId(String.valueOf(order.getUser().getUserId()));
                orderResource.setUserName(order.getUser().getUsername());
                orderList.add(orderResource);
            }
        }
        response.setDataRows(orderList);
        response.setEntries(orderList.size());
        return response;
    }

    /**
     * This method is for get all "WAITING" state Orders
     *
     * @return
     */
    public DataTableResponse<OrderResource> getAllWaitingOrders() {
        DataTableResponse<OrderResource> response = new DataTableResponse<>();
        List<OrderResource> orderList = new ArrayList<>();
        for (Order order : orderRepository.findByStatusOrderByOrderTimeDesc("WAITING")) {
            OrderResource orderResource = new OrderResource();
            orderResource.setOrderId(order.getOrderId());
            if(order.getTable()!=null) {
                orderResource.setTableId(String.valueOf(order.getTable().getTableId()));
                orderResource.setTableName(String.valueOf(tableFlowService.getTableById(order.getTable().getTableId()).getName()));
            }
            orderResource.setCustomerName(order.getCustomerName());
            orderResource.setOrderTime(order.getOrderTime());
            orderResource.setKotNumber(order.getKotNumber());
            orderResource.setComment(order.getComment());
            orderResource.setType(order.getType());
            orderResource.setAmount(order.getAmount());
            orderResource.setOpenOrder(order.getOpenOrder());
            orderResource.setVoidOrder(order.getVoidOrder());
            orderResource.setItemResourceList(orderResource.getItemResourceList());
            orderResource.setUserId(String.valueOf(order.getUser().getUserId()));
            orderResource.setUserName(order.getUser().getUsername());
            orderList.add(orderResource);
        }
        response.setDataRows(orderList);
        response.setEntries(orderList.size());
        return response;
    }

    /**
     * This method is for getting tht Order Object without converting to the OrderResource.
     *
     * @param id
     * @return
     */
    public Order getPlainOrderObject(int id) {
        Order orderNew = orderRepository.findByOrderId(id);
        return orderNew;
    }


    /**
     * This method is for get the Order as OrderResource.
     *
     * @param id
     * @return
     */
    public OrderResource getOrder(int id) {
        OrderResource orderResource = new OrderResource();
        Order orderNew = getPlainOrderObject(id);
        orderResource.setOrderId(orderNew.getOrderId());
        if(orderNew.getTable()!=null) {
            orderResource.setTableId(String.valueOf(orderNew.getTable().getTableId()));
            orderResource.setTableName(String.valueOf(tableFlowService.getTableById(orderNew.getTable().getTableId()).getName()));
        }
        orderResource.setAmount(orderNew.getAmount());
        orderResource.setOrderTime(orderNew.getOrderTime());
        orderResource.setKotNumber(orderNew.getKotNumber());
        orderResource.setCustomerName(orderNew.getCustomerName());
        orderResource.setComment(orderNew.getComment());
        orderResource.setType(orderNew.getType());
        orderResource.setVoidOrder(orderNew.getVoidOrder());
        orderResource.setOpenOrder(orderNew.getOpenOrder());
        orderResource.setState(orderNew.getStatus());
        orderResource.setUserId(String.valueOf(orderNew.getUser().getUserId()));
        orderResource.setUserName(orderNew.getUser().getUsername());
        List<ItemResource> itemResourceList = new ArrayList<>();
        orderNew.getOrderDetailList().stream().forEach(orderDetail -> {
            ItemResource itemResource = new ItemResource();
            if (orderDetail.getItem() != null) {
                itemResource.setItemId(orderDetail.getItem().getItemId());
                itemResource.setTaxCode(orderDetail.getItem().getTaxCode());
                itemResource.setComment(orderDetail.getItem().getComment());
                itemResource.setPortion(orderDetail.getItem().getPortion());
                itemResource.setSkuCode(orderDetail.getItem().getSkuCode());
                itemResource.setComment(orderDetail.getItem().getComment());
            } else {
                itemResource.setItemId(-1);
                itemResource.setComment(orderDetail.getComment());
            }
            itemResource.setPrice(orderDetail.getPrice());
            itemResource.setQuantity(orderDetail.getQuantity());
            itemResource.setName(orderDetail.getItemName());
            itemResourceList.add(itemResource);
        });
        orderResource.setItemResourceList(itemResourceList);
        return orderResource;
    }

    public OrderResource getOrderForKitchen(int id, int kId) {
        OrderResource orderResource = new OrderResource();
        Order orderNew = getPlainOrderObject(id);
        orderResource.setOrderId(orderNew.getOrderId());
        if(orderNew.getTable()!=null) {
            orderResource.setTableId(String.valueOf(orderNew.getTable().getTableId()));
            orderResource.setTableName(String.valueOf(tableFlowService.getTableById(orderNew.getTable().getTableId()).getName()));
        }
        orderResource.setAmount(orderNew.getAmount());
        orderResource.setOrderTime(orderNew.getOrderTime());
        orderResource.setKotNumber(orderNew.getKotNumber());
        orderResource.setCustomerName(orderNew.getCustomerName());
        orderResource.setComment(orderNew.getComment());
        orderResource.setType(orderNew.getType());
        orderResource.setVoidOrder(orderNew.getVoidOrder());
        orderResource.setOpenOrder(orderNew.getOpenOrder());
        orderResource.setUserId(String.valueOf(orderNew.getUser().getUserId()));
        orderResource.setUserName(orderNew.getUser().getUsername());
        List<ItemResource> itemResourceList = new ArrayList<>();
        orderNew.getOrderDetailList().stream().forEach(orderDetail -> {
            if (orderDetail.getItem() == null || (orderDetail.getItem().getKitchen().getKitchenId() == kId)) {
                ItemResource itemResource = new ItemResource();
                //TODO Add Kitchen for the open order
                if (orderDetail.getItem() != null) {
                    itemResource.setItemId(orderDetail.getItem().getItemId());
                    itemResource.setTaxCode(orderDetail.getItem().getTaxCode());
                    itemResource.setComment(orderDetail.getItem().getComment());
                    itemResource.setPortion(orderDetail.getItem().getPortion());
                    itemResource.setSkuCode(orderDetail.getItem().getSkuCode());
                    itemResource.setComment(orderDetail.getItem().getComment());
                } else {
                    itemResource.setItemId(-1);
                    itemResource.setComment(orderDetail.getComment());
                }
                itemResource.setPrice(orderDetail.getPrice());
                itemResource.setQuantity(orderDetail.getQuantity());
                itemResource.setName(orderDetail.getItemName());
                itemResourceList.add(itemResource);
            }
        });
        orderResource.setItemResourceList(itemResourceList);
        return orderResource;

    }


    /**
     * This method is for updating the order, This will no longer required.
     * //TODO remove this method.
     *
     * @param orderResource
     * @param id
     * @return
     * @throws ParseException
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public Order update(OrderResource orderResource, int id) throws ParseException {
        orderRepository.deleteByOrderId(id);
        orderResource.autoCorrectModel();

        Order order = new Order();
        order.setOrderId(orderResource.getOrderId());
        order.setOrderTime(orderResource.getOrderTime());
        order.setAmount(orderResource.getAmount());
        order.setCustomerName(orderResource.getCustomerName());
        order.setTable(tableFlowService.getTableById(Integer.parseInt(orderResource.getTableId())));
        order.setKotNumber(orderResource.getKotNumber());
        order.setType(orderResource.getType());
        order.setComment(orderResource.getComment());

        order.setStatus("PENDING");
        try {
            Order ordersaved = orderRepository.save(order);
            ArrayList<ItemResource> itemResourceList = (ArrayList<ItemResource>) orderResource.getItemResourceList();
            itemResourceList.stream().forEach(itemResource -> {
                orderDetailService.create(itemResource, ordersaved);
            });
            return ordersaved;
        } catch (DataAccessException e) {
            throw new org.hibernate.service.spi.ServiceException("Failed to save the DownStream", e);
        }
    }

    /**
     * This method is for updating the Order State.
     *
     * @param orderResource
     * @return
     */
    public int updateOrderState(OrderResource orderResource) {
        return updateState(orderResource.getOrderId(), orderResource.getState());
    }


    public void completeOrder(OrderResource orderResource) {
        int kitchenId = orderResource.getkId();
        Order order = orderRepository.findByOrderId(orderResource.getOrderId());

        int numOfKitchen = orderDetailService.getNumberofKitchens(order);
        int numOfServedKitchen = orderDetailService.getNumberofServedKitchens(order);

        if (numOfKitchen == numOfServedKitchen + 1) {
            updateState(orderResource.getOrderId(), orderResource.getState());
        } else {
            List<OrderDetail> byOrder = orderDetailService.findByOrder(order);
            for (OrderDetail orderDetail : byOrder) {
                if (orderDetail.getKitchen().getKitchenId() == kitchenId) {
                    orderDetailService.updateOrderServed(orderDetail.getId());
                }
            }
        }
    }


    /**
     * This method is for updating the state.
     *
     * @param id
     * @param state
     * @return
     */
    public int updateState(int id, String state) {
        Integer integer = orderRepository.updateOrderState(id, state);
        return integer;
    }

    /**
     * Get all completed orders. This method is for check the order history.
     *
     * @return
     */
    public DataTableResponse<OrderResource> getAllCompletedOrders() {
        DataTableResponse<OrderResource> response = new DataTableResponse<>();
        List<OrderResource> orderList = new ArrayList<>();
        for (Order order : orderRepository.findByStatusOrderByOrderTimeDesc("COMPLETED")) {
            OrderResource orderResource = new OrderResource();
            orderResource.setOrderId(order.getOrderId());
            if(order.getTable()!=null) {
                orderResource.setTableId(String.valueOf(order.getTable().getTableId()));
                orderResource.setTableName(String.valueOf(tableFlowService.getTableById(order.getTable().getTableId()).getName()));
            }
            orderResource.setCustomerName(order.getCustomerName());
            orderResource.setOrderTime(order.getOrderTime());
            orderResource.setKotNumber(order.getKotNumber());
            orderResource.setComment(order.getComment());
            orderResource.setType(order.getType());
            orderResource.setAmount(order.getAmount());
            orderResource.setItemResourceList(orderResource.getItemResourceList());
            orderResource.setUserId(String.valueOf(order.getUser().getUserId()));
            orderResource.setUserName(order.getUser().getUsername());
            orderList.add(orderResource);
        }
        response.setDataRows(orderList);
        response.setEntries(orderList.size());
        return response;
    }


    /**
     * This method is for returning the next KOT
     *
     * @return
     */
    public OrderResource getNextKOT() {
        List<Order> allByOrderByKotNumber = orderRepository.findAllByOrderByKotNumber();
        String nextKOTNumber = KOTNumberGenerator.getNextKOTNumber();
        return new OrderResource(nextKOTNumber);
    }

    public int updateVoidOrder(OrderResource orderResource) {
        Integer integer = orderRepository.updateVoidOrder(orderResource.getOrderId(), orderResource.getVoidOrder(), orderResource.getState(),
                orderResource.getComment());
        return integer;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void approveOrder(OrderResource orderResource) {
        if (orderResource.getVoidOrder()) {
            updateState(orderResource.getOrderId(), "VOIDED");
//            orderRepository.deleteByOrderId(orderResource.getOrderId());
        } else if (orderResource.getOpenOrder()) {
            orderRepository.updateOrderState(orderResource.getOrderId(), orderResource.getState());
        }
    }

    public DataTableResponse<OrderResource> getOrdersForDateRange(String startDate, String endDate, int type) throws ParseException {
        DataTableResponse<OrderResource> response = new DataTableResponse<>();
        List<OrderResource> orderList = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        if (startDate.contentEquals(endDate)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            c.setTime(sdf.parse(startDate));
            c.add(Calendar.DATE, 1);
            endDate = sdf.format(c.getTime());
        }

        Date startDateObj = dateFormat.parse(startDate);
        Date endDateObj = dateFormat.parse(endDate);
        List<Order> orderListFromDb = null;
        if (type == 2) {
            orderListFromDb = orderRepository.findByStatusAndOrderTimeBetween("COMPLETED", startDateObj, endDateObj);
        } else {
            orderListFromDb = orderRepository.findByStatusAndTypeAndOrderTimeBetween("COMPLETED", type, startDateObj, endDateObj);
        }
        for (Order order : orderListFromDb) {
            OrderResource orderResource = new OrderResource();
            orderResource.setOrderId(order.getOrderId());
            if(order.getTable()!=null) {
                orderResource.setTableId(String.valueOf(order.getTable().getTableId()));
                orderResource.setTableName(String.valueOf(tableFlowService.getTableById(order.getTable().getTableId()).getName()));
            }
            orderResource.setCustomerName(order.getCustomerName());
            orderResource.setOrderTime(order.getOrderTime());
            orderResource.setKotNumber(order.getKotNumber());
            orderResource.setComment(order.getComment());
            orderResource.setType(order.getType());
            orderResource.setAmount(order.getAmount());

            List<ItemResource> itemResourceList = new ArrayList<>();
            order.getOrderDetailList().stream().forEach(orderDetail -> {
                ItemResource itemResource = new ItemResource();
                if (orderDetail.getItem() != null) {
                    itemResource.setItemId(orderDetail.getItem().getItemId());
                    itemResource.setTaxCode(orderDetail.getItem().getTaxCode());
                    itemResource.setComment(orderDetail.getItem().getComment());
                    itemResource.setPortion(orderDetail.getItem().getPortion());
                    itemResource.setSkuCode(orderDetail.getItem().getSkuCode());
                    itemResource.setComment(orderDetail.getItem().getComment());
                } else {
                    itemResource.setItemId(-1);
                    itemResource.setComment(orderDetail.getComment());
                }
                itemResource.setPrice(orderDetail.getPrice());
                itemResource.setQuantity(orderDetail.getQuantity());
                itemResource.setName(orderDetail.getItemName());
                itemResourceList.add(itemResource);
            });
            orderResource.setItemResourceList(itemResourceList);

            orderResource.setUserId(String.valueOf(order.getUser().getUserId()));
            orderResource.setUserName(order.getUser().getUsername());
            orderList.add(orderResource);
        }
        response.setDataRows(orderList);
        response.setEntries(orderList.size());
        return response;
    }

    public DataTableResponse<OrderResource> getOrdersForCashierDateRange(String startDate, String endDate, int type, int id) throws ParseException {
        DataTableResponse<OrderResource> response = new DataTableResponse<>();
        List<OrderResource> orderList = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        if (startDate.contentEquals(endDate)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            c.setTime(sdf.parse(startDate));
            c.add(Calendar.DATE, 1);
            endDate = sdf.format(c.getTime());
        }

        Date startDateObj = dateFormat.parse(startDate);
        Date endDateObj = dateFormat.parse(endDate);
        List<Order> orderListFromDb = null;

        if(id!=0) {
            User user = userService.getUserByUserId(Integer.toString(id));
            if (type == 2) {
                orderListFromDb = orderRepository.findByStatusAndOrderTimeBetweenAndUser("COMPLETED", startDateObj, endDateObj, user);
            } else {
                orderListFromDb = orderRepository.findByStatusAndTypeAndOrderTimeBetweenAndUser("COMPLETED", type, startDateObj, endDateObj, user);
            }
        }else{
            if (type == 2) {
                orderListFromDb = orderRepository.findByStatusAndOrderTimeBetween("COMPLETED", startDateObj, endDateObj);
            } else {
                orderListFromDb = orderRepository.findByStatusAndTypeAndOrderTimeBetween("COMPLETED", type, startDateObj, endDateObj);
            }
        }
        for (Order order : orderListFromDb) {
            OrderResource orderResource = new OrderResource();
            orderResource.setOrderId(order.getOrderId());
            if(order.getTable()!=null) {
                orderResource.setTableId(String.valueOf(order.getTable().getTableId()));
                orderResource.setTableName(String.valueOf(tableFlowService.getTableById(order.getTable().getTableId()).getName()));
            }
            orderResource.setCustomerName(order.getCustomerName());
            orderResource.setOrderTime(order.getOrderTime());
            orderResource.setKotNumber(order.getKotNumber());
            orderResource.setComment(order.getComment());
            orderResource.setType(order.getType());
            orderResource.setAmount(order.getAmount());

            List<ItemResource> itemResourceList = new ArrayList<>();
            order.getOrderDetailList().stream().forEach(orderDetail -> {
                ItemResource itemResource = new ItemResource();
                if (orderDetail.getItem() != null) {
                    itemResource.setItemId(orderDetail.getItem().getItemId());
                    itemResource.setTaxCode(orderDetail.getItem().getTaxCode());
                    itemResource.setComment(orderDetail.getItem().getComment());
                    itemResource.setPortion(orderDetail.getItem().getPortion());
                    itemResource.setSkuCode(orderDetail.getItem().getSkuCode());
                    itemResource.setComment(orderDetail.getItem().getComment());
                } else {
                    itemResource.setItemId(-1);
                    itemResource.setComment(orderDetail.getComment());
                }
                itemResource.setPrice(orderDetail.getPrice());
                itemResource.setQuantity(orderDetail.getQuantity());
                itemResource.setName(orderDetail.getItemName());
                itemResourceList.add(itemResource);
            });
            orderResource.setItemResourceList(itemResourceList);

            orderResource.setUserId(String.valueOf(order.getUser().getUserId()));
            orderResource.setUserName(order.getUser().getUsername());
            orderList.add(orderResource);
        }
        response.setDataRows(orderList);
        response.setEntries(orderList.size());
        return response;
    }


    public DataTableResponse<OrderResource> getAllOrdersForDate(String date) throws ParseException {
        DataTableResponse<OrderResource> response = new DataTableResponse<>();
        List<OrderResource> orderList = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String startDate = date;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(date));
        c.add(Calendar.DATE, 1);
        String endDate = sdf.format(c.getTime());

        Date startDateObj = dateFormat.parse(startDate);
        Date endDateObj = dateFormat.parse(endDate);
        List<Order> orderListFromDb = null;

        orderListFromDb = orderRepository.findByOrderTimeBetween(startDateObj, endDateObj);

        for (Order order : orderListFromDb) {
            PaymentResource paymentByOrderId = paymentService.getPaymentByOrderId(order.getOrderId());

            OrderResource orderResource = new OrderResource();
            orderResource.setOrderId(order.getOrderId());
            if(order.getTable()!=null) {
                orderResource.setTableId(String.valueOf(order.getTable().getTableId()));
                orderResource.setTableName(String.valueOf(tableFlowService.getTableById(order.getTable().getTableId()).getName()));
            }
            orderResource.setCustomerName(order.getCustomerName());
            orderResource.setOrderTime(order.getOrderTime());
            orderResource.setKotNumber(order.getKotNumber());
            orderResource.setComment(order.getComment());
            orderResource.setType(order.getType());
            orderResource.setOpenOrder(order.getOpenOrder());
            orderResource.setAmount(order.getAmount());
            orderResource.setState(order.getStatus());

            List<ItemResource> itemResourceList = new ArrayList<>();
            order.getOrderDetailList().stream().forEach(orderDetail -> {
                ItemResource itemResource = new ItemResource();
                if (orderDetail.getItem() != null) {
                    itemResource.setItemId(orderDetail.getItem().getItemId());
                    itemResource.setTaxCode(orderDetail.getItem().getTaxCode());
                    itemResource.setComment(orderDetail.getItem().getComment());
                    itemResource.setPortion(orderDetail.getItem().getPortion());
                    itemResource.setSkuCode(orderDetail.getItem().getSkuCode());
                    itemResource.setComment(orderDetail.getItem().getComment());
                } else {
                    itemResource.setItemId(-1);
                    itemResource.setComment(orderDetail.getComment());
                }
                itemResource.setPrice(orderDetail.getPrice());
                itemResource.setQuantity(orderDetail.getQuantity());
                itemResource.setName(orderDetail.getItemName());
                itemResourceList.add(itemResource);
            });
            orderResource.setItemResourceList(itemResourceList);

            orderResource.setPaymentDetails(paymentByOrderId);
            orderResource.setUserId(String.valueOf(order.getUser().getUserId()));
            orderResource.setUserName(order.getUser().getUsername());
            orderList.add(orderResource);
        }
        response.setDataRows(orderList);
        response.setEntries(orderList.size());
        return response;
    }
}
