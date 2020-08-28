package com.cdq.until;

public class PathUtil {

    private static String seperator = System.getProperty("file.separator");
    public static String WINDOWS_SRC = "D:/projectdev/media_image/";
    public static String LINUX_SRC = "/home/media_image/";

    public static String getImageBasePath() {
        String basePath = "";
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")) {
            basePath = WINDOWS_SRC;
        } else {
            basePath = LINUX_SRC;
        }
        basePath.replace("/", seperator);
        return basePath;
    }

    public static String getShopImagePath(String articleId) {
        String imagePath = "/article/";

        return imagePath.replace("/", seperator);
    }

}
