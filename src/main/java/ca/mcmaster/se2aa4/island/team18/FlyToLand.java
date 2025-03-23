package ca.mcmaster.se2aa4.island.team18;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class FlyToLand implements DroneState{

    private final Logger logger = LogManager.getLogger();
    
    private final Drone drone;
    private boolean onLand = false;
    private boolean turnDone = false;
    private int count = -1;

    public FlyToLand(Drone drone, Echo echo) {
        this.drone = drone;
    }

    @Override
    public Decision makeDecision() {
        if(!turnDone) {                                     // must go in this sequence and then go forwards to reach ground
            count ++;
            if (count == 0) {
                return drone.flyForward();
            }
            else if (count == 1 || count == 2) {
                return drone.turnLeft();
            }
            else if (count == 3) {
                turnDone = true;
                return drone.turnRight();
            }

        }
        return drone.flyForward();
    }

    @Override
    public void handleResponse(String response) {
        if(drone.x == drone.topGroundCoor[0]) {
            onLand = true;
        }
    }

    @Override
    public DroneState getNextState() {
        if (onLand) {
            logger.info("ground cell is: {}, {}", drone.x, drone.y);
            return new GridSearch(drone);
        }
        return this; // Stay in the current state
    }
}
