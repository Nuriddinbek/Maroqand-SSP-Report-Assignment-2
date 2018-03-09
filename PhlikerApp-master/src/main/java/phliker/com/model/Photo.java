package phliker.com.model;

/**
 * Created by nsarvar on 2/16/18.
 */

public class Photo {
    /**
     * Declaration of member variables
     */
    private String id;
    private String server;
    private String secret;
    private String farm;
    private String title;


    /**
     * Return the String value of id of photo
     * @return id property
     */
    public String getId() {
        return id;
    }

    /**
     * Set id property of photo
     * @param id property of photo
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Return the String value of server of photo
     * @return server property
     */
    public String getServer() {
        return server;
    }

    /**
     * Set server property of photo
     * @param server property
     */
    public void setServer(String server) {
        this.server = server;
    }

    /**
     * Return the String value of secret of photo
     * @return secret property
     */
    public String getSecret() {
        return secret;
    }

    /**
     * Set secret property of photo
     * @param secret property
     */
    public void setSecret(String secret) {
        this.secret = secret;
    }

    /**
     * Return the String value of farm of photo
     * @return farm property
     */
    public String getFarm() {
        return farm;
    }

    /**
     * Set farm property of photo
     * @param farm property
     */
    public void setFarm(String farm) {
        this.farm = farm;
    }

    /**
     * Return the String value of tittle of photo
     * @return title property
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set title property of photo
     * @param title property
     */
    public void setTitle(String title) {
        this.title = title;
    }

}