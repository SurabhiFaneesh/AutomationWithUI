package utils;

import java.io.File;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.ReloadingFileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.reloading.PeriodicReloadingTrigger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.net.URL;

public class Config {
    private static final Logger log = LogManager.getLogger(Config.class);
    private static Config instance;
    private static ReloadingFileBasedConfigurationBuilder<FileBasedConfiguration> builder;

    // Property name definitions
    public static final String CFG_CSAT_WINDOW_DAYS = "csat.window_days";
    public static Properties properties;

    private Config() {
        properties = new Properties();
        Parameters params = new Parameters();
        File propertiesFile = null;
        URL resource = getClass().getClassLoader().getResource("automation.properties");
        System.out.println("Automation property file path : "+resource.getPath());
        if (resource == null) {
            throw new IllegalArgumentException("file is not found!");
        } else {
            propertiesFile = new File(resource.getFile());
        }

        builder = new ReloadingFileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                .configure(params.fileBased()
                        .setFile(propertiesFile));
        PeriodicReloadingTrigger trigger = new PeriodicReloadingTrigger(builder.getReloadingController(),
                null, 1, TimeUnit.MINUTES);
        trigger.start();
        log.debug("Config end");
    }

    public Config getInstance() {
        if (instance == null)
            instance = new Config();
        return instance;
    }
    public static String getStringParam(String paramName) {
        if (instance == null)
            instance = new Config();
        try {
            return builder.getConfiguration().getString(paramName);
        } catch (ConfigurationException e) {
            log.error("Configuration parameter " + paramName + " is missing");
            return null;
        }
    }

    public static Long getLongParam(String paramName) {
        if (instance == null)
            instance = new Config();
        try {
            return builder.getConfiguration().getLong(paramName);
        } catch (ConfigurationException e) {
            log.error("Configuration parameter " + paramName + " is missing");
            return null;
        }
    }

    public static Integer getIntParam(String paramName) {
        if (instance == null)
            instance = new Config();
        try {
            return builder.getConfiguration().getInt(paramName);
        } catch (ConfigurationException e) {
            log.error("Configuration parameter " + paramName + " is missing");
            return null;
        }
    }

    public static String getEncryptedStringParam(String paramName) {
        String value = getStringParam(paramName);

        if (value != null) {
            value = CryptoUtil.getInstance().decryptValue(value);
        }
        return value;
    }

   public static String getPropertyValue(String propertyName) {
       if (instance == null)
           instance = new Config();
       try {
           return builder.getConfiguration().getProperty(propertyName).toString();
       } catch (ConfigurationException e) {
           log.error("Configuration parameter " + propertyName + " is missing");
           return null;
       }
   }

   public static void setPropertyValue(String key, String value) {
       if (instance == null)
           instance = new Config();
       try {
           builder.getConfiguration().setProperty(key, value);
       } catch (ConfigurationException e) {
           log.error("unable to set value "+value+" for a key "+key);
       }
   }
}
