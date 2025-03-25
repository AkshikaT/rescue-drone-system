package ca.mcmaster.se2aa4.island.team18;

import java.util.ArrayList;
import java.util.List;

public class StateManager {
    private final Drone drone;
    private final Battery battery;
    private List<DroneState> stateSequence;
    private int currentStateIndex;
    private final Creeks creek;
    private final Sites site;

    public StateManager(Drone drone, Battery battery, Creeks creek, Sites site) {
        this.drone = drone;
        this.battery = battery;
        this.creek = creek;       
        this.site = site;
        initializeStateSequence();  
        this.currentStateIndex = 0;
    }

    private void initializeStateSequence() {
        stateSequence = new ArrayList<>();
        
        // Initialize all states in execution order
        stateSequence.add(new MapDimensions(drone));
        stateSequence.add(new FindLand(drone));
        stateSequence.add(new FlyToLand(drone));
        stateSequence.add(new GridSearch(drone, creek, site));
        stateSequence.add(new Stop());
    }

    public Decision makeDecision() {
        if (battery.getRemainingPower() <= 0) {
            return new Stop().makeDecision();
        }
        
        // Get current state's decision
        Decision decision = stateSequence.get(currentStateIndex).makeDecision();
        
        return decision;
    }

    public void processResponse(String response) {
        // Let current state handle the response
        stateSequence.get(currentStateIndex).handleResponse(response);
        
        // Check if current state is complete
        if (stateSequence.get(currentStateIndex).stateCompleted()) {
            advanceToNextState();
        }
    }

    private void advanceToNextState() {
        if (currentStateIndex < stateSequence.size() - 1) {
            currentStateIndex++;
        }
    }
    
}
