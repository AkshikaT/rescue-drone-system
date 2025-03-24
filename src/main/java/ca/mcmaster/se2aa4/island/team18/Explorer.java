package ca.mcmaster.se2aa4.island.team18;

import java.io.StringReader;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.json.JSONTokener;

import eu.ace_design.island.bot.IExplorerRaid;

public class Explorer implements IExplorerRaid {

    private final Logger logger = LogManager.getLogger();
    private Drone drone;
    private DroneState currentState;
    private Creeks creek;
    private Sites site;


    @Override
    public void initialize(String s) {
        logger.info("** Initializing the Exploration Command Center");
        JSONObject info = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Initialization info:\n {}",info.toString(2));
        String direction = info.getString("heading");
        Integer batteryLevel = info.getInt("budget");

        drone = new Drone(Direction.valueOf(direction), batteryLevel);
        currentState = new MapDimensions(drone);
        logger.info("The drone is facing {}", direction);
        logger.info("Battery level is {}", batteryLevel);
    }

    @Override
    public String takeDecision() {
        logger.info("BATTERY LEVEL AT: {}", drone.getBatteryLevel());
        Decision decision = currentState.makeDecision();
        logger.info("** Decision: {}",decision.toString());
        logger.info("current position: {}, {}", drone.position.getX(), drone.position.getY());
        return decision.toString();
    }

    @Override
    public void acknowledgeResults(String s) {
        if (drone.getBatteryLevel() <= 0) {
            Decision decision = drone.stop();
            logger.info("** Decision: {}",decision.toString());
        }
        else {
            JSONObject response = new JSONObject(new JSONTokener(new StringReader(s)));
            logger.info("** Response received:\n"+response.toString(2));
            Integer cost = response.getInt("cost");
            logger.info("The cost of the action was {}", cost);
            String status = response.getString("status");
            logger.info("The status of the drone is {}", status);
            JSONObject extraInfo = response.getJSONObject("extras");
            logger.info("Additional information received: {}", extraInfo);

            drone.consumeBattery(cost);
            currentState.handleResponse(response.toString());
            currentState = currentState.getNextState();
        }
    }

    @Override
    public String deliverFinalReport() {
        Path shortestPath = new Path();
        ArrayList<String> creekList = creek.getCreeks();
        String emergencySite = site.getSite();

        String closestCreek = shortestPath.findShortestPath(creekList, creek.getXCoords(), creek.getYCoords(), site.getXCoord(), site.getYCoord());

        logger.info("Creeks located: " + creekList);
        logger.info("Emergency site located: " + emergencySite);
        logger.info("Site position: " + site.getXCoord() + ", " + site.getYCoord());
        logger.info("Closest creek to site: " + closestCreek);
        logger.info("Closest creek is located at: " + creek.creekXCoord(closestCreek) + ", " + creek.creekYCoord(closestCreek));

        return closestCreek;
    }

}
