package com.example.androidshop.models;

import java.io.Serializable;

public class Product implements Serializable {
    String description;
    String name;
    String id;
    String imageUrl;
    String price;
    String type;

    public Product() {
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }
}
