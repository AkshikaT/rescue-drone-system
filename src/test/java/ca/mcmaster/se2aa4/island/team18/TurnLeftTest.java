package ca.mcmaster.se2aa4.island.team18;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TurnLeftTest {
    @Test
    void testExecute() {
        Direction direction = Direction.N;
        Drone drone = new Drone(direction, 100);
        Position position = new Position(drone);

        TurnLeft turnLeft = new TurnLeft(drone, position, direction);
        Decision decision = turnLeft.execute();

        assertEquals(Direction.W, drone.getDirection());

        JSONObject expected = new JSONObject();
        expected.put("action", "heading");
        expected.put("parameters", new JSONObject().put("direction", "W"));

        assertEquals(expected.toString(), decision.getCommand());
    }
}

