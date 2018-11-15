package map.family.familymapclient.memberobjects;

import java.util.UUID;

/**
 * Auth objects represent unique authorization identifiers that should be assigned to users once
 * they login to the system
 */
public class Auth {
    /**
     * The auth token is the unique identifier for the user
     */
    private String authToken = null;

    /**
     * This is the user that is logged into the system and assigned this AuthToken
     */
    private String username = null;

    /**
     * The constructor will set the user and then assign a unique auth token to that user
     * @param username the username of the user that is to be assigned this AuthToken
     */
    public Auth(String username) {
        this.username = username;
        this.authToken = UUID.randomUUID().toString();
    }

    /**
     * Generic constructor will not initialize anything
     */
    public Auth() {
    }

    /**
     * Checks if any of the parameters in the Auth object are null
     * @return boolean indicating whether any of the parameters are null
     */
    public boolean hasNullData() {
        if (username == null || authToken == null) {
            return true;
        }
        return false;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
