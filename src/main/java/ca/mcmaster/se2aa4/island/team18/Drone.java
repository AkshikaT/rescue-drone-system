package ca.mcmaster.se2aa4.island.team18;

import org.json.JSONObject;

public class Drone {
    private Direction direction;

    public Drone(Direction direction) {
        this.direction = direction;
    }

    public Decision turnRight() {
        direction = Direction.valueOf(direction.getRightDirection());

        JSONObject command = new JSONObject();
        command.put("action", "heading");

        JSONObject parameters = new JSONObject();
        parameters.put("direction", direction.name());

        command.put("parameters", parameters);

        return new Decision(command.toString());
    }

    public Decision turnLeft() {
        direction = Direction.valueOf(direction.getLeftDirection());

        JSONObject command = new JSONObject();
        command.put("action", "heading");

        JSONObject parameters = new JSONObject();
        parameters.put("direction", direction.name());

        command.put("parameters", parameters);

        return new Decision(command.toString());
    }

    public Decision uTurn() {
        turnRight();
        turnRight();

        JSONObject command = new JSONObject();
        command.put("action", "heading");

        JSONObject parameters = new JSONObject();
        parameters.put("direction", direction.name());

        command.put("parameters", parameters);

        return new Decision(command.toString());
    }

    public Decision flyForward() {
        JSONObject command = new JSONObject();
        command.put("action", "fly");

        return new Decision(command.toString());
    }

    public Decision echoDecision(String relativeDirection) {
        Echo echo = new Echo(this);
        return new Decision(echo.takeDecision(relativeDirection));
    }

    public Direction getDirection() {
        return direction;
    }
}
