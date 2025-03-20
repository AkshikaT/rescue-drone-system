package ca.mcmaster.se2aa4.island.team18;

import org.json.JSONObject;

public class Drone {
    private Direction direction;
    private Integer batteryLevel;

    public Drone(Direction direction, Integer batteryLevel) {
        this.direction = direction;
        this.batteryLevel = batteryLevel;
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

    public Decision stop() {
        JSONObject command = new JSONObject();
        command.put("action", "stop");

        return new Decision(command.toString());
    }

    public Direction getDirection() {
        return direction;
    }

    public boolean consumeBattery(int power) { //use the true/false markers to tell the drone to return to start if batteryLevel <= 0
        if (batteryLevel > 0) {
            batteryLevel -= power;
        }

        return batteryLevel > 0; //true = there is battery left, false = no battery left
    }

    public int getBatteryLevel() {
        return batteryLevel;
    }

}
