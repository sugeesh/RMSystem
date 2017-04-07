package org.hashtag.rms.service;

import org.hashtag.rms.model.CashDrawer;
import org.hashtag.rms.model.Item;
import org.hashtag.rms.model.ItemRelation;
import org.hashtag.rms.repository.CashDrawerRepository;
import org.hashtag.rms.repository.ItemRelationRepository;
import org.hashtag.rms.repository.ItemRepository;
import org.hashtag.rms.resource.CashDrawerResource;
import org.hashtag.rms.resource.ItemResource;
import org.hashtag.rms.resource.OrderResource;
import org.hashtag.rms.util.rest.DataTableResponse;
import org.springframework.beans.factory.annotation.Autowired;

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
            item.setCategory(i.getCategory());
            item.setName(i.getName());
            item.setItemId(i.getItemId());
            item.setPriority(i.getPriority());
            item.setActive(i.getActive());
            item.setComment(i.getComment());
            item.setOrderDetailList(i.getOrderDetailList());
            item.setPortion(i.getPortion());
            item.setPrice(i.getPrice());
            item.setSkuCode(i.getSkuCode());
            item.setTaxCode(i.getTaxCode());

            items.add(itemResource);
        }

        response.setDataRows(items);
        return response;
    }
}
