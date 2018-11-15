package map.family.familymapclient.model;

public class Model {
    /**
     * Singleton instance of the Model class
     */
    private static Model instance = null;

    /**
     * Singleton function for getting the singleton instance of this class
     * @return instance of the Model class
     */
    public static Model getInstance() {
        if (instance == null) {
            instance = new Model();
        }
        return instance;
    }


}
