package map.server.response;

/**
 * Holds response data for the clear service
 */
public class ClearResponse {
    /**
     * Response of a successful or unsuccessful clear
     */
    private String message = null;

    /**
     * Constructor for a clear response
     */
    public ClearResponse() {
    }

    /**
     * Sets a message indicating a successful marriage
     */
    public void setSuccessResponse() {
        message = "Clear succeeded";
    }

    public String getMessage() {
        return message;
    }

    /**
     * Sets an error message if something goes wrong in the clear service
     * @param message String describing the error that occurred
     */
    public void setErrorResponse(String message) {
        this.message = message;
    }
}
