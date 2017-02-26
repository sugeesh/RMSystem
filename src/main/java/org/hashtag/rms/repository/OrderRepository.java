package org.hashtag.rms.repository;

import org.hashtag.rms.model.Category;
import org.hashtag.rms.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Sugeesh Chandraweera
 */
public interface OrderRepository extends CrudRepository<Order, Integer> {


    List<Order> findByStatus(String status);

    Order findByOrderId(int id);

    void deleteByOrderId(int id);

    List<Order> findAllByOrderByKotNumber();

    @Transactional
    @Modifying
    @Query("UPDATE Order o SET o.status = :status WHERE o.orderId = :orderId")
    Integer updateOrderState(@Param("orderId") int orderId, @Param("status") String status);
}
