package org.googlemap;

import com.google.appengine.labs.repackaged.com.google.common.collect.Maps;
import org.json.*;

import java.util.Map;

public class DistanceMatrix extends Direction {


    public double[][] getDistanceMatrix(double[][] coordinates, String type) throws Exception {
        double [][] distancematrix = new double[coordinates[0].length][coordinates[1].length];

        String baseUrl = "http://maps.googleapis.com/maps/api/distancematrix/json?";
        Map<String, String> params = Maps.newHashMap();
        params.put("sensor", "false");
        params.put("language", "ru");
        params.put("mode", "driving");

        String coords = "";
        for(int i=0; i<coordinates[0].length; i++){
            coords += coordinates[0][i]+","+coordinates[1][i]+"|";
        }
        coords = coords.substring(0,coords.length()-1);
        params.put("origins", coords);
        params.put("destinations", coords);
        String url = baseUrl + encodeParams(params);
        JSONObject response = JSONReader.read(url);
        org.json.JSONArray rows = response.getJSONArray("rows");

        for(int i=0; i<rows.length(); i++){
            JSONObject row = rows.getJSONObject(i);
            org.json.JSONArray elements = row.getJSONArray("elements");
            for(int j=0; j<elements.length(); j++){
                JSONObject element = elements.getJSONObject(j);
                String duration = element.getJSONObject(type).getString("value");
                distancematrix[i][j] = Double.parseDouble(duration);
            }
        }

        double max = getMaxValue(distancematrix);
        for(int i=0; i<distancematrix.length; i++)
            distancematrix[i][i] = max + 10000.0;

        return distancematrix;
    }

    protected double getMaxValue(double[][] array){
        double maxValue = 0.0;
        for(int i=0; i<array.length; i++)
            for(int j=0; j<array[0].length; j++)
                if(maxValue<= array[i][j]) maxValue = array[i][j];
        return maxValue;
    }
}
