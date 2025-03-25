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
    private int turnCounter = 0;
    private boolean outOfRange = false;
    private int uTurnCounter = 0;
    private int echoCounter = 0;
    private int range;
    private final Creeks creek;
    private final Sites site;
    private final ArrayList<String> creekId;
    private static final int MAX_CREEKS = 10;
    private static final int OUT_OF_RANGE_THRESHOLD = 12;
   

    public GridSearch(Drone drone, Creeks creek, Sites site) {
        this.drone = drone;
        this.scan = new Scan();
        this.creek = creek;
        this.site = site;
        this.creekId = creek.getCreeks();
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
                return drone.echo("F");
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
                return drone.turnLeft();
            }
        }

        if (turnCounter == 0) {
            turnCounter++;
            return drone.scan();
        } else if (turnCounter == 1) {
            if (echoCounter % 2 == 0) {
                turnCounter++;
                return drone.echo("F");
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
            processCreeks(extras);
            processSites(extras);
            processEchoResults(extras);
        }
        
    }

    @Override
    public boolean stateCompleted() {
        if (outOfRange && range <= 0) {
            return true;
        }
        if (creekId.size() == MAX_CREEKS && site.getSite() != null) {
            logger.info("All targets found. Transitioning to Stop state.");
            return true;
        }
        else return false;
    }

    private void processCreeks(JSONObject extras) {
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
    }

    private void processSites(JSONObject extras) {
        if (extras.has("sites")) {
            JSONArray sitesArray = extras.getJSONArray("sites");
            if (sitesArray.length() > 0) {
                site.foundSite(sitesArray.getString(0));
                site.addCoord(drone.position.getX(), drone.position.getY());
                logger.info("Emergency site found: {}", site.getSite());
            }
        }
    }

    private void processEchoResults(JSONObject extras) {
        if (extras.has("found")) {
            String found = extras.getString("found");
            if ("OUT_OF_RANGE".equals(found)) {
                range = extras.optInt("range"); 
                logger.info("Out of range detected");
                    if (range <= OUT_OF_RANGE_THRESHOLD)
                        outOfRange = true;         
            }
        }
    }

}