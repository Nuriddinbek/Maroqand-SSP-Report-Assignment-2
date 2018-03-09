package phliker.com.service;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import phliker.com.model.FlickrResponse;
import phliker.com.utils.AppProperties;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by nsarvar on 2/15/18.
 */
public class FlickrService implements Service {
    /**
        all API data is stored as hard-coded with variables.
     */
    private static final String SEARCH = "flickr.photos.search";
    private static final String GET_INFO = "flickr.photos.getInfo";
    private final static String flickrApiUrl = "https://api.flickr.com/services/rest";
    private static String flickrApiKey;
    private boolean debugging;

    /**
     * FlickrService class constructor for initialization of flickrApiKey
     */
    public FlickrService() {
        flickrApiKey = AppProperties.getProperty("api_key");
    }

    /**
     * Search for photo based on tags and get JSON object data using url
     * @param tags input keyword of photo
     * @return FlickrResponse instance
     */
    @Override
    public FlickrResponse searchPhoto(String tags){
        FlickrResponse response = new FlickrResponse();
        String url = flickrApiUrl + "/?&method="+SEARCH + "&api_key="+flickrApiKey+"&tags="+tags+"&format=json&per_page=20&nojsoncallback=1";
        JSONObject obj = getJsonObject(url);
        System.out.println(tags);
        //System.out.println(url);
        System.out.println("service: " + obj);
        try{
            if(obj.get("stat").equals("fail")){
                String messages = (String)obj.get("message");
                System.out.println("Here: " + messages);
                if(messages==null){
                    Alert alertNo = new Alert(Alert.AlertType.ERROR);
                    alertNo.setTitle("Error");
                    alertNo.setContentText("Error!");
                    alertNo.showAndWait();
                }
                Alert alertYes = new Alert(Alert.AlertType.ERROR);
                alertYes.setTitle("Error");
                alertYes.setContentText(messages);
                alertYes.showAndWait();
                return null;
            }
        }
        catch (Exception ex){
            System.out.println(ex.toString());
        }
        response.setObject(obj);
        return response;
    }

    /**
     * Get image from API using url
     * @param photo the requested photo
     * @return requested photo image
     */
    @Override
    public Image getImage(phliker.com.model.Photo photo) {
        String photoSourceUrl = "https://farm{farm-id}.staticflickr.com/{server-id}/{id}_{secret}.jpg";
        photoSourceUrl =photoSourceUrl.replace("{farm-id}", photo.getFarm());
        photoSourceUrl = photoSourceUrl.replace("{server-id}", photo.getServer());
        photoSourceUrl = photoSourceUrl.replace("{id}",photo.getId());
        photoSourceUrl = photoSourceUrl.replace("{secret}",photo.getSecret());

        System.out.println(photoSourceUrl + " / " + photo.getId() + " / " + photo.getServer());

        try {
            BufferedImage image = ImageIO.read(new URL(photoSourceUrl));
            Image newimg = SwingFXUtils.toFXImage(image,null);
            return newimg;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * Get JSON object based on its url
     * @param url of API
     * @return JSON object
     */
    public JSONObject getJsonObject(String url){
        JSONParser parser = new JSONParser();
        JSONObject responseJsonObject = null;

        try {
            InputStream is = new URL(url).openStream();
            responseJsonObject = (JSONObject) parser.parse(new InputStreamReader(is, Charset.forName("UTF-8")));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // get the photos
        //System.out.println(responseJsonObject.get("photos"));

        return responseJsonObject;
    }
}