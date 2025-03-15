package ca.mcmaster.se2aa4.island.team18;

import java.io.StringReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.json.JSONObject;
import org.json.JSONTokener;

// Description: An abstract class that offers two methods to read the map: scan and echo
public abstract class MapReader{

    private final Logger logger = LogManager.getLogger();

    public MapReader() {
        super();
    }

    public abstract String takeDecision(String direction);

}
