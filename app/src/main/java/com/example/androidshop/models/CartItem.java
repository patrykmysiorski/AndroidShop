package com.example.androidshop.models;

public class CartItem {
    String productName;
    String productPrice;
    String totalPrice;
    String totalQuantity;
    String url;

    public CartItem(String productName, String productPrice, String totalPrice, String totalQuantity, String url) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.totalPrice = totalPrice;
        this.totalQuantity = totalQuantity;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public CartItem() {
    }

    public String getProductName() {
        return productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public String getTotalQuantity() {
        return totalQuantity;
    }
}
