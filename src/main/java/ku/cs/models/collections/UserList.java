package ku.cs.models.collections;

import ku.cs.models.users.User;

import java.util.ArrayList;

public class UserList implements Searchable<User> {
    private final ArrayList<User> users;

    public UserList() {
        users = new ArrayList<>();
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public User findUserByUsername(String username) {
        for (User user : users) {
            if (user.isUsername(username)) {
                return user;
            }
        }
        return null;
    }

    public User login(String username, String password) {
        User user = findUserByUsername(username);
        if (user != null && user.validatePassword(password)) {
            return user;
        }
        return null;
    }

    public void addUsers(ArrayList<? extends User> userList) {
        users.addAll(userList);
    }

    @Override
    public ArrayList<User> search(String keyword) {
        ArrayList<User> targetUsers = new ArrayList<>();
        for (User user : users) {
            if (user.getFullName().contains(keyword)) {
                targetUsers.add(user);
            } else if (user.getUsername().contains(keyword)) {
                targetUsers.add(user);
            }
        }
        return targetUsers;
    }

    @Override
    public ArrayList<User> filter(String roleString) {
        ArrayList<User> targetUsers = new ArrayList<>();
        for (User user : users) {
            if (user.getRole().equals(roleString)) {
                targetUsers.add(user);
            }
        }
        return targetUsers;
    }
}
