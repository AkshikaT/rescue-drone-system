package ca.mcmaster.se2aa4.island.team18;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

class FlyTest {

    @Test
    void testUpdatesPosition() {
        Drone drone = new Drone(Direction.N);
        Position position = new Position(drone);
        Fly flyCommand = new Fly(position);

        int initialX = position.getX();
        int initialY = position.getY();

        flyCommand.execute();

        assertEquals(initialX, position.getX());
        assertEquals(initialY - 1, position.getY());
    }

    @Test
    void testExecuteReturnsCorrectDecision() {
        Position position = new Position(new Drone(Direction.N));
        Fly flyCommand = new Fly(position);

        Decision decision = flyCommand.execute();

        JSONObject expectedCommand = new JSONObject();
        expectedCommand.put("action", "fly");

        assertEquals(expectedCommand.toString(), decision.toString());
    }
}
