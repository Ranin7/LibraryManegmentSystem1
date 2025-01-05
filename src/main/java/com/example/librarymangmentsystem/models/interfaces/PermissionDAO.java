package com.example.librarymangmentsystem.models.interfaces;

import com.example.librarymangmentsystem.models.Permission;
import java.util.List;

public interface PermissionDAO {
    List<Permission> getAllPermissions();
    Permission getPermissionById(int permissionId);
    boolean addPermission(Permission permission);
    boolean updatePermission(Permission permission);
    boolean deletePermission(int permissionId);
}
