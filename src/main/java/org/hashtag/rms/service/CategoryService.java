package org.hashtag.rms.service;


import org.hashtag.rms.model.Category;
import org.hashtag.rms.model.Item;
import org.hashtag.rms.repository.CategoryRepository;
import org.hashtag.rms.resource.CategoryResource;
import org.hashtag.rms.resource.ItemResource;
import org.hashtag.rms.util.rest.DataTableResponse;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * CategoryService is for providing services for the category table
 *
 * @author Sugeesh Chandraweera
 */
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public DataTableResponse<CategoryResource> getAllCategories(String search, int page, int size, Boolean asc, String column) throws ServiceException {
        List<CategoryResource> categoryList = new ArrayList<>();
        Sort.Direction direction = asc ? Sort.Direction.ASC : Sort.Direction.DESC;
        search = search.isEmpty() ? "%" : "%" + search + "%";
        DataTableResponse<CategoryResource> response = new DataTableResponse<>();
        try {
            Page<Category> results = categoryRepository.findAllByNameLike(search, new PageRequest(page, size, direction, column));
            for (Category category : results) {
                categoryList.add(CategoryResource.createResource(category));
            }
            response.setEntries(results.getTotalElements());
            response.setDataRows(categoryList);
            return response;
        } catch (DataAccessException e) {

        }
        return response;
    }

    public Object getAllCategoriesWithoutPagination() {
        List<CategoryResource> categoryList = new ArrayList<>();
        for (Category category : categoryRepository.findAll()) {
            categoryList.add(CategoryResource.createResource(category));
        }
        return categoryList;
    }

    public DataTableResponse<CategoryResource> getAllCategoriesWithItems() {
        List<CategoryResource> categoryList = new ArrayList<>();
        DataTableResponse<CategoryResource> response = new DataTableResponse<>();
        for (Category category : categoryRepository.findAllByOrderByPriority()) {
            CategoryResource resource = CategoryResource.createResource(category);
            resource = resource.addItems(category);
            categoryList.add(resource);
        }
        response.setDataRows(categoryList);
        response.setEntries(categoryList.size());
        return response;

    }

    public Object getItemsForCategory(int categoryId) {
        System.out.println(categoryId);
        return null;
    }

    public Object saveCategory(CategoryResource categoryResource) {
        Category category = new Category();
        category.setName(categoryResource.getName());
        category.setColorCode(categoryResource.getColorCode());
        category.setPriority(categoryResource.getPriority());

        Category save = categoryRepository.save(category);
        return save;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public CategoryResource deleteCategory(int categoryId) {
        categoryRepository.deleteItemByCategoryId(categoryId);
        return new CategoryResource(categoryId);
    }

    public Integer updateCategoryPriority(CategoryResource categoryResource) {
        Integer integer = categoryRepository.updatePriority(categoryResource.getCategoryId(), categoryResource.getPriority());
        return integer;
    }

    public Category getCategoryById(int catororyId){
        return  categoryRepository.findByCategoryId(catororyId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateAllCategoryPriority(CategoryResource[] array) {
        Arrays.stream(array).forEach(catUpdated -> {
            updateCategoryPriority(catUpdated);
        });
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateCategory(CategoryResource categoryResource) {
        categoryRepository.updateCategory(categoryResource.getCategoryId(),categoryResource.getName()
                ,categoryResource.getColorCode());
    }
}