package cabanas.garcia.ismael.springbootpoc.service;

import cabanas.garcia.ismael.springbootpoc.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Default user service's implementation.
 *
 * Created by ismael on 19/06/2016.
 */
@Service
public class UserServiceImpl implements UserService {

    /**
     * Find all users.
     *
     * @return a collection of users
     */
    @Override
    public Collection<User> findAll() {
        return new ArrayList<User>();
    }
}
