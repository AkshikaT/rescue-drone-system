package ca.mcmaster.se2aa4.island.team18;

public class Drone {
    protected Direction direction;
    protected Position position;
    private TurnLeft turnLeft;
    private TurnRight turnRight;
    private Fly fly;
    private Echo echo;
    private final Scan scan;

    protected int x = 0;              
    protected int y = 0;

    protected int mapHorRange;
    protected int mapVerRange;
    protected int [] topGroundCoor = new int [2];
    protected int endX;
    protected int groundX;
    protected int groundY;

    public Drone(Direction direction) {
        this.direction = direction;
        this.position = new Position(this);
        this.scan = new Scan();
    }

    public Decision turnRight() {
        this.turnRight = new TurnRight(this, position, direction);
        return turnRight.execute();
    }

    public Decision turnLeft() {
        this.turnLeft = new TurnLeft(this, position, direction);
        return turnLeft.execute();
    }

    public Decision echo(String relativeDirection) {
        echo = new Echo(this, relativeDirection);
        return echo.execute();
    }

    public Decision scan() {
        return scan.execute();
    }

    public Decision flyForward() {
        fly = new Fly(position);
        return fly.execute();
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Position getPosition() {
        return position;
    }


}
