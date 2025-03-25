package ca.mcmaster.se2aa4.island.team18;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

class TurnRightTest {

    @Test
    void testUpdatesDirection() {
        Drone drone = new Drone(Direction.N);
        Position position = new Position(drone);
        TurnRight turnRightCommand = new TurnRight(drone, position, drone.getDirection());

        Direction initialDirection = drone.getDirection();

        Decision decision = turnRightCommand.execute();

        assertNotEquals(initialDirection, drone.getDirection());
        assertEquals(Direction.E, drone.getDirection());
    }

    @Test
    void testReturnsCorrectDecision() {
        Drone drone = new Drone(Direction.N);
        Position position = new Position(drone);
        TurnRight turnRightCommand = new TurnRight(drone, position, drone.getDirection());

        Decision decision = turnRightCommand.execute();

        JSONObject expectedCommand = new JSONObject();
        expectedCommand.put("action", "heading");
        expectedCommand.put("parameters", new JSONObject().put("direction", "E"));

        assertEquals(expectedCommand.toString(), decision.toString());
    }
}

