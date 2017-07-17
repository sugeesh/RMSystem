package org.hashtag.rms.repository;


import org.hashtag.rms.model.Order;
import org.hashtag.rms.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author Sugeesh Chandraweera
 */
public interface OrderRepository extends CrudRepository<Order, Integer> {


    List<Order> findByStatusOrStatus(String status1, String status2);

//    List<Order> findByStatusOrderByOrderTimeAsc(String status);
    List<Order> findByStatusOrderByOrderTimeDesc(String status);

    List<Order> findByStatusAndOrderTimeBetween(String status, Date startDate, Date endDate);

    List<Order> findByStatusAndOrderTimeBetweenAndUser(String status, Date startDate, Date endDate,User user);

    List<Order> findByStatusAndTypeAndOrderTimeBetween(String status, int type, Date startDate, Date endDate);

    List<Order> findByStatusAndTypeAndOrderTimeBetweenAndUser(String status, int type, Date startDate, Date endDate, User user);

    Order findByOrderId(int id);

    void deleteByOrderId(int id);

    List<Order> findAllByOrderByKotNumber();

    @Transactional
    @Modifying
    @Query("UPDATE Order o SET o.status = :status WHERE o.orderId = :orderId")
    Integer updateOrderState(@Param("orderId") int orderId, @Param("status") String status);

    @Transactional
    @Modifying
    @Query("UPDATE Order o SET o.voidOrder = :voidOrder ,o.comment = :comment, o.status = :status WHERE o.orderId = :orderId")
    Integer updateVoidOrder(@Param("orderId") int orderId, @Param("voidOrder") Boolean voidOrder, @Param("status") String status,
                            @Param("comment") String comment);

    List<Order> findByOrderTimeBetween(Date startDateObj, Date endDateObj);
}
