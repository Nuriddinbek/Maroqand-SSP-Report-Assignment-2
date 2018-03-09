package phliker.com.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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

    private final String ID_PHOTO = "id";
    private final String SECRET_PHOTO = "secret";
    private final String SERVER_PHOTO = "server";
    private final String FARM_PHOTO = "farm";
    private final String TITLE_PHOTO = "title";

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
        //cacheService.clearCache(counterImage);

        System.out.println(" search button clicked ");
        String input = searchField.getText();
        input = input.replace(" ", "+");

        service = new FlickrService();
        FlickrResponse response = service.searchPhoto(input);
        if (response==null){
            return;
        }
        if(setPhotoParameters(response,allphoto)){
            imageView.setImage(cacheService.getImageFromService(allphoto.get(counterImage)));
            setControlParameters(counterImage);
        }
    }

    /**
     * Set parameters of all fetched photo
     * @param flickrResponse instance attribute
     * @param photoMap Map instance for collection of photos
     */
    public boolean setPhotoParameters(FlickrResponse flickrResponse, Map<Integer,Photo> photoMap){
        JSONObject obj = flickrResponse.getObject();
        //System.out.println("Get object \"photos\": " + obj.get("photos"));
        JSONObject jsonObject = (JSONObject) obj.get("photos");
        JSONArray jsonArray = (JSONArray)jsonObject.get("photo");

        allphoto = new HashMap<Integer, Photo>();
        if (!contentInvalidShow(jsonArray)){
            return false;
        }
        for (int i=0; i<jsonArray.size();i++){
            Photo photo = new Photo();
            System.out.println("success: " + i);


            photo.setId(fetchJsonResponse(jsonArray,ID_PHOTO,i));

            photo.setServer(fetchJsonResponse(jsonArray,SERVER_PHOTO,i));

            photo.setSecret(fetchJsonResponse(jsonArray,SECRET_PHOTO,i));

            photo.setFarm(fetchJsonResponse(jsonArray,FARM_PHOTO,i));

            photo.setTitle(fetchJsonResponse(jsonArray,TITLE_PHOTO,i));

            allphoto.put(i,photo);

            System.out.println("\n-----------------------------------------");
        }
        //System.out.println("Print allphoto hash map: " + allphoto);
        Photos ph = new Photos();
        ph.setPhotos(allphoto);
        return true;
    }

    /**
     * Fetch JSON object response attributes
     * @param jsonArray of photos
     * @param contentOf of each property
     * @param index of each photo object
     * @return content of each property of photo
     */
    public String fetchJsonResponse(JSONArray jsonArray, String contentOf, int index){
        String content = "";
        JSONObject contentJson = (JSONObject) jsonArray.get(index);
        if(contentOf.matches("farm")){
            Long farm = (Long) contentJson.get(FARM_PHOTO);
            content = String.valueOf(farm);
        }
        else{
            content = (String) contentJson.get(contentOf);
        }

        System.out.print(" " + content);
        return content;
    }

    /**
     * Set Button and Label Contols parameters
     * @param count of iteration
     */
    public void setControlParameters(int count){
        title.setText(allphoto.get(count).getTitle());
        counter.setText((count+1) + " of 20 images");
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

    /**
     * Alert if input does not result in enough content
     * @param jsonArray of json object photos
     * @return true if content is enough
     */
    private boolean contentInvalidShow(JSONArray jsonArray){
        if(jsonArray.size()<19){
            System.out.println("Not enough content!");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Attention");
            alert.setContentText("Not enough content");
            searchField.setText("");
            alert.showAndWait();
            searchButton.setDisable(true);
            nextButton.setDisable(true);
            prevButton.setDisable(true);
            return false;
        }
        return true;
    }
}