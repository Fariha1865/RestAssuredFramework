package utils;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.util.HashMap;
import java.util.Properties;

public class Utils {
    public static void setEnvVar(String key, String value) throws ConfigurationException {
        PropertiesConfiguration config=new PropertiesConfiguration("./src/test/resources/config.properties");
        config.setProperty(key,value);
        config.save();
    }

    public static HashMap<String,String> getHeaderValues(Properties prop){
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + prop.getProperty("token"));
        headers.put("X-AUTH-SECRET-KEY", prop.getProperty("secretKey"));
        return  headers;
    }
    public static int generateRandomId(int min, int max){
        double randomId= Math.random()*(max-min)+min;
        return (int) randomId;
    }


}
