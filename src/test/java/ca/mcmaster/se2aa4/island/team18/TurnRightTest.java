package ca.mcmaster.se2aa4.island.team18;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TurnRightTest {
    @Test
    void testExecute() {
        Direction direction = Direction.N;
        Drone drone = new Drone(direction, 100);
        Position position = new Position(drone);

        TurnRight turnRight = new TurnRight(drone, position, direction);
        Decision decision = turnRight.execute();

        assertEquals(Direction.E, drone.getDirection());

        JSONObject expected = new JSONObject();
        expected.put("action", "heading");
        expected.put("parameters", new JSONObject().put("direction", "E"));

        assertEquals(expected.toString(), decision.getCommand());
    }
}
