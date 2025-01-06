package com.example.librarymangmentsystem.models;

import javax.persistence.*;

@Entity
@Table(name = "permissions")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "permission_id")
    private int id;

    @Column(name = "permission_name", nullable = false, unique = true)
    private String name;



    public Permission() {}

    public Permission(String name) {
        this.name = name;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", name='" + name + '\'' +

                '}';
    }
}
