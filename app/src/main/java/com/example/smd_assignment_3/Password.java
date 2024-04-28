package com.example.smd_assignment_3;

import java.net.URL;
public class Password {
    private String username;
    private String password;
    private String websiteUrl;
    int id;

    public Password(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Password(String username, String password, String websiteUrl, int id) {
        this.username = username;
        this.password = password;
        this.websiteUrl = websiteUrl;
        this.id=id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }
}
