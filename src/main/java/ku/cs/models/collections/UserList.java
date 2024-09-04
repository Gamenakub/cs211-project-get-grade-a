package ku.cs.models.collections;

import ku.cs.models.users.User;

import java.util.ArrayList;

public class UserList {
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
            return user;
        }
        return null;
//        if(username.equals("admin")){
//            return new User("admin", "test", "Isarapong", "Tuensakul");
//        }
//        if(username.equals("student")){
//            return new Student("student", "test", "Tee", "Anu", "b6610402222", "tee.anu@ku.th");
//        }
//        if(username.equals("advisor")){
//            return new Advisor("admin", "test", "Jirat", "Kong", "D1425", "Com-sci", "")
//        }
    }
}
