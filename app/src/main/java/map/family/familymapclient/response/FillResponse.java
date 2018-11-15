package map.server.response;

/**
 * Holds response data for the fill service
 */
public class FillResponse {
    /**
     * Response of a successful or unsuccessful fill
     */
    private String message = null;

    /**
     * Constructor for a fill response
     */
    public FillResponse() {
    }

    /**
     * Constructor for when number of persons and events is known
     * @param numberOfPersons Number of persons that are to be filled into the database
     * @param numberOfEvents Number of events that are to be filled into the database
     */
    public FillResponse(int numberOfPersons, int numberOfEvents) {
        setSuccessResponse(numberOfPersons, numberOfEvents);
    }

    /**
     * Sets a new success response that includes the number of persons and events that are added to the database
     * @param numberOfPersons Number of persons added to the database
     * @param numberOfEvents Number of events added to the database
     */
    public void setSuccessResponse(int numberOfPersons, int numberOfEvents) {
        message = "Successfully added " + numberOfPersons + " persons and " + numberOfEvents
                + " events to the database.";
    }

    /**
     * Sets the error message if an error occurs in the fill service
     * @param message String explaining the error
     */
    public void setErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
