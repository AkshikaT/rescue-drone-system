package ca.mcmaster.se2aa4.island.team18;

public class Position {
    private int x = 0;
    private int y = 0;
    private final Drone drone;
    public Position (Drone drone) {
        this.drone = drone;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public void update() {
        if (drone.direction.toString().equals("N")) {
            y -= 1;
        }
        else if (drone.direction.toString().equals("S")) {
            y += 1;
        }
        else if (drone.direction.toString().equals("E")) {
            x += 1;
        }
        else {
            x -= 1;
        }

    }
}
