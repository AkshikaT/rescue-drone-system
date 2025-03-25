package ca.mcmaster.se2aa4.island.team18;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class Echo implements DroneCommand{

    private final Logger logger = LogManager.getLogger();
    private final Drone drone;
    private String relativeDirection;

    public Echo (Drone drone, String relativeDirection) {
        this.drone = drone;
        this.relativeDirection = relativeDirection;
    }

    @Override
    public Decision execute() {        
        Direction currDirection = drone.getDirection();
        relativeDirection = getNewDirection(relativeDirection, currDirection);

        JSONObject decision = new JSONObject();
        decision.put("action", "echo"); 
        JSONObject parameters = new JSONObject();
        parameters.put("direction", relativeDirection);
        decision.put("parameters", parameters);

        logger.info("** Decision: {}",decision.toString());
        return new Decision (decision.toString());
    }

    
    public String getNewDirection(String relativeDirection, Direction currDirection) {
        String upperDirection = relativeDirection.toUpperCase();
        
        if ("R".equals(upperDirection)) {
            return currDirection.getRightDirection();
        } else if ("L".equals(upperDirection)) {
            return currDirection.getLeftDirection();
        } else if ("F".equals(upperDirection)) {
            return currDirection.name();
        } else {
            throw new IllegalArgumentException("Invalid relative direction: " + relativeDirection);
        }
    }
}
