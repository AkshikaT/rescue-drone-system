package ca.mcmaster.se2aa4.island.team18;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class BatteryTest {

    @Test
    void testInitialLevel() {
        assertEquals(75, new Battery(75).getBatteryLevel());
    }

    @Test
    void testConsumption() {
        Battery b = new Battery(60);
        assertTrue(b.consume(15));
        assertEquals(45, b.getBatteryLevel());
    }

    @Test
    void testDepletion() {
        Battery b = new Battery(20);
        assertTrue(b.consume(20));
        assertEquals(0, b.getBatteryLevel());
    }

    @Test
    void testDrainBelowZero() {
        Battery b = new Battery(10);
        assertFalse(b.consume(25));
        assertTrue(b.getBatteryLevel() <= 0);
    }
    
    @Test
    void testNoNegative() {
        Battery b = new Battery(0);
        assertFalse(b.consume(8));
        assertEquals(0, b.getBatteryLevel());
    }

}
