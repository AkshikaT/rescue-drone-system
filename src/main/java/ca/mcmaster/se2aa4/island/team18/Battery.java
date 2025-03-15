package ca.mcmaster.se2aa4.island.team18;

import java.io.StringReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.ace_design.island.bot.IExplorerRaid;

import org.json.JSONObject;
import org.json.JSONTokener;

import jdk.jfr.Timespan;

public class Battery {
    private Integer capacity;
    private Integer batteryLevel;

    public Battery (JSONObject json) {
        this.capacity = json.getInt("budget");
        this.batteryLevel = capacity;
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