package phliker.com.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import phliker.com.model.FlickrResponse;
import phliker.com.model.Photo;
import phliker.com.model.Photos;
import phliker.com.service.CacheService;
import phliker.com.service.FlickrService;
import phliker.com.utils.AppProperties;
import java.util.HashMap;
import java.util.Map;

/**
 * PhotoController class begins
 */
public class PhotoController {
    // view elements
    @FXML
    TextField searchField;
    @FXML
    Button searchButton;
    @FXML
    ImageView imageView;
    @FXML
    ImageView loaderImageView;
    @FXML
    Button prevButton;
    @FXML
    Button nextButton;
    @FXML
    Label title;
    @FXML
    Label counter;
    CacheService cacheService;
    private boolean debugging;
    private Map<Integer,Photo> allphoto;
    private FlickrService service;
    private int counterImage;

    /**
     * PhotoController class constructor for initialization of:
     *  - debugging property
     *  - cacheService instance
     *
     */
    public PhotoController() {
        debugging = AppProperties.getBool("debug");
        cacheService = new CacheService();

        if (debugging) {
            System.out.println("[debug] PhotoController: constructor");
        }
    }

    /**
     *  keyReleased method for disabling search button when input box is empty
     */
    public void keyReleased(){
        String input = searchField.getText();
        boolean isEnable = (input.isEmpty()||input.trim().isEmpty());
        searchButton.setDisable(isEnable);
    }

    /**
     * Search for requested photo
     * Set parameters of retreived photo to Photo class
     * @param event Action handler on search button click
     * @throws JSONException JSON parsing exception for fetching json data
     */
    @FXML
    private void searchImage(ActionEvent event) throws JSONException {
        counterImage=0;
        cacheService.clearCache(counterImage);
        System.out.println(" search button clicked ");
        String input = searchField.getText();
        input = input.replace(" ", "+");
        service = new FlickrService();
        FlickrResponse response = service.searchPhoto(input);
        JSONObject obj = response.getObject();
        System.out.println("Get object \"photos\": " + obj.get("photos"));
        JSONObject jsonObject = (JSONObject) obj.get("photos");
        JSONArray jsonArray = (JSONArray)jsonObject.get("photo");

        allphoto = new HashMap<Integer, Photo>();

        for (int i=0; i<jsonArray.size();i++){
            Photo photo = new Photo();
            System.out.println("success: " + i);
            JSONObject idJson = (JSONObject) jsonArray.get(i);
            String id = (String) idJson.get("id");
            System.out.print(id);
            photo.setId(id);

            JSONObject serverJson = (JSONObject) jsonArray.get(i);
            String server = (String) serverJson.get("server");
            System.out.print(" " + server);
            photo.setServer(server);


            JSONObject secretJson = (JSONObject) jsonArray.get(i);
            String secret = (String) secretJson.get("secret");
            System.out.print(" " + secret);
            photo.setSecret(secret);

            JSONObject farmJson = (JSONObject) jsonArray.get(i);
            Long farm =  (Long) farmJson.get("farm");
            System.out.print(" " + farm);
            photo.setFarm(String.valueOf(farm));

            JSONObject titleJson = (JSONObject) jsonArray.get(i);
            String title = (String) titleJson.get("title");
            System.out.print(" " + title);
            photo.setTitle(title);

            allphoto.put(i,photo);

            System.out.println("\n-----------------------------------------");
        }
        //System.out.println("Print allphoto hash map: " + allphoto);
        Photos ph = new Photos();
        ph.setPhotos(allphoto);
        imageView.setImage(cacheService.getImageFromService(allphoto.get(counterImage)));

        title.setText(allphoto.get(counterImage).getTitle());
        counter.setText((counterImage+1) + " of 20 images");

        nextButton.setDisable(false);

    }

    /**
     * Display next photo
     * @param event Action handler for nextImage button click
     */
    @FXML
    public void nextImage(ActionEvent event) {
        if (counterImage<19){
            nextButton.setDisable(false);
            prevButton.setDisable(false);
            counterImage++;
            imageView.setImage(cacheService.getImageFromService(allphoto.get(counterImage)));
            //System.out.println(counterImage + " " + allphoto.get(counterImage).getId() + " " + allphoto.get(counterImage).getServer());
            title.setText(allphoto.get(counterImage).getTitle());
            counter.setText((counterImage+1) + " of 20 images");
            System.out.println(" next button clicked ");
        }
        else{
            //counterImage=0;
            nextButton.setDisable(true);
        }

    }

    /**
     * Display previous photo
     * @param event Action handler for previous button click
     */
    @FXML
    public void prevImage(ActionEvent event) {
        if (counterImage>0){
            nextButton.setDisable(false);
            prevButton.setDisable(false);
            counterImage--;
            imageView.setImage(cacheService.getImageFromService(allphoto.get(counterImage)));
            title.setText(allphoto.get(counterImage).getTitle());
            counter.setText((counterImage+1) + " of 20 images");
            System.out.println(" prev button clicked ");
        }
        else{
            prevButton.setDisable(true);
            //counterImage=19;
        }

    }
}
