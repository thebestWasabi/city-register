package edu.petersburg.config;

import java.io.InputStream;
import java.util.Properties;

public class Config {

    public static final String DB_URL = "db.url";
    public static final String DB_LOGIN = "db.login";
    public static final String DB_PASSWORD = "db.password";

    private static Properties properties = new Properties();

    public synchronized static String getProperty(String name) {
        if (properties.isEmpty()) {
            try (InputStream inputStream = Config.class.getClassLoader().getResourceAsStream("dao.properties")){
                properties.load(inputStream);
            } catch (Exception exception) {
                exception.printStackTrace();
                throw new RuntimeException(exception);
            }
        }
        return properties.getProperty(name);
    }
}
