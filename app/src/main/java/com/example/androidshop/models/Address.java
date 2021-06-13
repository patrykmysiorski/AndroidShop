package com.example.androidshop.models;

public class Address {
    String name;
    String city;
    String phoneNumber;
    String postalCode;
    String address;
    boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(name);
        stringBuilder.append(", ");
        stringBuilder.append(city);
        stringBuilder.append(", ");
        stringBuilder.append(address);
        stringBuilder.append(", ");
        stringBuilder.append(postalCode);
        stringBuilder.append(", ");
        stringBuilder.append(phoneNumber);
        return stringBuilder.toString();
    }

    public Address() {
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getAddress() {
        return address;
    }
}
