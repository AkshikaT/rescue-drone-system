package ca.mcmaster.se2aa4.island.team18;

public enum Direction {
    N, E, S, W;

    public String getRightDirection() {
        return values()[(this.ordinal() + 1) % 4].name();
    }

    public String getLeftDirection() {
        return values()[(this.ordinal() + 3) % 4].name(); 
    }
}
