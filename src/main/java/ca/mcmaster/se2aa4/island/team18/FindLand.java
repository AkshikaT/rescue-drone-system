package ca.mcmaster.se2aa4.island.team18;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class FindLand implements DroneState{
    private final Drone drone;
    private boolean echoDone = false;
    private boolean groundFound = false;
    private boolean faceLand = false;
    private boolean findTop = false;
    private int echoIndex = -1;
    protected boolean patrol = false;

    private final Logger logger = LogManager.getLogger();

    public FindLand(Drone drone) {
        this.drone = drone;
    }

    public Decision makeDecision() {

        if (groundFound && !faceLand) {
            faceLand = true;
                return drone.turnRight();
            
        }

        if (!echoDone) {
            echoDone = true;
            return drone.echo("R");
        }

        // Fly forward is no ground is found
        echoDone = false;
        return drone.flyForward();
    }

    public void handleResponse(String response) {
        JSONObject responseJson = new JSONObject(response);
        JSONObject extras = responseJson.optJSONObject("extras");
        
        if (extras != null && extras.has("found")) {
            String found = extras.getString("found");

            if ("GROUND".equals(found)) {
                int range = extras.getInt("range");
                groundFound = true;
                findTop = true;
                drone.topGroundCoor[1] = range + drone.y;
                drone.topGroundCoor[0] = drone.x;

                
            }
        }
    }

    public boolean stateCompleted() {
        if (findTop) {
            logger.info("ground starts: x = {}, y = {} ", drone.topGroundCoor[0], drone.topGroundCoor[1]);
        }
        if (groundFound && faceLand) {
            logger.info("Now flying to land");
            return true;
        }
        else return false;
    }


}