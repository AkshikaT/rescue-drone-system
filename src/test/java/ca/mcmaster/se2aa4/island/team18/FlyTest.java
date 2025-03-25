package ca.mcmaster.se2aa4.island.team18;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

class FlyTest {
    
    @Test
    void testExecuteUpdatesPosition() {
        Position position = new Position(); // Assuming a default constructor exists
        Fly flyCommand = new Fly(position);
        
        flyCommand.execute();
        
        // Assuming Position has a method to check if it was updated
        assertTrue(position.wasUpdated()); // Replace with actual verification method
    }
    
    @Test
    void testExecuteReturnsCorrectDecision() {
        Position position = new Position();
        Fly flyCommand = new Fly(position);
        
        Decision decision = flyCommand.execute();
        
        JSONObject expectedCommand = new JSONObject();
        expectedCommand.put("action", "fly");
        
        assertEquals(expectedCommand.toString(), decision.getCommand());
    }
}