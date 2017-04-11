package org.hashtag.rms.repository;

import org.hashtag.rms.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * This repository is for handling the item table
 *
 * @author Sugeesh Chandraweera
 */

@Component
public interface ItemRepository extends CrudRepository<Item, Integer> {

    Page<Item> findAllByNameLike(String LastName, Pageable pageable);


    @Transactional
    @Modifying
    @Query("UPDATE Item i SET i.priority = :priority,i.active = :active WHERE i.itemId = :itemId")
    Integer updatePriority(@Param("itemId") int itemId, @Param("priority") int priority, @Param("active") boolean active);

    List<Item> findAllByOrderByPriority();

    void deleteItemByItemId(int id);

    Item findByItemId(Integer id);
}
