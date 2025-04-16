package org.Emberalive.account;

import org.Emberalive.db_access.Db_Access;

public class IT_employee {
    Account acc = new Account();
    private String ItUsername;
    private String ItPassword;

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
        this.ItPassword = Db_Access.hashPassword(password);
        String role = "employee";
        return acc.register(getUsername(), getPassword(), role);
    }
}

