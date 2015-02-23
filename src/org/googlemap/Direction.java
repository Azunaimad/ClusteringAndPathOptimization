package org.googlemap;


import com.google.appengine.labs.repackaged.com.google.common.collect.Maps;
import org.json.JSONObject;

import java.util.Map;

/**
 * Helps to get direction from on point to another
 */
public class Direction extends AbstractUrlParamsEncoding {
    /**
     * Get distance ot duration from origin to destination
     * @param origin - origin
     * @param destination - origin
     * @param type - equals "duration" or "distance"
     * @return distance (in meters) or duration (in seconds)
     * @throws Exception
     */
    public double getDirectionLength(String origin, String destination, String type)
            throws Exception {
        String baseUrl = "http://maps.googleapis.com/maps/api/directions/json";
        Map<String, String> params = Maps.newHashMap();
        params.put("sensor", "false");
        params.put("language", "ru");
        params.put("mode", "driving");
        params.put("origin", origin);
        params.put("destination", destination);
        String url = baseUrl + '?' + encodeParams(params);
        JSONObject response = JSONReader.read(url);
        JSONObject location = response.getJSONArray("routes").getJSONObject(0);
        location = location.getJSONArray("legs").getJSONObject(0);
        String lengthStr = "";
        if(type.equals("duration"))
            lengthStr = location.getJSONObject("duration").getString("value");
        else if(type.equals("distance"))
            lengthStr = location.getJSONObject("distance").getString("text");

        return Double.parseDouble(lengthStr);
    }
}
