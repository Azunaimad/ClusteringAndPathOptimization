package web.googlemap;


import com.google.appengine.labs.repackaged.com.google.common.collect.Maps;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

public class Direction extends AbstractUrlParamsEncoding {
    public double getDirectionLength(String origin, String destination, String lengthType) throws IOException, JSONException {
        String baseUrl = "http://maps.googleapis.com/maps/api/directions/json";
        Map<String, String> params = Maps.newHashMap();
        params.put("sensor", "false");
        params.put("language", "ru");
        params.put("mode", "driving");
        params.put("origin", origin);
        params.put("destination", destination);
        String url = baseUrl + '?' + encodeParams(params);
        JSONObject response = JsonReader.read(url);
        JSONObject location = response.getJSONArray("routes").getJSONObject(0);
        location = location.getJSONArray("legs").getJSONObject(0);
        String lengthStr = "";
        if(lengthType.equals("duration"))
            lengthStr = location.getJSONObject("duration").getString("value");
        else if(lengthType.equals("distance"))
            lengthStr = location.getJSONObject("distance").getString("text");

        return Double.parseDouble(lengthStr);
    }
}
