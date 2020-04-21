package com.cdq.util;

import java.io.File;

public class PathUtil {

    private static String seperator = System.getProperty("file.separator");


    public static String getImageBasePath() {
        String basePath = "";
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")) {
            basePath = ConstansUtil.WINDOWS_SRC;
        } else {
            basePath = ConstansUtil.LINUX_SRC;
        }
        basePath.replace("/", seperator);
        return basePath;
    }

    public static String getShopImagePath(String pex) {
        String imagePath = "/"+pex+"/";
        return imagePath.replace("/", seperator);
    }

}
