package com.example.librarymangmentsystem.models;

import java.security.Permission;
import java.util.List;

public class Role {
    private String name;
    private List<Permission> permissions;

    public Role(String name, List<Permission> permissions) {
        this.name = name;
        this.permissions = permissions;
    }

    public String getName() {
        return name;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public boolean hasPermission(String action) {
        return permissions.stream().anyMatch(p -> p.getActions().equals(action));
    }
}