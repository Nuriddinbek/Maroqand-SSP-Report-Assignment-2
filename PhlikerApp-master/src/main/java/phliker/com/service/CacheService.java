package phliker.com.service;

import javafx.scene.image.Image;
import phliker.com.model.FlickrResponse;
import phliker.com.model.Photo;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nsarvar on 2/20/18.
 */
public class CacheService implements Service {
    /**
     * Declaration of class member variables
     */
    private Service flickrService;
    private Map<String,Image> cachedImages;
    private int counter = 0;

    /**
     * CacheService class constructor for initialization of member variables
     */
    public CacheService() {
        flickrService = new FlickrService();
        cachedImages = new HashMap<String, Image>();
    }

    /**
     * Return FlickrResponse type method for searching photo
     * @param tags for input keyword for photo
     * @return FlickrResponse method
     */
    // don't change this method
    @Override
    public FlickrResponse searchPhoto(String tags){
        return flickrService.searchPhoto(tags);
    }

    /**
     * Get image by requesting FlickrService method
     * Store fetched images id and image into cachedImages
     * @param photo the requested photo
     * @return image retreived from FlickrService
     */
    @Override
    public Image getImage(Photo photo) {
        Image image = flickrService.getImage(photo);
        cachedImages.put(photo.getId(), image);
        //System.out.println("------ " + cachedImages.toString());
        return image;
    }

    /**
     * Get image from cache if exist otherwise request to FlickrService
     * @param photo the requested photo
     * @return image retreived from cache or FlickrService
     */
    public Image getImageFromService(Photo photo){
        if(cachedImages.containsKey(photo.getId())){
            Image image = cachedImages.get(photo.getId());
            System.out.println("exist in cache - YES");
            return image;
        }
        else{
            System.out.println("exist in cache - NO");
            return getImage(photo);
        }
    }

    /**
     * Clear cache when search counter is 0
     * @param counter search index
     */
    public void clearCache(int counter){
        if (counter==0){
            cachedImages.clear();
        }
    }

}