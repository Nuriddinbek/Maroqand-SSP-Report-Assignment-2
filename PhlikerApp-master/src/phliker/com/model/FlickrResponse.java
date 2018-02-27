package phliker.com.model;

/**
 * Created by nsarvar on 2/16/18.
 */
public class FlickrResponse {

    private Photos photos;
    private org.json.simple.JSONObject object;

    /**
     * Return instance of Photos class
     * @return photos oobject of Photos class
     */
    public Photos getPhotos() {
        return photos;
    }

    /**
     * Set Photos object type variable
     * @param photos oobject of Photos class
     */
    public void setPhotos(Photos photos) {
        this.photos = photos;
    }

    /**
     * Return JSON object
     * @return JSON object
     */
    public org.json.simple.JSONObject getObject() {
        return object;
    }

    /**
     * Set JSON object member variable
     * @param object JSON object
     */
    public void setObject(org.json.simple.JSONObject object) {
        this.object = object;
    }


    /*
    modify this class. Think about how it might look. It is depend on JSON response structure
    */
}
