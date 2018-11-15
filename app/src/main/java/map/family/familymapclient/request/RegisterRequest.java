package map.server.request;

/**
 * Holds the request data for the register service
 */
public class RegisterRequest {
    /**
     * Requested user name
     */
    private String userName = null;

    /**
     * Requested password
     */
    private String password = null;

    /**
     * requested email
     */
    private String email = null;

    /**
     * requested first name
     */
    private String firstName = null;

    /**
     * requested last name
     */
    private String lastName = null;

    /**
     * requested gender ("f" or "m")
     */
    private String gender = null;

    /**
     * Constructs a request with initialized data
     * @param userName quested user name
     * @param password Requested password
     * @param email requested email
     * @param firstName requested first name
     * @param lastName requested last name
     * @param gender requested gender ("f" or "m")
     */
    public RegisterRequest(String userName, String password, String email, String firstName,
                           String lastName, String gender) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    /**
     * Constructs a register request without initializing data
     */
    public RegisterRequest() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
