package ca.mcmaster.se2aa4.island.team18;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class MapDimensions implements DroneState{

    private final Logger logger = LogManager.getLogger();

    private final Drone drone;
    private final Echo echo;
    private int count = 0;    
    private boolean mappingComplete = false;
    private final String[] echoOptions = {"F", "R"};
    private int echoIndex = -1;
    private Creeks creek;
    private Sites site;
    private ArrayList<String> creekId;
    

    public MapDimensions(Drone drone, Creeks creek, Sites site) {
        this.drone = drone;
        this.echo = new Echo(drone);
        this.creek = creek;
        this.site = site;
        this.creekId = creek.getCreeks();
    }

    @Override
    public Decision makeDecision() {
        count ++;
        if(count == 2) {
            mappingComplete = true;
        }
        echoIndex = (echoIndex + 1) % echoOptions.length;
        return echo.takeDecision(echoOptions[echoIndex]);
        
    }
    @Override
    public void handleResponse(String response) {
        JSONObject responseJson = new JSONObject(response);
        JSONObject extras = responseJson.optJSONObject("extras");
        if (extras != null && extras.has("range")) {
            if (count == 1) {          // horiztonal range       
                drone.mapHorRange = extras.getInt("range");
            }
            else if (count == 2) {
                drone.mapVerRange = extras.getInt("range");
            }
        }
    }

    @Override
    public DroneState getNextState() {
        logger.info("evaluating next state");
        if (mappingComplete) {
            logger.info("dimensions of the map are: {} x {}", drone.mapHorRange, drone.mapVerRange);
            return new FindLand(drone, creek, site); 
        }
        return this; 
    }
}
