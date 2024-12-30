package com.example.librarymangmentsystem.models.interfaces;

import com.example.librarymangmentsystem.models.Role;

public interface UserDAO {
    void addUser(Role.User user);
    Role.User getUserByUsername(String username);
}
