package org.hashtag.rms.repository;

import org.hashtag.rms.model.CashDrawer;
import org.hashtag.rms.model.Payment;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;

/**
 * @author Sugeesh Chandraweera
 */
public interface CashDrawerRepository extends CrudRepository<CashDrawer, Integer> {

    CashDrawer findByDate(Date date);
}
