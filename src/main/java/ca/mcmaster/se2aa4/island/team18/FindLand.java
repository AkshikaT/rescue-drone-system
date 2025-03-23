package ca.mcmaster.se2aa4.island.team18;

import org.json.JSONObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FindLand implements DroneState{
    private final Drone drone;
    private final Echo echo;
    private boolean echoDone = false;
    private boolean groundFound = false;
    private boolean faceLand = false;
    private boolean findTop = false;
    private final String[] echoOptions = {"F", "L", "R"};
    private int echoIndex = -1;
    protected boolean patrol = false;

    private final Logger logger = LogManager.getLogger();

    public FindLand(Drone drone) {
        this.drone = drone;
        this.echo = new Echo(drone);
    }

    public Decision makeDecision() {

        if (groundFound && !faceLand) {
            faceLand = true;
                return drone.turnRight();
            
        }

        // Echo F, L, and R
        if (!echoDone) {
            echoIndex = (echoIndex + 1) % echoOptions.length;
            echoDone = true;
            return echo.takeDecision("R");
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

    @Override
    public DroneState getNextState() {
        if (findTop) {
            logger.info("ground starts: x = {}, y = {} ", drone.topGroundCoor[0], drone.topGroundCoor[1]);
        }
        if (groundFound && faceLand) {
            logger.info("Now flying to land");
            return new FlyToLand(drone, echo); 
        }
        return this; 
    }


}