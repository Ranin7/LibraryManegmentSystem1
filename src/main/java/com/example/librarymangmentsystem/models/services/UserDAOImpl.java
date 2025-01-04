package com.example.librarymangmentsystem.models.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.example.librarymangmentsystem.models.Role;
import com.example.librarymangmentsystem.models.interfaces.UserDAO;

public class UserDAOImpl implements UserDAO {

    @Override
    public void addUser(Role.User user) {
    }

    @Override
    public Role.User getUserByUsername(String username) {
        Role.User user = null;
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarydb", "root", "Group3_123");
            String query = "SELECT * FROM users WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String password = resultSet.getString("password");
                int roleId = resultSet.getInt("role_id");
                String role = "";
                switch (roleId) {
                    case 1:
                        role = "Admin";
                        break;
                    case 2:
                        role = "Librarian";
                        break;
                    case 3:
                        role = "User";
                        break;
                    default:
                        role = "Unknown";
                        break;
                }
                user = new Role.User(username, password, role);
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
}