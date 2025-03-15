package ca.mcmaster.se2aa4.island.team18;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class Scan extends MapReader {

    private final Logger logger = LogManager.getLogger();

    public Scan() {
        super();
    }

    public String takeDecision(String direction) {
        JSONObject decision = new JSONObject();
        decision.put("action", "scan");
        logger.info("** Decision: {}",decision.toString());
        return decision.toString();
    }
}
