package ca.mcmaster.se2aa4.island.team18;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class GridSearch implements DroneState {
    private final Logger logger = LogManager.getLogger();

    private final Drone drone;
    private final Scan scan;
    private final Echo echo;
    private final ArrayList<String> creeks = new ArrayList<>();
    private String site = "";
    private int turnCounter = 0;
    private boolean outOfRange = false;
    private int uTurnCounter = 0;
   

    public GridSearch(Drone drone) {
        this.drone = drone;
        this.scan = new Scan();
        this.echo = new Echo(drone);
    }

    @Override
    public Decision makeDecision() {

        if (outOfRange) {
            if (uTurnCounter == 0) {
                uTurnCounter++;
                return drone.turnLeft();
            }
            else if (uTurnCounter == 1) {
                outOfRange = false;
                uTurnCounter++;
                return drone.turnLeft();
            }
            else if (uTurnCounter == 2) {
                uTurnCounter++;
                return drone.turnRight();
            }
            else if (uTurnCounter == 3) {
                outOfRange = false;
                uTurnCounter = 0;
                return drone.turnRight();
            }
        }

        if (turnCounter == 0) {
            // Scan the current tile
            turnCounter++;
            return scan.takeDecision();
        } else if (turnCounter == 1) {
            // Echo forward to check for boundaries
            turnCounter++;
            return echo.takeDecision("F");
        }   
        else {
            turnCounter = 0;
            return drone.flyForward();
        }
    }

    @Override
    public void handleResponse(String response) {
        JSONObject responseJson = new JSONObject(response);
        JSONObject extras = responseJson.optJSONObject("extras");

        if (extras != null) {
            // Process creeks
            if (extras.has("creeks")) {
                JSONArray creeksArray = extras.getJSONArray("creeks");
                for (int i = 0; i < creeksArray.length(); i++) {
                    creeks.add(creeksArray.getString(i));
                }
                logger.info("Creeks found: {}", creeks);
            }

            // Process emergency site
            if (extras.has("sites")) {
                JSONArray sitesArray = extras.getJSONArray("sites");
                if (sitesArray.length() > 0) {
                    site = sitesArray.getString(0);
                    logger.info("Emergency site found: {}", site);
                }
            }

            // Process echo results
            if (extras.has("found")) {
                String found = extras.getString("found");
                if ("OUT_OF_RANGE".equals(found)) {
                    Direction currentDirection = drone.getDirection();
            
                    int range = extras.optInt("range", -1); // Default to -1 if range is not present
                    logger.info("Out of range detected");
            
                    if (range <= 10) {
                        outOfRange = true;
                    } 
                    
                }
            }
        }
        
    }

    @Override
    public DroneState getNextState() {
        if (creeks.size() == 10 && !site.isEmpty()) {
            logger.info("All targets found. Transitioning to Stop state.");
            return new Stop(drone); 
        }
        return this; 
    }
}