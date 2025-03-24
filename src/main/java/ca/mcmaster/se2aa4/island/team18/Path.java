package ca.mcmaster.se2aa4.island.team18;

import java.util.ArrayList;

public class Path {

    public String findShortestPath(ArrayList<String> creeks, ArrayList<Integer> creekX, ArrayList<Integer> creekY, int siteX, int siteY) {
        double minDistance = 1000000;
        String closest = "";

        for (int i = 0; i < creeks.size(); i++){
            int x = creekX.get(i);
            int y = creekY.get(i);
            double distance = Math.sqrt(Math.pow((siteX-x), 2) + Math.pow((siteY-y), 2));
            if (distance < minDistance) {
                minDistance = distance;
                closest = creeks.get(i);
            }
        }
        return closest;
    }

}