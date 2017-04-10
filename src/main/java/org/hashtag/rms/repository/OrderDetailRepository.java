package org.hashtag.rms.repository;

import org.hashtag.rms.model.Item;
import org.hashtag.rms.model.Order;
import org.hashtag.rms.model.OrderDetail;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Sugeesh Chandraweera
 */
public interface OrderDetailRepository extends CrudRepository<OrderDetail, Integer> {

    @Transactional
    List<OrderDetail> deleteById(int id);

    @Transactional
    List<OrderDetail> deleteByOrder(Order order);

    List<OrderDetail> findAllByItem(Item item);


    @Transactional
    @Modifying
    @Query("UPDATE OrderDetail od SET od.isServed = :served WHERE od.id = :orderDetailId")
    Integer updateOrderState(@Param("orderDetailId") int orderDetailId, @Param("served") Boolean served);

    List<OrderDetail> findAllByOrder(Order order);

//    void deleteByOrderId(Integer orderId);
}
