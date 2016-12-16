/*
 *   (C) Copyright Ideamart.
 */
package hms.kite.samples.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class PropertyLoader {

    private final static Logger LOGGER = Logger.getLogger(PropertyLoader.class.getName());

    private final static String PROPERTY_NAME="lbs-sample.properties";

    private static PropertyLoader instance;

    private Properties properties = new Properties();

    private PropertyLoader(){
        loadProperties();
    }

    public synchronized static PropertyLoader getInstance(){
        if(instance==null){
            instance=new PropertyLoader();
        }
        return instance;
    }

    private void loadProperties(){

        String path = System.getProperty("user.dir");

        String[] workingDir = path.split("stand-alone");

        try {
            InputStream in = new FileInputStream(workingDir[0]+"stand-alone/conf/lbs-sample.properties");
            properties.load(in);
            in.close();
        } catch (Exception e) {
            LOGGER.info(PROPERTY_NAME+" unable to load.");
            throw new IllegalStateException(e.toString());
        }
    }

    public String getText(String key){
        final String value=properties.getProperty(key);
        if(value==null||value.isEmpty())
            return null;
//            throw new MissingResourceException("Expected value null or empty", PROPERTY_NAME,key);
        else
            return value;
    }
}
