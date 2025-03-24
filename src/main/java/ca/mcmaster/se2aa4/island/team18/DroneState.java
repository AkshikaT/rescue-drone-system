package ca.mcmaster.se2aa4.island.team18;

public interface DroneState {
    Decision makeDecision();
    void handleResponse(String response);
    DroneState getNextState();
}
