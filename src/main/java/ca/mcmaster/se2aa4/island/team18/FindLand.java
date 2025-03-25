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
    protected boolean patrol = false;

    private final Logger logger = LogManager.getLogger();

    public FindLand(Drone drone) {
        this.drone = drone;
    }

    @Override
    public Decision makeDecision() {

        // If ground has been found but the drone has not yet faced it, turn right
        if (groundFound && !faceLand) {
            faceLand = true;
                return drone.turnRight();
            
        }

        // If echo has not been performed yet, perform an echo scan to detect land
        if (!echoDone) {
            echoDone = true;
            return drone.echo("R");
        }

        // Fly forward is no ground is found
        echoDone = false;
        return drone.flyForward();
    }

    @Override
    public void handleResponse(String response) {
        JSONObject responseJson = new JSONObject(response);
        JSONObject extras = responseJson.optJSONObject("extras");
        
        if (extras != null && extras.has("found")) {
            String found = extras.getString("found");

            // If the drone detects ground, record its position
            if ("GROUND".equals(found)) {
                int range = extras.getInt("range");
                groundFound = true;
                findTop = true;
                drone.topGroundCoor[1] = range + drone.y;
                drone.topGroundCoor[0] = drone.x;      
            }
        }
    }

    @Override
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