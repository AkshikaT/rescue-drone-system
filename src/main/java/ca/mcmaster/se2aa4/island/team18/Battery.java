package ca.mcmaster.se2aa4.island.team18;

public class Battery {
    private Integer powerLevel;

    public Battery (Integer initialPower) {
        this.powerLevel = initialPower;
    }

    public void consume(int amount) { 
        powerLevel -= amount;
    }

    public boolean hasPower() {
        return powerLevel > 0;
    }
    
    public int getRemainingPower() {
        return powerLevel;
    }




}