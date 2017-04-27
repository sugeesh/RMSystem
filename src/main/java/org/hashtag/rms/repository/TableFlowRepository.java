package org.hashtag.rms.repository;

import org.hashtag.rms.model.CashDrawer;
import org.hashtag.rms.model.Payment;
import org.hashtag.rms.model.TableFlow;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Sugeesh Chandraweera
 */
public interface TableFlowRepository extends CrudRepository<TableFlow, Integer> {

    TableFlow findByTableId(int tableId);

    List<TableFlow> findAllByAvailability(boolean availability);

    @Transactional
    @Modifying
    @Query("UPDATE TableFlow tf SET tf.availability = :availability WHERE tf.tableId = :tableId")
    Integer updateOrderState(@Param("tableId") int tableId, @Param("availability") Boolean availability);
}
