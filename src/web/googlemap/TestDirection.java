package web.googlemap;

import org.json.JSONException;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Created by Azunai on 18.02.2015.
 */
public class TestDirection {
    @org.junit.Test
    public void testGetDirectionLength(){
        String origin = "59.923710640259905,30.3057861328125";
        String destination = "55.923710640259905,30.3057861328125";
        Geodecoder geodecoder = new Geodecoder();
        Direction direction = new Direction();
        try {
            double result = direction.getDirectionLength(geodecoder.geodecode(origin),
                    geodecoder.geodecode(destination), "duration");
            double expVal = 24197.0;
            assertEquals(expVal,result,0.0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
