package ca.mcmaster.se2aa4.island.team18;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.json.JSONObject;

public class Echo extends MapReader{

    private final Logger logger = LogManager.getLogger();
    private Drone drone;

    public Echo (Drone drone) {
        super();
        this.drone = drone;
    }

    public String takeDecision(String relativeDirection) {          // relativeDirection can be F, R, or L for front right and left respectively
        Direction currDirection = drone.getDirection();
        relativeDirection = getNewDirection(relativeDirection, currDirection);
        JSONObject decision = new JSONObject();
        decision.put("action", "echo"); 
        JSONObject parameters = new JSONObject();
        parameters.put("direction", relativeDirection);
        decision.put("parameters", parameters);
        logger.info("** Decision: {}",decision.toString());
        return decision.toString();
    }
    
    public String getNewDirection(String relativeDirection, Direction currDirection) {
        switch(relativeDirection.toUpperCase()) {
            case "R":
                return currDirection.getRightDirection();
            case "L":
                return currDirection.getLeftDirection();
            case "F":
                return currDirection.name();
            default:
                throw new IllegalArgumentException("Invalid relative direction: " + relativeDirection);
        }
    }
}
