package ca.mcmaster.se2aa4.island.team18;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class GridSearch implements DroneState{
    private final Drone drone;
    private final Scan scan;
    private final Echo echo;
    private boolean scanDone = false;
    private boolean flyForwardDone = false;
    private boolean turnDone = false;
    private final ArrayList<String> creeks = new ArrayList<>();
    private String site = "";

    public GridSearch(Drone drone) {
        this.drone = drone;
        this.scan = new Scan();
        this.echo = new Echo(drone);
    }

    @Override
    public Decision makeDecision() {
        if (!scanDone) {
            scanDone = true;
            return scan.takeDecision(); 
        } else if (!flyForwardDone) {
            // Echo forward to check for boundaries before flying
            flyForwardDone = true;
            return echo.takeDecision("F");
        } else if (!turnDone) {
            turnDone = true;
            return drone.turnRight(); 
        } else {
            scanDone = false;
            flyForwardDone = false;
            turnDone = false;
            return drone.flyForward(); // Fly forward to the next tile
        }
    }

    @Override
    public void handleResponse(String response) {
        JSONObject responseJson = new JSONObject(response);
        JSONObject extras = responseJson.optJSONObject("extras");
        
        if (extras != null) {
            if (extras.has("creeks")) {
                JSONArray creeksArray = extras.getJSONArray("creeks");

                if (creeksArray.length() > 0) {
                    for (int i = 0; i < creeksArray.length(); i++) {
                        creeks.add(creeksArray.getString(i));
                    }
                }
                // logger.info("Creeks found: {}", creeks);
            }
            if (extras.has("sites")) {
                JSONArray sitesArray = extras.getJSONArray("sites");
                if (sitesArray.length() > 0) { 
                    site = sitesArray.getString(0); 
                    // logger.info("Site found: {}", site);
                }
            }
            if (extras.has("found")) {
                String found = extras.getString("found");
                if ("OUT_OF_RANGE".equals(found)) {
                    flyForwardDone = true;
                }
            }
        }
    }

    @Override
    public DroneState getNextState() {
        if (!creeks.isEmpty() && !site.isEmpty()) {
            // return new Stop(drone);
        }
        return this; 
    }
}
