package com.example.librarymangmentsystem;

import java.util.HashSet;
import java.util.Set;

public class UserSession {
    private static UserSession instance;

    private String userRole;
    private Set<Integer> userPermissionIds;

    // Private constructor to prevent instantiation
    private UserSession() {
        userPermissionIds = new HashSet<>();
    }

    // Get the singleton instance
    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    // Set the user role
    public void setUserRole(String role) {
        this.userRole = role;
        loadPermissions(role);
    }

    // Get the user role
    public String getUserRole() {
        return userRole;
    }

    // Load permissions based on the user role
    private void loadPermissions(String role) {
        userPermissionIds.clear();

        if ("Librarian".equals(role)) {
            userPermissionIds.add(1);  // Add Book
            userPermissionIds.add(10); // Add Reservation
            userPermissionIds.add(15); // View Details
            userPermissionIds.add(14); // View All Books
            userPermissionIds.add(11); // View History
            userPermissionIds.add(16);
            userPermissionIds.add(9);
            userPermissionIds.add(12);
            userPermissionIds.add(13);
        } else if ("User".equals(role)) {
            userPermissionIds.add(15); // View Details
            userPermissionIds.add(14); // View All Books
        }
    }

    // Get permissions
    public Set<Integer> getUserPermissionIds() {
        return userPermissionIds;
    }

    // Check if a permission exists
    public boolean hasPermission(int permissionId) {
        return userPermissionIds.contains(permissionId);
    }
}
