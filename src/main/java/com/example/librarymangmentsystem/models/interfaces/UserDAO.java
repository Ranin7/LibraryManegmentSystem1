package com.example.librarymangmentsystem.models.interfaces;

import com.example.librarymangmentsystem.models.User;

public interface UserDAO {
    void addUser(User user);
    User getUserByUsername(String username);
    User getUserByEmail(String email);
    boolean updatePassword(String email, String newPassword);
}
