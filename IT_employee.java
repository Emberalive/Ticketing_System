import at.favre.lib.crypto.bcrypt.BCrypt;

public class IT_employee {
    private String ItUsername;
    private String ItPassword;

    public String getUsername() {
        return ItUsername;
    }
    //encrypting the password
    public void setUsername(String username) {
        this.ItUsername = BCrypt.withDefaults().hashToString(12, username.toCharArray());
    }
    public String getPassword() {
        return ItPassword;
    }
    public void setPassword(String password) {
        this.ItPassword = password;
    }
}

