package org.hashtag.rms.repository;

import org.hashtag.rms.model.Item;
import org.hashtag.rms.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

/**
 * Created by Buddhi on 2/19/2017.
 */
@Component
public interface UserRepository extends CrudRepository<User, Integer> {
    
    User findByUsernameAndPassword(String username, String password);
}
