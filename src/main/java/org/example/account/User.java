package org.example.account;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class User {
    Account acc = new Account();
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public boolean setPassword(String password) {
        //encrypting the password
        this.password = BCrypt.withDefaults().hashToString(12, password.toCharArray());
        //registering the User
        // this allows me to know if it is a User or an admin
        String role = "User";
        return acc.register(getUsername(), getPassword(), role);
    }
}
