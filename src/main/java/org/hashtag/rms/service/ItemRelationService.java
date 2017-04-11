package org.hashtag.rms.service;

import org.hashtag.rms.model.CashDrawer;
import org.hashtag.rms.model.Item;
import org.hashtag.rms.model.ItemRelation;
import org.hashtag.rms.repository.CashDrawerRepository;
import org.hashtag.rms.repository.ItemRelationRepository;
import org.hashtag.rms.repository.ItemRepository;
import org.hashtag.rms.resource.CashDrawerResource;
import org.hashtag.rms.resource.ItemRelationResource;
import org.hashtag.rms.resource.ItemResource;
import org.hashtag.rms.resource.OrderResource;
import org.hashtag.rms.util.rest.DataTableResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Buddhi on 3/16/2017.
 */
public class ItemRelationService {
    @Autowired
    private ItemRelationRepository itemRelationRepository;

    @Autowired
    private ItemRepository itemRepository;

    public Object getChildItemsForItem(Integer id) {
        DataTableResponse<ItemResource> response = new DataTableResponse<>();

        Item item = itemRepository.findByItemId(id);
        List<ItemRelation> allByParentItemID = itemRelationRepository.findAllByParentItem(item);

        List<ItemResource> items = new ArrayList<ItemResource>();
        for (ItemRelation itemRelation : allByParentItemID) {
            Item i = itemRepository.findByItemId(itemRelation.getChildItem().getItemId());

            ItemResource itemResource = new ItemResource();
            itemResource.setCategoryId(i.getCategory().getCategoryId());
            itemResource.setName(i.getName());
            itemResource.setItemId(i.getItemId());
            itemResource.setPriority(i.getPriority());
            itemResource.setActive(i.getActive());
            itemResource.setComment(i.getComment());
            itemResource.setPortion(i.getPortion());
            itemResource.setPrice(i.getPrice());
            itemResource.setSkuCode(i.getSkuCode());
            itemResource.setTaxCode(i.getTaxCode());

            items.add(itemResource);
        }

        response.setDataRows(items);
        return response;
    }

    public Object addNewItemRelation(Integer itemId, Integer childId) {
        Item item = itemRepository.findByItemId(itemId);
        Item child = itemRepository.findByItemId(childId);

        ItemRelation itemRelation = new ItemRelation(item, child);

        ItemRelation save = itemRelationRepository.save(itemRelation);
        return save;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Object removeItemRelation(Integer itemId, Integer childId) {
        Item parent = itemRepository.findByItemId(itemId);
        Item child = itemRepository.findByItemId(childId);
        itemRelationRepository.deleteItemRelationByParentItemAndChildItem(parent,child);
        return new ItemRelationResource();
    }
}
