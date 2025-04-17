package org.Emberalive.account;

import org.Emberalive.db_access.Db_Access;

public class User {
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
    public void setPassword(String password) {
        //encrypting the password
        this.password = Db_Access.hashPassword(password);
        //registering the User
        // this allows me to know if it is a User or an admin
    }

    public String getRole() {
        return "user";
    }
}
