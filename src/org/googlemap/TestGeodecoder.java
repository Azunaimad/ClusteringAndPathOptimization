package org.googlemap;

import static org.junit.Assert.assertEquals;

/**
 * Created by Azunai on 18.02.2015.
 */
public class TestGeodecoder {
    @org.junit.Test
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
