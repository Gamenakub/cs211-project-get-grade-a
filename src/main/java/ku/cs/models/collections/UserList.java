package ku.cs.models.collections;

import ku.cs.models.users.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class UserList implements Searchable<User>{
    private ArrayList<User> users;
    public UserList() {
        users = new ArrayList<User>();
    }
    public ArrayList<User> getUsers() {
        return users;
    }
    public void setUsers(ArrayList<User> users) {
        this.users = users;
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
        if(user != null && user.validatePassword(password)){
            LocalDateTime recentTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            user.setRecentTime(recentTime);
            return user;
        }
        return null;
    }

    public void addUsers(ArrayList<? extends User> userList) {
        users.addAll(userList);
    }

    @Override
    public ArrayList<User> search(String term) {
        ArrayList<User> targetUsers = new ArrayList<>();
        for (User user : users) {
            String name = user.getNameTitle() + user.getName() + " " + user.getSurname();
            if(name.contains(term)){
                targetUsers.add(user);
            }
        }
        return targetUsers;
    }

    @Override
    public ArrayList<User> filter(String role) {
        ArrayList<User> targetUsers = new ArrayList<>();
        for(User user : users){
            if(user.getRole().equals(role)){
                targetUsers.add(user);
            }
        }
        return targetUsers;
    }
}
