package ca.mcmaster.se2aa4.island.team18;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class CoastFinder {
    private static final Logger logger = LogManager.getLogger(CoastFinder.class);

    public static List<Integer> getCoastLineKeys(String s) {
        List<Integer> coastKeys = new ArrayList<>();
        try {
            // Parse the input string into a JSONArray
            JSONArray vertices = new JSONArray(new JSONTokener(new StringReader(s)));

            // Display coastline vertices
            logger.info("** Coastline Vertices:");
            for (int i = 0; i < vertices.length(); i++) {
                JSONObject vertex = vertices.getJSONObject(i);
                int key = vertex.getInt("key");
                JSONArray vals = vertex.getJSONArray("vals");

                // Check the properties of the vertex
                for (int j = 0; j < vals.length(); j++) {
                    JSONObject property = vals.getJSONObject(j);

                    // Log the vertex key if it is a coastline
                    if (property.getString("p").equals("isCoast") && property.getBoolean("v")) {
                        coastKeys.add(key);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error processing the file: ", e);
        }

        return coastKeys;
    }
}
