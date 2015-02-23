package org.googlemap;

import com.google.common.collect.Maps;
import org.json.JSONObject;

import java.util.Map;

public class Geodecoder extends  AbstractUrlParamsEncoding{
    /**
     * Get address
     * @param coordinates - example: "53.454635,30.48443"
     * @return address
     * @throws Exception
     */
    public String geodecode(String coordinates) throws Exception {
        String baseUrl = "http://maps.googleapis.com/maps/api/geocode/json";
        Map<String, String> params = Maps.newHashMap();
        params.put("language", "ru");
        params.put("sensor", "false");
        params.put("latlng", coordinates);
        String url = baseUrl + '?' + encodeParams(params);
        JSONObject response = JSONReader.read(url);
        JSONObject location = response.getJSONArray("results").getJSONObject(0);
        return location.getString("formatted_address");
    }

    /**
     * Get address
     * @param lat - latitude
     * @param lng - longitude
     * @return address
     * @throws Exception
     */
    public String geodecode(double lat, double lng) throws Exception {
        String coordinates = lat + "," + lng;
        return geodecode(coordinates);
    }
}
