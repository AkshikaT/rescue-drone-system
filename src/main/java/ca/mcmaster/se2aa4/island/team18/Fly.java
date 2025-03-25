package ca.mcmaster.se2aa4.island.team18;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class Fly implements DroneCommand{
    
    private Position position;
    private final Logger logger = LogManager.getLogger();

    public Fly(Position position) {
        this.position = position;
    }

    @Override
    public Decision execute() {
        position.update();

        JSONObject command = new JSONObject();
        command.put("action", "fly");
        return new Decision(command.toString());
    }
}
