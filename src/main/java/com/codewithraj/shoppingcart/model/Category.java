package com.codewithraj.shoppingcart.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String name;





    @PrePersist
    public void prePersist() {
        String categoryId = UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 20)
                .toUpperCase();
        this.id = "CTID" + categoryId;
    }
}
