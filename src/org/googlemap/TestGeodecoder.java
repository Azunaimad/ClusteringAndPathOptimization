package org.googlemap;

import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class TestGeodecoder {
    @Test
    public void testGeodecode(){
        String coordinatesStr = "59.923710640259905,30.3057861328125";
        Geodecoder geodecoder = new Geodecoder();
        try {
            String exVal = "Большая Подьяческая улица, 20, Санкт-Петербург, Россия, 190068";
            assertEquals(exVal, geodecoder.geodecode(coordinatesStr));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
