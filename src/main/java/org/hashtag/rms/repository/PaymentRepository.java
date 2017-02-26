package org.hashtag.rms.repository;

import org.hashtag.rms.model.Payment;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Sugeesh Chandraweera
 */
public interface PaymentRepository extends CrudRepository<Payment, Integer> {

}
