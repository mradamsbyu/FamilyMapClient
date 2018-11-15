package map.family.familymapclient.response;


/**
 * Holds response data for the load service
 */
public class LoadResponse {
    /**
     * Response of a successful fill
     */
    private String message = null;

    /**
     * Constructor for a fill response
     */
    public LoadResponse() {
    }

    /**
     * Constructor for when number of users, persons, and events is known
     * @param numberOfUsers Number of users that are to be loaded loaded into the database
     * @param numberOfPersons Number of persons that are to be loaded into the database
     * @param numberOfEvents Number of events that are to be loaded into the database
     */
    public LoadResponse(int numberOfUsers, int numberOfPersons, int numberOfEvents) {
        setSuccessResponse(numberOfUsers, numberOfPersons, numberOfEvents);
    }

    public String getMessage() {
        return message;
    }

    /**
     * Sets a new success response that includes the number of persons and events that are added to the database
     * @param numberOfPersons Number of persons added to the database
     * @param numberOfEvents Number of events added to the database
     */
    public void setSuccessResponse(int numberOfUsers, int numberOfPersons, int numberOfEvents) {
        message = "Successfully added " + numberOfUsers + " users, " + numberOfPersons + " persons, " +
                "and " + numberOfEvents + " events to the database";
    }

    /**
     * Sets an error message if something went wrong in the load service
     * @param message String describing the error that occured
     */
    public void setErrorResponse(String message) {
        this.message = message;
    }
}
