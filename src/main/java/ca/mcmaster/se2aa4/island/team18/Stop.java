package ca.mcmaster.se2aa4.island.team18;

public class Stop implements DroneState{
    private Drone drone;

    public Stop(Drone drone) {
        this.drone = drone;
    }

    @Override
    public Decision makeDecision() {
        return drone.stop();
    }

    @Override
    public void handleResponse(String response) {

    }

    @Override
    public DroneState getNextState() {
        return this;
    }


    
}
