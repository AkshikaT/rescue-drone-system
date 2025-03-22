package ca.mcmaster.se2aa4.island.team18;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;


public class FindLand implements DroneState{

    private final Logger logger = LogManager.getLogger();

    private final Drone drone;
    private final Echo echo;
    private boolean echoDone = false;

    private boolean findTop = false;
    private boolean turnDone = false;
    protected int [] topGroundCoor = new int [2];

    public FindLand(Drone drone) {
        this.drone = drone;
        this.echo = new Echo(drone);
    }

    public Decision makeDecision() {
        if (!turnDone) {                    // only turn once at the beginning
            turnDone = true;
            return drone.turnRight();
        }

        else if (turnDone && !echoDone) {
            echoDone = true;
            return echo.takeDecision("L");
        } 
        echoDone = false;
        return drone.flyForward();
    }

    public void handleResponse(String response) {
        JSONObject responseJson = new JSONObject(response);
        JSONObject extras = responseJson.optJSONObject("extras");

        if (extras != null && extras.has("found")) {
            String found = extras.getString("found");
            int range = extras.getInt("range");
            if("GROUND".equals(found)) {
                findTop = true;
                topGroundCoor[0] = drone.y;
                topGroundCoor[1] = range;
            }
        }
    }

    @Override
    public DroneState getNextState() {
        if (findTop) {
            logger.info("ground starts: x = {}, y = {} ", topGroundCoor[0], topGroundCoor[1]);
            return new FlyToLand(drone, echo); 
        }
        return this; 
    }


}