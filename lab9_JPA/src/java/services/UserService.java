package services;

import dataaccess.UserDB;
import dataaccess.RoleDB;
import java.util.List;
import models.User;
import models.Role;

public class UserService {

    public List<User> getAll() throws Exception {
        UserDB userDB = new UserDB();
        List<User> users = userDB.getAll();
        return users;
    }

    public User get(String email) throws Exception {
        UserDB userDB = new UserDB();
        User user = userDB.get(email);
        return user;
    }
    
    public void insert(String email, boolean active, String firstName, String lastName, String password, int roleid) throws Exception {
        User user = new User(email, active, firstName, lastName, password);
        RoleDB roleDB = new RoleDB();
        Role role = roleDB.get(roleid);
        user.setRole(role);
    }

    public void update(String email, boolean active, String firstName, String lastName, String password, int role) throws Exception {
        UserDB userDB = new UserDB();
        User user = userDB.get(email);
        user.setActive(active);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(password); 
    }

    public void delete(String email) throws Exception {
        UserDB userDB = new UserDB();
        User user = userDB.get(email);
        userDB.delete(user);
    }
}
