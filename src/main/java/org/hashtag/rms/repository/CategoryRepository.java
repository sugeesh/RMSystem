package org.hashtag.rms.repository;

import org.hashtag.rms.model.Category;
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
public interface CategoryRepository extends CrudRepository<Category, Integer> {

    Page<Category> findAllByNameLike(String LastName, Pageable pageable);

    Category findByCategoryId(int catororyId);

    @Transactional
    @Modifying
    @Query("UPDATE Category c SET c.priority = :priority WHERE c.categoryId = :categoryId")
    Integer updatePriority(@Param("categoryId") int categoryId, @Param("priority") int priority);

    @Transactional
    @Modifying
    @Query("UPDATE Category c SET c.name = :name, c.colorCode = :colorCode WHERE c.categoryId = :categoryId")
    Integer updateCategory(@Param("categoryId") int categoryId, @Param("name") String name, @Param("colorCode") String colorCode);

    List<Category> findAllByOrderByPriority();

    void deleteItemByCategoryId(int categoryId);
}
