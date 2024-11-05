package com.example.numad24fa_yunxinliu;

public class Contact {
    String name;
    String phone;

    public Contact(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return this.name;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setPhoneNumber(String phone) {
        this.phone = phone;
    }
}
