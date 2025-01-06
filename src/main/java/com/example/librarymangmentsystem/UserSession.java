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

    // Set the user role and load permissions based on user role ID
    public void setUserRole(int roleId) {
        // Set the role based on the database ID (1 for Admin, 2 for Librarian)
        if (roleId == 1) {
            this.userRole = "Admin";
        } else if (roleId == 2) {
            this.userRole = "Librarian";
        } else {
            this.userRole = "User";
        }

        loadPermissions(userRole);
    }

    // Get the user role
    public String getUserRole() {
        return userRole;
    }

    // Load permissions based on the user role
    private void loadPermissions(String role) {
        userPermissionIds.clear();

        if ("Librarian".equals(role)) {
            userPermissionIds.add(1);
            userPermissionIds.add(10);
            userPermissionIds.add(15);
            userPermissionIds.add(14);
            userPermissionIds.add(11);
            userPermissionIds.add(9);
            userPermissionIds.add(12);
            userPermissionIds.add(13);
        } else if ("User".equals(role)) {
            userPermissionIds.add(15);
            userPermissionIds.add(14);
        } else if ("Admin".equals(role)) {
            userPermissionIds.add(1);
            userPermissionIds.add(10);
            userPermissionIds.add(15);
            userPermissionIds.add(14);
            userPermissionIds.add(11);
            userPermissionIds.add(16);  // Create Account
            userPermissionIds.add(9);
            userPermissionIds.add(12);
            userPermissionIds.add(13);
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

