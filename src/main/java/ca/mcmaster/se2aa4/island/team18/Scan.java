package ca.mcmaster.se2aa4.island.team18;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class Scan {

    private final Logger logger = LogManager.getLogger();

    public Scan() {
        super();
    }

    public Decision takeDecision() {
        JSONObject decision = new JSONObject();
        decision.put("action", "scan");
        logger.info("** Decision: {}",decision.toString());
        return new Decision (decision.toString());
    }
}
