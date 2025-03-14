package ca.mcmaster.se2aa4.island.team18;

import java.io.StringReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.json.JSONObject;
import org.json.JSONTokener;

public class MapReader extends Explorer{

    private final Logger logger = LogManager.getLogger();

    // Constructor to initialize the Explorer class
    public MapReader() {
        super();
    }

    // create ECHO request in this format: { "action": "echo", "parameters": { "direction": "E" } }
    private String getEchoRequest(String direction) {
        JSONObject echoRequest = new JSONObject();
        echoRequest.put("action", "echo");
        JSONObject parameters = new JSONObject();
        parameters.put("direction", direction);
        echoRequest.put("parameters", parameters);
        logger.info("Echo request: " + echoRequest.toString());
        return echoRequest.toString();
    }

    public void getEchoResponse(String request) {
        acknowledgeResults(request);
    }

}
