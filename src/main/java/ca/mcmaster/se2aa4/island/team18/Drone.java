package ca.mcmaster.se2aa4.island.team18;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class Drone {
    private final Logger logger = LogManager.getLogger();

    private Direction direction;
    private Integer batteryLevel;

    private int x = 0;           // relative coordinates of the drone       
    private int y = 0;

    protected int mapHorRange;
    protected int mapVerRange;

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
        this.updateDroneCoors();
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

    public String getDirectionChar() {
        return String.valueOf(direction.name().charAt(0));
    }

    public void updateDroneCoors() {                // only called when drone makes movement
        switch (getDirectionChar()){
            case ("N"):
                this.y += 1;
                break;
            case ("S"):
                this.y -= 1;
                break;
            case ("E"):
                this.x += 1;
                break;
            case ("W"):
                this.x -= 1;
                break;
            default:
                logger.info("coordinates not able to update ");
                break;
        }
        logger.info("New direction: {}    Coordinates of the drone: x = {}, y = {}", getDirectionChar(), x, y);
    }

}
