package ca.mcmaster.se2aa4.island.team18;

import org.json.JSONObject;

public class Fly implements DroneCommand{
    
    private Position position;

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
