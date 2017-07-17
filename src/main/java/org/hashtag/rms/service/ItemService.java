package org.hashtag.rms.service;


import org.hashtag.rms.model.Category;
import org.hashtag.rms.model.Item;
import org.hashtag.rms.model.Kitchen;
import org.hashtag.rms.repository.ItemRepository;
import org.hashtag.rms.resource.ItemResource;
import org.hashtag.rms.util.rest.DataTableResponse;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * ItemService is for providing services for the item table
 *
 * @author Sugeesh Chandraweera
 */
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private KitchenService kitchenService;

    public DataTableResponse<ItemResource> getAllItems(String search, int page, int size, Boolean asc, String column) throws ServiceException {
        List<ItemResource> itemList = new ArrayList<>();
        Sort.Direction direction = asc ? Sort.Direction.ASC : Sort.Direction.DESC;
        search = search.isEmpty() ? "%" : "%" + search + "%";
        DataTableResponse<ItemResource> response = new DataTableResponse<>();
        try {
            Page<Item> results = itemRepository.findAllByNameLike(search, new PageRequest(page, size, direction, column));
            for (Item item : results) {
                itemList.add(ItemResource.createResource(item));
            }
            response.setEntries(results.getTotalElements());
            response.setDataRows(itemList);
            return response;
        } catch (DataAccessException e) {

        }
        return response;
    }

    public Object getAllItemsWithoutPagination() {
        List<ItemResource> permissions = new ArrayList<>();
        for (Item permission : itemRepository.findAll()) {
            permissions.add(ItemResource.createResource(permission));
        }
        return permissions;
    }

    public ItemResource saveItem(ItemResource itemResource) {
        Item item = new Item();
        item.setName(itemResource.getName());

        Category category = categoryService.getCategoryById(itemResource.getCategoryId());
        Kitchen kitchen = kitchenService.getKitchenById(itemResource.getKitchenId());

        item.setName(itemResource.getName());
        item.setPortion(itemResource.getPortion());
        item.setPrice(itemResource.getPrice());
        item.setSkuCode(itemResource.getSkuCode());
        item.setTaxCode(itemResource.getTaxCode());
        item.setComment(itemResource.getComment());
        item.setActive(true);
        item.setPriority(category.getItemList().size());
        item.setCategory(category);
        item.setKitchen(kitchen);
        if(itemResource.getIsTakeAway()==0){
            item.setTakeAway(false);
        }else {
            item.setTakeAway(true);
        }

        Item save = itemRepository.save(item);
        return itemResource;
    }


    public Integer updateItemPriority(ItemResource itemResource) {
        Integer integer = itemRepository.updatePriority(itemResource.getItemId(), itemResource.getPriority()
                ,itemResource.isActive());
        return integer;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateAllItemPriority(ItemResource[] array) {
        Arrays.stream(array).forEach(iteUpdated -> {
            updateItemPriority(iteUpdated);
        });
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ItemResource deleteItem(int id) {
        itemRepository.deleteItemByItemId(id);
        return new ItemResource(id);
    }

}
