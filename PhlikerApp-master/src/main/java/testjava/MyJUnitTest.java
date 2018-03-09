package testjava;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import phliker.com.service.FlickrService;
import phliker.com.service.FlickrService;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class MyJUnitTest {
    @Test
    public void testGetJsonObject() throws IOException, ParseException {
        FlickrService flickrService = new FlickrService();
        String photoSourceUrl = "https://farm{farm-id}.staticflickr.com/{server-id}/{id}_{secret}.jpg";
        photoSourceUrl =photoSourceUrl.replace("{farm-id}", "5");
        photoSourceUrl = photoSourceUrl.replace("{server-id}", "4706");
        photoSourceUrl = photoSourceUrl.replace("{id}","40392223022");
        photoSourceUrl = photoSourceUrl.replace("{secret}","cf28bbf03f");
        System.out.println(photoSourceUrl);
        JSONObject obj = flickrService.getJsonObject(photoSourceUrl);
        assertNotNull(obj);

    }
}