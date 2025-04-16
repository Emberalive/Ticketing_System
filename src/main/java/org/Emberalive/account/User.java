package org.Emberalive.account;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.Emberalive.db_access.Db_Access;

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
        this.password = Db_Access.hashPassword(password);
        //registering the User
        // this allows me to know if it is a User or an admin
        String role = "user";
        return acc.register(getUsername(), getPassword(), role);
    }
}
