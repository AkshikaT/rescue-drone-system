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
    private Battery battery;
    private StateManager commandCenter;
    private Creeks creek = new Creeks();
    private Sites site = new Sites();

    @Override
    public void initialize(String s) {
        logger.info("** Initializing the Exploration Command Center");
        JSONObject info = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Initialization info:\n {}",info.toString(2));
        String direction = info.getString("heading");
        Integer batteryLevel = info.getInt("budget");

        drone = new Drone(Direction.valueOf(direction));
        battery = new Battery(batteryLevel);
        this.commandCenter = new StateManager(drone, battery, creek, site);
        // currentState = new MapDimensions(drone);
        logger.info("The drone is facing {}", direction);
        logger.info("Battery level is {}", batteryLevel);

    }

    @Override
    public String takeDecision() {
        logger.info("BATTERY LEVEL AT: {}", battery.getRemainingPower());
        Decision decision = commandCenter.makeDecision();
        logger.info("** Decision: {}",decision.toString());
        logger.info("current position: {}, {}", drone.position.getX(), drone.position.getY());
        return decision.toString();
    }

    @Override
    public void acknowledgeResults(String s) {
        JSONObject response = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Response received:\n" + response.toString(2));
        Integer cost = response.getInt("cost");
        logger.info("The cost of the action was {}", cost);
        String status = response.getString("status");
        logger.info("The status of the drone is {}", status);
        JSONObject extraInfo = response.getJSONObject("extras");
        logger.info("Additional information received: {}", extraInfo);

        battery.consume(cost);
        commandCenter.processResponse(response.toString());
    }

    @Override
    public String deliverFinalReport() {
        logger.info("**** FINAL REPORT ****");
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
