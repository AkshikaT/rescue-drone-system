package ca.mcmaster.se2aa4.island.team18;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class Drone {
    private final Logger logger = LogManager.getLogger();

    protected Direction direction;
    protected Position position;
    private Integer batteryLevel;

    protected int x = 0;           // relative coordinates of the drone       
    protected int y = 0;

    protected int mapHorRange;
    protected int mapVerRange;
    protected int [] topGroundCoor = new int [2];
    protected int endX;
    protected int groundX;
    protected int groundY;

    public Drone(Direction direction, Integer batteryLevel) {
        this.direction = direction;
        this.batteryLevel = batteryLevel;
        this.position = new Position(this);
    }

    public Decision turnRight() {
        position.updatePosition();
        direction = Direction.valueOf(direction.getRightDirection());
        position.updatePosition();
        JSONObject command = new JSONObject();
        command.put("action", "heading");

        JSONObject parameters = new JSONObject();
        parameters.put("direction", direction.name());

        command.put("parameters", parameters);
        return new Decision(command.toString());
    }

    public Decision turnLeft() {
        position.updatePosition();
        direction = Direction.valueOf(direction.getLeftDirection());
        position.updatePosition();
        JSONObject command = new JSONObject();
        command.put("action", "heading");

        JSONObject parameters = new JSONObject();
        parameters.put("direction", direction.name());

        command.put("parameters", parameters);
        return new Decision(command.toString());
    }

    public Decision flyForward() {
        position.updatePosition();

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
