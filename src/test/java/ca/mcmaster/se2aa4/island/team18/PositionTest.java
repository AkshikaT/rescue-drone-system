package ca.mcmaster.se2aa4.island.team18;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class PositionTest {

    private final Logger logger = LogManager.getLogger();

    @Test
    public void testTravelNorth() {
        Drone drone = new Drone(Direction.N);
        Position position = new Position(drone);

        position.update();

        assertEquals(0, position.getX());
        assertEquals(-1, position.getY());

        logger.info("Drone is going north.");
    }
}

