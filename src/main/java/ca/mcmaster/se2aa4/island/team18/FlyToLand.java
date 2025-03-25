package ca.mcmaster.se2aa4.island.team18;


import org.json.JSONObject;

/**
 * Manages the drone's movement towards land.
 */
public class FlyToLand implements DroneState{
    
    private final Drone drone;
    private boolean onLand = false;
    private boolean echoDone = false;
    protected boolean patrol;

    public FlyToLand(Drone drone) {
        this.drone = drone;
    }

    @Override
    public Decision makeDecision() {
        // Perform an echo before moving forward.
        if (!echoDone) {
            echoDone = true;
            return drone.echo("F");
        }

        // Move forward after performing echo.
        echoDone = false;
        return drone.flyForward();
    }

    @Override
    public void handleResponse(String response) {
        JSONObject responseJson = new JSONObject(response);
        JSONObject extras = responseJson.optJSONObject("extras");
        
        // Process echo response to check if the drone has reached land.
        if (extras != null && extras.has("found")) {
            String found = extras.getString("found");
            if ("GROUND".equals(found) && extras.getInt("range") == 0) {
                onLand = true;
            }
        }
    }


    @Override
    public boolean stateCompleted() {
        return onLand;
    }
}