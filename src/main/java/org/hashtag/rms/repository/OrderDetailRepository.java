package org.hashtag.rms.repository;

import org.hashtag.rms.model.Order;
import org.hashtag.rms.model.OrderDetail;
import org.springframework.data.repository.CrudRepository;
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

//    void deleteByOrderId(Integer orderId);
}
