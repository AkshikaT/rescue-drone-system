package ca.mcmaster.se2aa4.island.team18;

import org.json.JSONObject;

public class TurnLeft implements DroneCommand{
    private Drone drone;
    private Position position;
    private Direction direction;

    public TurnLeft(Drone drone, Position position, Direction direction) {
        this.drone = drone;
        this.position = position;
        this.direction = direction;

    }

    @Override
    public Decision execute() {
        position.update();
        Direction newDirection = Direction.valueOf(direction.getLeftDirection());
        drone.setDirection(newDirection); 
        position.update();
        
        JSONObject command = new JSONObject();
        command.put("action", "heading");
        command.put("parameters", new JSONObject()
            .put("direction", newDirection.name()));
        
        return new Decision(command.toString());
    }

}
