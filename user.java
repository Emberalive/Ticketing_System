import at.favre.lib.crypto.bcrypt.BCrypt;

public class user {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }
    //encrypting the password
    public void setUsername(String username) {
        this.username = BCrypt.withDefaults().hashToString(12, username.toCharArray());
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
