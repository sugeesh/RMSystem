package org.hashtag.rms.repository;

import org.hashtag.rms.model.CashDrawer;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

/**
 * @author Sugeesh Chandraweera
 */
public interface CashDrawerRepository extends CrudRepository<CashDrawer, Integer> {

    CashDrawer findByDate(Date date);

    List<CashDrawer> findByDateBetween(Date startDateObj, Date endDateObj);

    List<CashDrawer> findAllByOrderByDateDesc();
}
