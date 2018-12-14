package map.family.familymapclient.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import map.family.familymapclient.model.Model;

/**
 * Created by mradams on 11/15/18.
 */

public class HttpClient {
    /**
     * Singleton instance of the HttpClient class
     */
    private static HttpClient instance = null;
    /**
     *
     */
    private String serverHost;
    private String serverPort;
    HttpURLConnection http;

    /**
     * Singleton method for getting an instance of the HttpClient class
     * @return instance of the HttpClient class
     */
    public static HttpClient getInstance() {
        if (instance == null) {
            instance = new HttpClient();
        }
        return instance;
    }

    /**
     * Sets the port and host ip addresses
     * @param serverHost host ip address
     * @param serverPort port address
     */
    public void setServer(String serverHost, String serverPort) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    /**
     * Make a get request to the Family Map Server
     * @param urlPath URL path for the requested service (not including the server host or port)
     * @return JSON string containing the response data or error information
     */
    public String getRequest(String urlPath) {
        try {
            URL url = new URL("http://" + instance.serverHost + ":" + instance.serverPort + urlPath);
            http = (HttpURLConnection)url.openConnection();
            http.setRequestMethod("GET");
            http.setDoOutput(false);
            if (Model.getInstance().authTokenExists()) {
                http.addRequestProperty("Authorization", Model.getInstance().getUserAuthToken().getAuthToken());
            }
            http.connect();
            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                respBody.close();
                return respData;
            }
            else {
                return "ERROR: " + http.getResponseMessage();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            return "IOException occurred";
        }
    }

    /**
     * Make a post request to the Family Map Server
     * @param requestData JSON string holding the request data.  Set as null if there is no request data
     * @param urlPath URL path for the requested service (not including the server host or port)
     * @return JSON string containing the response data or error information
     */
    public String postRequest(String requestData, String urlPath) {
        try {
            URL url = new URL("http://" + instance.serverHost + ":" + instance.serverPort + urlPath);
            http = (HttpURLConnection)url.openConnection();
            http.setRequestMethod("POST");
            if (requestData != null) {
                http.setDoOutput(true);
            }
            else {
                http.setDoOutput(false);
            }
            if (Model.getInstance().authTokenExists()) {
                http.addRequestProperty("Authorization", Model.getInstance().getUserAuthToken().getAuthToken());
            }
            http.connect();
            if (requestData != null) {
                OutputStream requestBody = http.getOutputStream();
                OutputStreamWriter sw = new OutputStreamWriter(requestBody);
                sw.write(requestData);
                sw.flush();
                requestBody.close();
            }
            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream responseBody = http.getInputStream();
                String responseData = readString(responseBody);
                responseBody.close();
                return responseData;
            }
            else {
                return "ERROR: " + http.getResponseMessage();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            return "IOException occurred";
        }
    }

    /**
     * Reads a string from InputString
     * @param is InputString instance to read from
     * @return String that is read from the input string
     * @throws IOException Exception occurs there is a problem reading the input string
     */
    private static String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

}
