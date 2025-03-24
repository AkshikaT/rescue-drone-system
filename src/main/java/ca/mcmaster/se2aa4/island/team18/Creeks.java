package ca.mcmaster.se2aa4.island.team18;

import java.util.ArrayList;

public class Creeks {
    private ArrayList<String> creekIds;
    private ArrayList<Integer> creekX;
    private ArrayList<Integer> creekY;

    public void addCreek(String creek) {
        creekIds.add(creek);
    }

    public ArrayList<String> getCreeks() {
        return creekIds;
    }

    public void addCoord(int x, int y) {
        creekX.add(x);
        creekY.add(y);
    }

    public ArrayList<Integer> getXCoords() {
        return creekX;
    }

    public ArrayList<Integer> getYCoords() {
        return creekY;
    }

    public int creekXCoord(String id) {
        int xCoord = 0;
        for (int i = 0; i < creekIds.size(); i++) {
            String x = creekIds.get(i);
            if (x == id) {
                xCoord = creekX.get(i);
            }
        }
        return xCoord;
    }

    public int creekYCoord(String id) {
        int yCoord = 0;
        for (int i = 0; i < creekIds.size(); i++) {
            String y = creekIds.get(i);
            if (y == id) {
                yCoord =  creekY.get(i);
            }
        }
        return yCoord;
    }



}