package cabanas.garcia.ismael.springbootpoc.helpers;

import cabanas.garcia.ismael.springbootpoc.model.User;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Clase de soporte para generar Stubs de usuarios.
 *
 * Created by ismael on 28/06/2016.
 */
public final class UserHelper {

    private static final String USER_NAME = "UserName Test";

    public static Collection<User> getUsersStubData() {
        Collection<User> list = new ArrayList<User>();
        list.add(getUserStubData());
        return list;
    }

    private static User getUserStubData() {
        User user = new User.UserBuilder()
                .name(USER_NAME)
                .build();
        return user;
    }
}
