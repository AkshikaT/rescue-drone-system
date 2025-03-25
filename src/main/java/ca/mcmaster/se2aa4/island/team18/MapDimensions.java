package ca.mcmaster.se2aa4.island.team18;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

/**
 * Determines the horizontal and vertical dimensions of the map using echo commands.
 */
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

    /**
     * Sends an echo command to measure the map's dimensions.
     * Alternates between checking forward ("F") and right ("R") directions.
     *
     * @return A Decision object representing the echo command.
     */
    @Override
    public Decision makeDecision() {
        count ++;
        // Mapping is complete after two measurements (horizontal and vertical)
        if(count == 2) {
            mappingComplete = true;
        }
        echoIndex = (echoIndex + 1) % echoOptions.length;
        return drone.echo(echoOptions[echoIndex]);
        
    }

    /**
     * Processes the response from an echo command and updates the drone's map dimensions.
     *
     * @param response The JSON response from the echo command.
     */
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
