package Assistant;

public class AddressEntity {

    public static String URL;
    public static String BaseURI;
    public static String BasePath;

    public static String getURL() {
        return URL;
    }

    public static void setURL(String URL) {
        AddressEntity.URL = URL;
    }

    public static String getBaseURI() {
        return BaseURI;
    }

    public static void setBaseURI(String baseURI) {
        BaseURI = baseURI;
    }

    public static String getBasePath() {
        return BasePath;
    }

    public static String setBasePath(String basePath) {
        return BasePath = basePath;
    }
}
