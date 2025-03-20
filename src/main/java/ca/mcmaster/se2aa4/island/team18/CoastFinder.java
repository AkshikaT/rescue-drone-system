package ca.mcmaster.se2aa4.island.team18;

import java.io.StringReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.util.List;
import java.util.ArrayList;

public class CoastFinder {
    private static final Logger logger = LogManager.getLogger();

    public static List<Integer> getCoastLineKeys(String s) {
        try {
            //parse the input string into a JSONArray
            JSONArray vertices = new JSONArray(new JSONTokener(new StringReader(s)));
            List<Integer> coastKeys = new ArrayList<>();

            //display coastline vertices
            logger.info("** Coastline Vertices:");
            for (int i = 0; i < vertices.length(); i++) {
                JSONObject vertex = vertices.getJSONObject(i);
                int key = vertex.getInt("key");
                JSONArray vals = vertex.getJSONArray("vals");

                //check the properties of the vertex
               for (int j = 0; j < vals.length(); j++) {
                    JSONObject property = vals.getJSONObject(j);

                    //log the vertex key
                    if (property.getString("p").equals("isCoast") && property.getBoolean("v")) {
                        coastKeys.add(key);
                        break;
                    }
                }

                return coastKeys;
            }
        } catch (Exception e) {
            logger.error("Error processing the file: ", e);
        }
    }
}

