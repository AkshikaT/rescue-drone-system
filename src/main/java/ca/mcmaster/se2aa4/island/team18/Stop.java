package ca.mcmaster.se2aa4.island.team18;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class Stop implements DroneState{
    private Drone drone;
    private final Logger logger = LogManager.getLogger();

    public Stop(Drone drone) {
        this.drone = drone;
    }

    @Override
    public Decision makeDecision() {
        return drone.stop();
    }

    @Override
    public void handleResponse(String response) {
        JSONObject responseJson = new JSONObject(response);
        logger.info("Drone has stopped. Final response: {}", responseJson);
    }

    @Override
    public DroneState getNextState() {
        return this;
    }


    
}
