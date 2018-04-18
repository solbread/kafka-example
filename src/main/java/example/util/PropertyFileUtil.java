package example.util;

import org.apache.log4j.Logger;

import java.io.FileWriter;
import java.io.InputStream;
import java.util.Properties;

public class PropertyFileUtil {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private final Properties properties;
    private final String propertyFileName;

    public PropertyFileUtil(String propertyFileName) {
        this.properties = new Properties();
        this.propertyFileName = propertyFileName + ".properties";
        initializeProperties();
    }

    private void initializeProperties() {
        try(InputStream propInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(this.propertyFileName)) {
            this.properties.load(propInputStream);
            logger.info("properties file load success");
        } catch (Exception e) {
            logger.error("properties load failure : error message- " + e.getMessage(), e);
        }
    }

    public String getStringValue(String key) {
        return this.properties.getProperty(key);
    }

    public String getStringValue(String key, String defaultValue) {
        return this.properties.getProperty(key, defaultValue);
    }

    public int getIntValue(String key) throws Exception {
        return Integer.parseInt(this.properties.getProperty(key));
    }

    public long getLongValue(String key) throws Exception {
        return Long.parseLong(this.properties.getProperty(key));
    }

    public void setPropertiesValue(String name, String value, String comment) throws Exception {
        this.properties.setProperty(name, value);
        this.properties.store(new FileWriter(this.propertyFileName), comment);
    }

}
