package org.example.Account;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class IT_employee {
    Account acc = new Account();
    private String ItUsername;
    private String ItPassword;
    private String role = "employee";

    public String getUsername() {
        return ItUsername;
    }
    public void setUsername(String username) {
        ItUsername = username;
    }
    public String getPassword() {
        return ItPassword;
    }
    public boolean setPassword(String password) {
        //encrypting the password
        this.ItPassword = BCrypt.withDefaults().hashToString(12, password.toCharArray());
        boolean success = acc.register(getUsername(), getPassword(), role);
        return success;
    }
}

