package com.example.librarymangmentsystem.models;

import javax.persistence.*;

@Entity
@Table(name = "permissions")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "permission_name", nullable = false, unique = true)
    private String permissionName;



    // Constructor
    public Permission() {
    }

    public Permission(String permissionName, String description) {
        this.permissionName = permissionName;

    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

}
