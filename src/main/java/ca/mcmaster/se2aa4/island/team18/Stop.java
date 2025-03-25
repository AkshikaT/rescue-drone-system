package ca.mcmaster.se2aa4.island.team18;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class Stop implements DroneState{
    private final Logger logger = LogManager.getLogger();

    @Override
    public Decision makeDecision() {
        JSONObject command = new JSONObject();
        command.put("action", "stop");

        return new Decision(command.toString());
    }

    @Override
    public void handleResponse(String response) {
        JSONObject responseJson = new JSONObject(response);
        logger.info("Drone has stopped. Final response: {}", responseJson);
    }

    @Override
    public boolean stateCompleted() {
        return true;
    }


    
}
