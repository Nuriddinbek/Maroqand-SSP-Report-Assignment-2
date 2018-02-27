package phliker.com.model;

import java.util.Map;

/**
 * Created by nsarvar on 2/16/18.
 */
public class Photos {
    /**
     * Map type hashmap for collection of 20 photos
     */
    private Map<Integer, Photo> photos;

    /**
     * Return the Mapped value of collected photos
     * @return photos requested
     */
    public Map<Integer, Photo> getPhotos() {
        return photos;
    }

    /**
     * Set all photos to hashmap
     * @param photos of collection
     */
    public void setPhotos(Map<Integer, Photo> photos) {
        this.photos = photos;
    }
    /*
    modify this class. Think about how it might look. It is depend on JSON response structure
    */
}