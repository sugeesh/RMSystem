package org.hashtag.rms.repository;

import org.hashtag.rms.model.CashDrawer;
import org.hashtag.rms.model.Item;
import org.hashtag.rms.model.ItemRelation;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

/**
 * @author Sugeesh Chandraweera
 */
public interface ItemRelationRepository extends CrudRepository<ItemRelation, Integer> {

    List<ItemRelation> findAllByParentItem(Item item);
}
