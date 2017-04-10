package org.hashtag.rms.repository;

import org.hashtag.rms.model.Category;
import org.hashtag.rms.model.Kitchen;
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
public interface KitchenRepository extends CrudRepository<Kitchen, Integer> {

    Page<Kitchen> findAllByNameLike(String name, Pageable pageable);

    Kitchen findByKitchenId(int kitchenId);

    void deleteItemByKitchenId(int kitchenId);
}
