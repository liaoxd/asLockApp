package com.kiplening.demo.module;

/**
 * Created by MOON on 11/16/2016.
 */

public class Settings {
    private boolean isLocked;
    private String password;
    private String email;

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
