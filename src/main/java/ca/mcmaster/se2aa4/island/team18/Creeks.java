package ca.mcmaster.se2aa4.island.team18;

import java.util.ArrayList;

public class Creeks {
    private ArrayList<String> creekIds;
    private ArrayList<Integer> creekX;
    private ArrayList<Integer> creekY;

    public Creeks() {
        this.creekIds = new ArrayList<>();
        this.creekX = new ArrayList<>();
        this.creekY = new ArrayList<>();
    }

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
        for (int i = 0; i < creekIds.size(); i++) {
            if (creekIds.get(i).equals(id)) {
                return creekX.get(i);
            }
        }
        return -1; // Return a default or invalid value if not found
    }

    public int creekYCoord(String id) {
        for (int i = 0; i < creekIds.size(); i++) {
            if (creekIds.get(i) != null && creekIds.get(i).equals(id)) {
                return creekY.get(i);
            }
        }
        return -1; // Same here — return a value indicating "not found"
    }



}