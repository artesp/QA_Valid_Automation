package Assistant;

public class AddressEntity {

    public static String getUrl() {
        return URL;
    }

    public static void setUrl(String _url) {
        URL = _url;
    }

    public static String getBasePath(){
        return BasePath;
    }

    public static void setBasePath(String _basePath){
        BasePath = _basePath;
    }

    public static String URL;
    public static String BasePath;
}
