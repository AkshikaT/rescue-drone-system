package ca.mcmaster.se2aa4.island.team18;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class BatteryTest {

    @Test
    void testInitialLevel() {
        assertEquals(75, new Battery(75).getPowerLevel());
    }

    @Test
    void testConsumption() {
        Battery b = new Battery(60);
        b.consume(15);
        assertEquals(45, b.getPowerLevel());
    }

    @Test
    void testDepletion() {
        Battery b = new Battery(20);
        b.consume(20);
        assertEquals(0, b.getPowerLevel());
    }

    @Test
    void testBelowZero() {
        Battery b = new Battery(10);
        b.consume(25);
        assertEquals(0, b.getPowerLevel());
    }
    

}
