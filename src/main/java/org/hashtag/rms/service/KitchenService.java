package org.hashtag.rms.service;



import org.hashtag.rms.model.Kitchen;
import org.hashtag.rms.repository.KitchenRepository;
import org.hashtag.rms.resource.KitchenResource;
import org.hashtag.rms.util.rest.DataTableResponse;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;


/**
 * CategoryService is for providing services for the category table
 *
 * @author Sugeesh Chandraweera
 */
public class KitchenService {

    @Autowired
    private KitchenRepository kitchenRepository;

    public DataTableResponse<KitchenResource> getAllCategories(String search, int page, int size, Boolean asc, String column) throws ServiceException {
        List<KitchenResource> kitchenList = new ArrayList<>();
        Sort.Direction direction = asc ? Sort.Direction.ASC : Sort.Direction.DESC;
        search = search.isEmpty() ? "%" : "%" + search + "%";
        DataTableResponse<KitchenResource> response = new DataTableResponse<>();
        try {
            Page<Kitchen> results = kitchenRepository.findAllByNameLike(search, new PageRequest(page, size, direction, column));
            for (Kitchen kitchen : results) {
                kitchenList.add(KitchenResource.createResource(kitchen));
            }
            response.setEntries(results.getTotalElements());
            response.setDataRows(kitchenList);
            return response;
        } catch (DataAccessException e) {

        }
        return response;
    }

    public Object getAllKitchensWithoutPagination() {
        List<KitchenResource> kitchenList = new ArrayList<>();
        for (Kitchen kitchen : kitchenRepository.findAll()) {
            kitchenList.add(KitchenResource.createResource(kitchen));
        }
        return kitchenList;
    }

    public Kitchen getKitchenById(int kitchenId){
        return  kitchenRepository.findByKitchenId(kitchenId);
    }


    public Object saveKitchen(KitchenResource kitchenResource) {
        Kitchen kitchen = new Kitchen();
        kitchen.setName(kitchenResource.getName());
        Kitchen save = kitchenRepository.save(kitchen);
        return save;
    }
}