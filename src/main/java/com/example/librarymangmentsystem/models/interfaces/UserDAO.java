package com.example.librarymangmentsystem.models.interfaces;

import com.example.librarymangmentsystem.models.User;
import com.example.librarymangmentsystem.models.Role;
import java.util.List;

public interface UserDAO {
    void addUser(User user);
    User getUserByUsername(String username);
    User getUserByEmail(String email);
    boolean updatePassword(String email, String newPassword);
    boolean emailExists(String email);
    boolean usernameExists(String username);
    List<User> getUsersByRole(Role role);
}
