package org.hashtag.rms.repository;

import org.hashtag.rms.model.Item;
import org.hashtag.rms.model.ItemRelation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Sugeesh Chandraweera
 */
public interface ItemRelationRepository extends CrudRepository<ItemRelation, Integer> {

    List<ItemRelation> findAllByParentItem(Item item);

    void deleteItemRelationByParentItemAndChildItem(Item parent, Item child);
}
