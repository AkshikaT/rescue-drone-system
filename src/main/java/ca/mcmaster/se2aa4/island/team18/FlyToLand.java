package ca.mcmaster.se2aa4.island.team18;

import java.util.ArrayList;

import org.json.JSONObject;

public class FlyToLand implements DroneState{
    
    private final Drone drone;
    private Echo echo;
    private boolean onLand = false;
    private boolean echoDone = false;
    protected boolean patrol;
    private Creeks creek;
    private Sites site;
    private ArrayList<String> creekId;

    public FlyToLand(Drone drone, Echo echo, Creeks creek, Sites site) {
        this.drone = drone;
        this.echo = echo;
        this.creek = creek;
        this.site = site;
        this.creekId = creek.getCreeks();
    }

    @Override
    public Decision makeDecision() {
        if (!echoDone) {
            echoDone = true;
            return echo.takeDecision("F");
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
    public DroneState getNextState() {
        if (onLand) {
            return new GridSearch(drone, creek, site);
        }

        // if (onLand && !patrol) {
        //     return new PatrolLand(drone, echo);
        // }

        return this; 
    }
}