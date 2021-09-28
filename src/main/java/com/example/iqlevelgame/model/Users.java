package com.example.iqlevelgame.model;

public class Users {
    private String name,email,password,refercode;
    private long coins=25;

    public long getCoins() {
        return coins;
    }

    public void setCoins(long coins) {
        this.coins = coins;
    }

    public Users() {
    }

    public Users(String name, String email, String password, String refercode) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.refercode = refercode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRefercode() {
        return refercode;
    }

    public void setRefercode(String refercode) {
        this.refercode = refercode;
    }
}
