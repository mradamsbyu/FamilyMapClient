package map.family.familymapclient.request;

/**
 * Holds the request data for the login service
 */
public class LoginRequest {
    /**
     * Username entered at login
     */
    private String userName = null;

    /**
     * Password entered at login
     */
    private String password = null;

    /**
     * Constructor that initializes user name and password
     * @param userName Username entered by user
     * @param password Password entered by user
     */
    public LoginRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    /**
     * Constructor that does not initialize user name and password
     */
    public LoginRequest() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
