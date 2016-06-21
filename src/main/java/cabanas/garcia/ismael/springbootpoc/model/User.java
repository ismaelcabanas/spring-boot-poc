package cabanas.garcia.ismael.springbootpoc.model;

/**
 * User model class.
 *
 * Created by ismael on 19/06/2016.
 */
public class User {

    private String name;

    private User(UserBuilder builder) {
        this.name = builder.name;
    }

    public String getName() {
        return name;
    }

    public static class UserBuilder {
        private String name;

        public UserBuilder(){}

        public UserBuilder name(String name){
            this.name = name;
            return this;
        }

        public User build(){
            return new User(this);
        }
    }
}
