package sample;

/**
 * Обьект пользователя(логин, пароль)
 */
public class User {
    private String userName;
    private String password;

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public User() {}
    /**getter name*/
    public String getUserName() {
        return userName;
    }
    /**setter name*/
    public void setUserName(String userName) {
        this.userName = userName;
    }
    /**getter password*/
    public String getPassword() {
        return password;
    }
    /**setter password*/
    public void setPassword(String password) {
        this.password = password;
    }

}
