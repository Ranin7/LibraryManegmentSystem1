package com.example.librarymangmentsystem.models.interfaces;

import com.example.librarymangmentsystem.models.Role;

import java.util.List;

public interface RoleDAO {
    List<Role> getAllRoles();
    Role getRoleById(int roleId);
    Role getRoleByName(String roleName);
    boolean addRole(Role role);
    boolean updateRole(Role role);
    boolean deleteRole(int roleId);
}