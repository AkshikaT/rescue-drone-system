package ca.mcmaster.se2aa4.island.team18;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.json.JSONObject;

public class Echo extends MapReader{

    private final Logger logger = LogManager.getLogger();

    public Echo () {
        super();
    }

    public String takeDecision(String relativeDirection) {
        JSONObject decision = new JSONObject();
        decision.put("action", "echo"); 
        JSONObject parameters = new JSONObject();
        parameters.put("direction", relativeDirection);
        decision.put("parameters", parameters);
        logger.info("** Decision: {}",decision.toString());
        return decision.toString();
    }
    
}
