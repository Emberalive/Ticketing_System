package org.example.Account;

import at.favre.lib.crypto.bcrypt.BCrypt;


public class user {
    Account acc = new Account();
    private String username;
    private String password;
    // this allows me to know if it is a user or an admin
    private String role = "user";

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
        //encrypting the password
        this.password = BCrypt.withDefaults().hashToString(12, password.toCharArray());
        //registering the user
        acc.register(getUsername(), getPassword(), role);
    }
}
