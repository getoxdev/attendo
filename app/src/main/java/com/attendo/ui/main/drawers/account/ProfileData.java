package com.attendo.ui.main.drawers.account;

public class ProfileData {
    String id;
    String username;
    String college;
    String city;
    String Contact;

    public ProfileData(String id, String username, String college, String city, String contact) {
        this.id = id;
        this.username = username;
        this.college = college;
        this.city = city;
        Contact = contact;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getCollege() {
        return college;
    }

    public String getCity() {
        return city;
    }

    public String getContact() {
        return Contact;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setContact(String contact) {
        Contact = contact;
    }
}