package ku.cs.config;

import java.io.File;

public class Configuration {
    private static Configuration configuration;
    private final String imagesPath;
    private final String defaultImagePath;
    private final String defaultProfilePictureFileName;
    private final String requestFormsPdfPath;

    private Configuration() {
        defaultProfilePictureFileName = "default-image.jpg";
        imagesPath = "data" + File.separator + "profile-pictures";
        defaultImagePath = "src" + File.separator + "main" + File.separator + "resources" + File.separator + "images" + File.separator + "default-image.jpg";
        requestFormsPdfPath = "data" + File.separator + "request-forms-pdf";
    }

    public static Configuration getConfiguration() {
        if (configuration == null) {
            configuration = new Configuration();
        }
        return configuration;
    }

    public String getDefaultProfilePictureFileName() {
        return defaultProfilePictureFileName;
    }

    public String getImagesPath() {
        return imagesPath;
    }

    public String getDefaultImagePath() {
        return defaultImagePath;
    }

    public String getRequestFormsPdfPath() {
        return requestFormsPdfPath;
    }

}
