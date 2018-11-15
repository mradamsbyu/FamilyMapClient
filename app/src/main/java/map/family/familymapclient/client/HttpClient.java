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

public class Client {
    // The Client's "main" method.
    // The "args" parameter should contain two command-line arguments:
    // 1. The IP address or domain name of the machine running the server
    // 2. The port number on which the server is accepting Client connections
    public static void main(String[] args) {

        String serverHost = args[0];
        String serverPort = args[1];

        getGameList(serverHost, serverPort);
        claimRoute(serverHost, serverPort);
    }

    // The getGameList method calls the server's "/games/list" operation to
    // retrieve a list of games running in the server in JSON format
    private static void getGameList(String serverHost, String serverPort) {

        // This method shows how to send a GET request to a server

        try {
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/person");
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            http.setRequestMethod("GET");
            // Indicate that this request will not contain an HTTP request body
            http.setDoOutput(false);

            // Add an auth token to the request in the HTTP "Authorization" header
            http.addRequestProperty("Authorization", "afj232hj2332");
            // Specify that we would like to receive the server's response in JSON
            // format by putting an HTTP "Accept" header on the request (this is not
            // necessary because our server only returns JSON responses, but it
            // provides one more example of how to add a header to an HTTP request).
            http.addRequestProperty("Accept", "application/json");

            // Connect to the server and send the HTTP request
            http.connect();
            // By the time we get here, the HTTP response has been received from the server.
            // Check to make sure that the HTTP response from the server contains a 200
            // status code, which means "success".  Treat anything else as a failure.
            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {

                // Get the input stream containing the HTTP response body
                InputStream respBody = http.getInputStream();
                // Extract JSON data from the HTTP response body
                String respData = readString(respBody);
                // Display the JSON data returned from the server
                System.out.println(respData);
            }
            else {
                // The HTTP response status code indicates an error
                // occurred, so print out the message from the HTTP response
                System.out.println("ERROR: " + http.getResponseMessage());
            }
        }
        catch (IOException e) {
            // An exception was thrown, so display the exception's stack trace
            e.printStackTrace();
        }
    }

    private static void login(String serverHost, String serverPort, String userName, String password) {
        try {
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/login");
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);	// There is a request body
            http.addRequestProperty("Authorization", Model.getInstance().getUserAuthToken().getAuthToken());
            http.connect();
            String reqData =
                    "{" +
                            "\"userName\": \"" + userName + "\"," +
                            "\"password\": \"" + password + "\"" +
                            "}";
            OutputStream reqBody = http.getOutputStream();
            OutputStreamWriter sw = new OutputStreamWriter(reqBody);
            sw.write(reqData);
            sw.flush();
            reqBody.close();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                System.out.println("Route successfully claimed.");
            }
            else {
                System.out.println("ERROR: " + http.getResponseMessage());
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
        The readString method shows how to read a String from an InputStream.
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
