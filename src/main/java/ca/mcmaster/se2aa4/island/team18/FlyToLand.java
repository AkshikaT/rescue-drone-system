package ca.mcmaster.se2aa4.island.team18;


import org.json.JSONObject;

public class FlyToLand implements DroneState{
    
    private final Drone drone;
    private Echo echo;
    private boolean onLand = false;
    private boolean echoDone = false;
    protected boolean patrol;

    public FlyToLand(Drone drone) {
        this.drone = drone;
    }

    @Override
    public Decision makeDecision() {
        if (!echoDone) {
            echoDone = true;
            return drone.echo("F");
        }

        echoDone = false;
        return drone.flyForward();
    }

    @Override
    public void handleResponse(String response) {
        JSONObject responseJson = new JSONObject(response);
        JSONObject extras = responseJson.optJSONObject("extras");
        
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