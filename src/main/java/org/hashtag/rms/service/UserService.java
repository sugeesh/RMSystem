package org.hashtag.rms.service;

import org.hashtag.rms.model.User;
import org.hashtag.rms.repository.UserRepository;
import org.hashtag.rms.resource.UserResource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Buddhi on 2/19/2017.
 */
public class UserService {
    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;

    public Object registerUser(UserResource userResource) {
        User user = new User();

        user.setUsername(userResource.getUsername());
        user.setName(userResource.getName());
//        user.setPassword(passwordEncoder.encode(userResource.getPassword()));
        user.setPassword(userResource.getPassword());
        user.setType(userResource.getType());
        user.setNic(userResource.getNic());
        user.setTelephone(userResource.getTelephone());

        User save = userRepository.save(user);
        return save;
    }

    public Object loginUser(UserResource userResource) {
        User user = userRepository.findByUsernameAndPassword(userResource.getUsername(), userResource.getPassword());
        return user;
    }

    public User getUserByUserId(String id) {
        return userRepository.findByUserId(Integer.parseInt(id));
    }

    public Object getAllUsers() {
        return userRepository.findAll();
    }

    public Object getAllCashiers() {
        List<User> byType = userRepository.findByType(2);
        List<UserResource> userResourceList = new ArrayList<>();
        byType.forEach(user ->{
            userResourceList.add(new UserResource(user));
        });
        return userResourceList;
    }


    public Object updateUser(UserResource userResource) {
        userRepository.delete(userResource.getId());

        User user = new User();
        user.setUserId(userResource.getId());
        user.setName(userResource.getName());
        user.setPassword(userResource.getPassword());
        user.setTelephone(user.getTelephone());
        user.setNic(userResource.getNic());
        user.setUsername(userResource.getUsername());
        user.setType(userResource.getType());

        User save = userRepository.save(user);
        return save;
    }
}
