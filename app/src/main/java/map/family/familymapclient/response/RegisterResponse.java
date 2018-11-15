package map.family.familymapclient.response;

/**
 * Holds response data for the register service
 */
public class RegisterResponse {
    /**
     * Non-empty string containing the Person ID of the user's generated Person object
     */
    private String personID = null;
    /**
     * User name passed in with request
     */
    private String authToken = null;
    /**
     * Non-empty auth token string
     */
    private String userName = null;
    /**
     * Error response message for login service
     */
    private String message = null;

    /**
     * Construct a login response
     * @param personID Non-empty string containing the Person ID of the user's generated Person object
     * @param authToken User name passed in with reques
     * @param userName Non-empty auth token string
     */
    public RegisterResponse(String personID, String authToken, String userName) {
        this.personID = personID;
        this.authToken = authToken;
        this.userName = userName;
    }

    /**
     * Generic constructor that does not initialize data
     */
    public RegisterResponse() {
    }

    /**
     * Sets an error message if something goes wrong in the register service
     * @param message String describing the error that occurred
     */
    public void setErrorMessage(String message) {
        this.message = message;
    }

    public String getErrorMessage() {
        return message;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
