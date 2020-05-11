package com.cdq.until;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ：ヅてＤＱ
 * @date ：Created in 2020/5/11 15:18
 * @description：
 * @modified By：
 * @version: 1.0.1
 */
public class VideoUtil {

    /**
     * 处理视频文件
     *
     * @param file
     * @return 如果返回null表示，file是null
     */
    public static String uploadFile(CommonsMultipartFile file, String pathName) {
        //判断文件是否为空
        if (file.isEmpty()) {
            return null;
        }
        // 获取文件名
        String fileName = file.getOriginalFilename();
        fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_" + fileName;
        System.out.print("（加个时间戳，尽量避免文件名称重复）保存的文件名为: " + fileName + "\n");
        //加个时间戳，尽量避免文件名称重复
        String basePath = "D:/projectDev/media_video/";
        String path = pathName + "/" + fileName;
        System.out.print("保存文件绝对路径" + basePath + path + "\n");
        //创建文件路径
        File dest = new File(basePath + path);
        //判断文件是否已经存在
        if (dest.exists()) {
            return "文件已经存在";
        }
        //判断文件父目录是否存在
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdir();
        }
        try {
            //上传文件
            file.transferTo(dest); //保存文件
        } catch (IOException e) {
            return "上传失败";
        }
        return path;
    }
}
