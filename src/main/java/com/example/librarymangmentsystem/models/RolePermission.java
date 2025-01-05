package com.example.librarymangmentsystem.models;

import com.example.librarymangmentsystem.models.Permission;
import com.example.librarymangmentsystem.models.Role;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.*;

public class RolePermission {

    private static RolePermission instance;

    private final Map<String, Role> roles = new HashMap<>();

    // Private constructor
    private RolePermission() {
        loadRolesAndPermissions();
    }

    // Singleton instance getter
    public static synchronized  RolePermission getInstance() {
        if (instance == null) {
            instance = new  RolePermission();
        }
        return instance;
    }

    // Load roles and permissions from the database
    private void loadRolesAndPermissions() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("library_management_system");
        EntityManager em = emf.createEntityManager();

        try {
            // Fetch all roles
            List<Role> dbRoles = em.createQuery("SELECT r FROM Role r", Role.class).getResultList();

            for (Role role : dbRoles) {
                // Fetch permissions for each role
                Set<Permission> permissions = new HashSet<>(em.createQuery(
                        "SELECT p FROM Permission p JOIN userPermission rp ON p.id = rp.permission.id WHERE rp.role.id = :roleId",
                        Permission.class
                ).setParameter("roleId", role.getId()).getResultList());

                role.setPermissions(permissions);
                roles.put(role.getRoleName(), role);
            }
        } finally {
            em.close();
            emf.close();
        }
    }

    // Get role by name
    public Role getRole(String roleName) {
        return roles.get(roleName);
    }

    // Check if a role has a permission
    public boolean roleHasPermission(String roleName, String permissionName) {
        Role role = roles.get(roleName);
        if (role == null) {
            return false;
        }

        return role.getPermissions().stream()
                .anyMatch(permission -> permission.getPermissionName().equals(permissionName));
    }
}
