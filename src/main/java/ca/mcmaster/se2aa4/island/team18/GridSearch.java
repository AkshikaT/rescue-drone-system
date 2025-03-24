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
    private int turnCounter = 0;
    private boolean outOfRange = false;
    private int uTurnCounter = 0;
    private int rowCount = 0;
    private int count = 0;
    private int echoCounter = 0;
    private int range;
    private Creeks creek = new Creeks();
    private Sites site = new Sites();
    private ArrayList<String> creekId = creek.getCreeks();
   

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
                uTurnCounter++;
                return drone.flyForward();
            }
            else if (uTurnCounter == 2) {
                uTurnCounter++;
                return echo.takeDecision("F");
            }
            else if (uTurnCounter == 3) {
                outOfRange = false;
                uTurnCounter++;
                return drone.turnLeft();
            }
            else if (uTurnCounter == 4) {
                uTurnCounter++;
                return drone.turnLeft();
            }
            else if (uTurnCounter == 5) {
                outOfRange = false;
                uTurnCounter = 0;
                rowCount++;
                return drone.turnLeft();
            }
        }

        if (turnCounter == 0) {
            turnCounter++;
            return scan.takeDecision();
        } else if (turnCounter == 1) {
            if (echoCounter % 2 == 0) {
                turnCounter++;
                return echo.takeDecision("F");
            } else {
                turnCounter++;
                return makeDecision();
            }
        } else if (turnCounter == 2) {
            turnCounter = 0;
            echoCounter++; 
            return drone.flyForward();
        } else {
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
                    String currentCreek = creeksArray.getString(i);
                if (!creekId.contains(currentCreek)) { // Check for duplicates
                    creek.addCreek(currentCreek);
                    creek.addCoord(drone.position.getX(), drone.position.getY());
                    logger.info("Creek position: {}, {}", drone.position.getX(), drone.position.getY());

                }
        }
                logger.info("Creeks found: {}", creekId);
            }

            // Process emergency site
            if (extras.has("sites")) {
                JSONArray sitesArray = extras.getJSONArray("sites");
                if (sitesArray.length() > 0) {
                    site.foundSite(sitesArray.getString(0));
                    site.addCoord(drone.position.getX(), drone.position.getY());
                    logger.info("Emergency site found: {}", site.getSite());
                }
            }

            // Process echo results
            if (extras.has("found")) {
                String found = extras.getString("found");
                if ("OUT_OF_RANGE".equals(found)) {
                    range = extras.optInt("range"); // Default to -1 if range is not present
                    logger.info("Out of range detected");
                        if (range <= 12)
                            outOfRange = true;
                    
                    
                }
            }


        }
        
    }

    @Override
    public DroneState getNextState() {
        if (creekId.size() == 10 && site.getSite() != null) {
            logger.info("All targets found. Transitioning to Stop state.");
            return new Stop(drone); 
        }
        if (outOfRange && range <= 0) {
            return new Stop(drone);
        }
        return this; 
    }
}