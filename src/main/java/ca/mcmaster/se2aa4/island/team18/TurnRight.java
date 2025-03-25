package ca.mcmaster.se2aa4.island.team18;

import org.json.JSONObject;

public class TurnRight implements DroneCommand{
    private Drone drone;
    private Position position;
    private Direction direction;

    public TurnRight(Drone drone, Position position, Direction direction) {
        this.drone = drone;
        this.position = position;
        this.direction = direction;

    }
    @Override
    public Decision execute() {
        position.update();
        Direction newDirection = Direction.valueOf(direction.getRightDirection());
        drone.setDirection(newDirection); // THIS IS THE CRUCIAL CHANGE
        position.update();
        
        JSONObject command = new JSONObject();
        command.put("action", "heading");
        command.put("parameters", new JSONObject()
            .put("direction", newDirection.name()));
        
        return new Decision(command.toString());
    }

}
