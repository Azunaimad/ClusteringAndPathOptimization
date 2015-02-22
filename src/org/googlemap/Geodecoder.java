package org.googlemap;

import com.google.common.collect.Maps;
import org.json.JSONObject;

import java.util.Map;

public class Geodecoder extends  AbstractUrlParamsEncoding{
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
}
