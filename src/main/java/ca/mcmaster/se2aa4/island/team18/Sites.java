package ca.mcmaster.se2aa4.island.team18;

public class Sites {
    private String siteId;
    private int siteX;
    private int siteY;

    public String getSite() {
        return siteId;
    }

    public void foundSite(String id) {
        siteId = id;
    }

    public void addCoord(int x, int y) {
        siteX = x;
        siteY = y;
    }

    public int getXCoord() {
        return siteX;
    }

    public int getYCoord() {
        return siteY;
    }


}