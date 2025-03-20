package ca.mcmaster.se2aa4.island.team18;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Description: An abstract class that offers two methods to read the map: scan and echo
public abstract class MapReader{

    private final Logger logger = LogManager.getLogger();

    public MapReader() {
        super();
    }

    public abstract Decision takeDecision(String direction);

}
