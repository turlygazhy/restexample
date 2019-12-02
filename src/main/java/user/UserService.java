package user;

import user.User;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private static final List<User> users = new ArrayList<User>();

    static {
        users.add(new User("John", "Smith", "john", "john@mail.ru"));
    }


    public List<User> getUsers() {
        return users;
    }

    public User getUser(final String name) {
        return users.stream()
                .filter(u -> u.getFirstName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public User updateUser(User user) {
        User userForUpdate = getUser(user.getFirstName());
        users.remove(userForUpdate);
        users.add(user);
        return user;
    }

    public User addUser(User user) {
        users.add(user);
        return user;
    }

    public void removeUser(String name) {
        User userForDelete = getUser(name);
        users.remove(userForDelete);
    }
}
