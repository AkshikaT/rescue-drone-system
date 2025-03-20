package ca.mcmaster.se2aa4.island.team18;

import org.json.JSONObject;

public class FindLand {
    private final Drone drone;
    private final Echo echo;
    private boolean echoDone = false;
    private boolean groundFound = false;
    private final String[] echoOptions = {"F", "L", "R"};
    private int echoIndex = -1;

    public FindLand(Drone drone) {
        this.drone = drone;
        this.echo = new Echo(drone);
    }

    public Decision nextStep() {
        // Echo F, L, and R
        if (!echoDone) {
            echoIndex = (echoIndex + 1) % echoOptions.length;
            if (echoIndex == 2) {
                echoDone = true;
            }
            return echo.takeDecision(echoOptions[echoIndex]);
        }

        // If ground is found, stop
        if (groundFound) {
            echoIndex = -1;
            return drone.stop();
        }

        // Fly forward is no ground is found
        echoDone = false;
        return drone.flyForward();
    }

    public void handleResponse(String response) {
        JSONObject responseJson = new JSONObject(response);
        JSONObject extras = responseJson.optJSONObject("extras");
        
        if (extras != null && extras.has("found")) {
            String found = extras.getString("found");
            if ("GROUND".equals(found)) {
                groundFound = true;
            }
        }
    }
}