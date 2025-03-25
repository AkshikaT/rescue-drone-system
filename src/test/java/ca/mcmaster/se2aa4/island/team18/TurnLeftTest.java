package ca.mcmaster.se2aa4.island.team18;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

class TurnLeftTest {

    @Test
    void testUpdatesDirection() {
        Drone drone = new Drone(Direction.N);
        Position position = new Position(drone);
        TurnLeft turnLeftCommand = new TurnLeft(drone, position, drone.getDirection());

        Direction initialDirection = drone.getDirection();

        Decision decision = turnLeftCommand.execute();

        assertNotEquals(initialDirection, drone.getDirection());
        assertEquals(Direction.W, drone.getDirection());
    }

    @Test
    void testReturnsCorrectDecision() {
        Drone drone = new Drone(Direction.N);
        Position position = new Position(drone);
        TurnLeft turnLeftCommand = new TurnLeft(drone, position, drone.getDirection());

        Decision decision = turnLeftCommand.execute();

        JSONObject expectedCommand = new JSONObject();
        expectedCommand.put("action", "heading");
        expectedCommand.put("parameters", new JSONObject().put("direction", "W"));

        assertEquals(expectedCommand.toString(), decision.toString());
    }
}

