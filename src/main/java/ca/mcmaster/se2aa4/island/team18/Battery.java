package ca.mcmaster.se2aa4.island.team18;

public class Battery {
    private Integer batteryLevel;

    public Battery (Integer batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public boolean consume(int power) { //use the true/false markers to tell the drone to return to start if batteryLevel <= 0
        if (batteryLevel > 0) {
            batteryLevel -= power;
        }

        return batteryLevel > 0; //true = there is bettery left, false = no battery left
    }

    public int getBatteryLevel() {
        return batteryLevel;
    }




}