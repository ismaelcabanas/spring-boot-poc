package cabanas.garcia.ismael.springbootpoc.service;


import cabanas.garcia.ismael.springbootpoc.model.User;

import java.util.Collection;

/**
 * User services.
 *
 * Created by ismael on 19/06/2016.
 */
public interface UserService {
    /**
     * Find all users.
     *
     * @return a collection of users
     */
    Collection<User> findAll();
}