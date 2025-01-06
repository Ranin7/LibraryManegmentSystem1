package com.example.librarymangmentsystem;

import java.util.HashSet;
import java.util.Set;

public class UserSession {
    private static UserSession instance;
    private String userRole;
    private Set<Integer> userPermissionIds;

    private UserSession() {
        userPermissionIds = new HashSet<>();
    }

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public void setUserRole(int roleId) {
        if (roleId == 1) {
            this.userRole = "Admin";
        } else if (roleId == 2) {
            this.userRole = "Librarian";
        } else {
            this.userRole = "User";
        }

        loadPermissions(userRole);
    }

    public String getUserRole() {
        return userRole;
    }

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

    public Set<Integer> getUserPermissionIds() {
        return userPermissionIds;
    }
    public boolean hasPermission(int permissionId) {
        return userPermissionIds.contains(permissionId);
    }
}

