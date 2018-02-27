package phliker.com.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Class for retrieving values from property file.
 * The properties are retrieved from app properties source.
 * <ul>
 *     <li><code>app.properties</code> is located in the <code>/src</code> directory.</li>
 * </ul>
 *
 * @author Sarvar Nishonboyev &lt;sarvar.nishonboyev@gmail.com&gt;
 *
 */
public class AppProperties {

    private static AppProperties propertyObject;
    private Properties properties;
    static final String filename = "app.properties";

    /**
     * Load the properties from the property file(s)
     */
    public void load(){
        properties = new Properties();
        InputStream inputStream = null;

        try {
            inputStream = getClass().getClassLoader().getResourceAsStream(filename);
            if (inputStream == null) {
                System.err.println("[error] Properties: Sorry, unable to find " + filename);
                return;
            }

            properties.load(inputStream);
        }
        catch (IOException e) {
            System.err.println("[error] Properties: failed to read default properties \"" + filename + "\"");
        }
        finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Return the propertyObject properties, creating them if necessary
     *
     * @return the propertyObject properties
     */
    private static AppProperties hasProperties() {
        if (propertyObject == null) {
            propertyObject = new AppProperties();
            propertyObject.load();
        }
        return propertyObject;
    }

    /**
     * Return the value of a boolean property. The property value must be <code>True</code> or <code>False</code>
     *
     * @param propertyName the name of the property
     * @return the value of the property
     */
    public static Boolean getBool(String propertyName){
        String propertyValue = getProperty(propertyName);

        if (propertyValue == null) {
            propertyValue="";
        }
        Boolean boolValue = false;

        switch (propertyValue){
            case "false": boolValue = false; break;
            case "true": boolValue = true; break;
            case "":
                System.err.println("[warning] Properties: boolean expected " + propertyName + "='" + propertyValue + "' (assuming false)");
                boolValue = false;
            break;
            default:
                System.err.println("[error] Properties: boolean expected " + propertyName + "='" + propertyValue + "'");
                System.exit(1);
        }
        return boolValue;
    }

    /**
     * Return the value of an integer property.
     *
     * @param propertyName the name of the property
     * @return the value of the property
     */
    public static int getInt(String propertyName) {
        String propertyValue = getProperty(propertyName);
        if (propertyValue == null) { propertyValue=""; }
        int value = 0;
        try {
            value = Integer.parseInt(propertyValue);
        } catch (NumberFormatException e) {
            System.err.println("[error] Properties: integer expected '" + propertyName + "=" + propertyValue + "'");
            System.exit(1);
        }
        return value;
    }

    /**
     * Return the value of a string property.
     *
     * @param propertyName the name of the property
     * @return the value of the property, or <code>null</code> if not found
     */
    public static String getProperty(String propertyName) {
        String propertyValue = hasProperties().properties.getProperty(propertyName);
        return propertyValue;
    }
}