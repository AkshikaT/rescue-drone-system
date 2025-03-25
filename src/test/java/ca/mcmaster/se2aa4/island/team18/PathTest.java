package ca.mcmaster.se2aa4.island.team18;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class PathTest {

    @Test
    public void testFindShortestPath() {
        ArrayList<String> creeksList = new ArrayList<>();
        creeksList.add("Creek1");
        creeksList.add("Creek2");
        creeksList.add("Creek3");

        ArrayList<Integer> creekX = new ArrayList<>();
        creekX.add(10);
        creekX.add(15);
        creekX.add(20);

        ArrayList<Integer> creekY = new ArrayList<>();
        creekY.add(12);
        creekY.add(18);
        creekY.add(25);

        int siteX = 14;
        int siteY = 17;

        Path path = new Path();
        String expected = "Creek2";

        String closest = path.findShortestPath(creeksList, creekX, creekY, siteX, siteY);
        assertEquals(expected, closest);
    }
}
