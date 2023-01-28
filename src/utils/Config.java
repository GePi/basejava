package utils;

import storage.SqlStorage;
import storage.Storage;
import web.ResumeServlet;

import java.io.*;
import java.util.Properties;

public class Config {
    private static final String CONFIG_PATH = "config/resumes.properties";
    private static final Config INSTANCE = new Config();
    private final Storage storage;
    private final File storageDir;

    public static Config getInstance() {
        return INSTANCE;
    }

    private Config() {
        try (InputStream is = getRunContextInputStream()) {
            Properties properties = new Properties();
            properties.load(is);
            storageDir = new File(properties.getProperty("storage.dir"));
            storage = new SqlStorage(properties.getProperty("db.url"), properties.getProperty("db.user"), properties.getProperty("db.password"));
        } catch (IOException e) {
            throw new IllegalStateException("utils.Config file not found " + CONFIG_PATH);
        }
    }

    private InputStream getRunContextInputStream() throws FileNotFoundException {
        return (ResumeServlet.getContext() == null) ? new FileInputStream(CONFIG_PATH) : ResumeServlet.getContext().getResourceAsStream("/" + CONFIG_PATH);
    }

    public File getStorageDir() {
        return storageDir;
    }

    public Storage getStorage() {
        return storage;
    }
}
