package org.hashtag.rms.service;

import org.hashtag.rms.model.Category;
import org.hashtag.rms.model.Item;
import org.hashtag.rms.model.Order;
import org.hashtag.rms.model.User;
import org.hashtag.rms.repository.UserRepository;
import org.hashtag.rms.resource.ItemResource;
import org.hashtag.rms.resource.UserResource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Buddhi on 2/19/2017.
 */
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Object registerUser(UserResource userResource) {
        User user = new User();

        user.setUsername(userResource.getUsername());
        user.setName(userResource.getName());
        user.setPassword(userResource.getPassword());
        user.setType(userResource.getType());

        User save = userRepository.save(user);
        return save;
    }

    public Object loginUser(UserResource userResource) {
//        User user = userRepository.findByUsernameByPassword(userResource.getUsername(), userResource.getPassword());

//        return user;

        return null;
    }
}
