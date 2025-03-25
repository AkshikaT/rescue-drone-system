package ca.mcmaster.se2aa4.island.team18;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class MapDimensions implements DroneState{

    private final Logger logger = LogManager.getLogger();

    private final Drone drone;
    private int count = 0;    
    private boolean mappingComplete = false;
    private final String[] echoOptions = {"F", "R"};
    private int echoIndex = -1;
    
    public MapDimensions(Drone drone) {
        this.drone = drone;
    }

    @Override
    public Decision makeDecision() {
        count ++;
        if(count == 2) {
            mappingComplete = true;
        }
        echoIndex = (echoIndex + 1) % echoOptions.length;
        return drone.echo(echoOptions[echoIndex]);
        
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
    public boolean stateCompleted() {
        if (mappingComplete) {
            logger.info("dimensions of the map are: {} x {}", drone.mapHorRange, drone.mapVerRange);
            return true; 
        }
        else return false;
    }
}
