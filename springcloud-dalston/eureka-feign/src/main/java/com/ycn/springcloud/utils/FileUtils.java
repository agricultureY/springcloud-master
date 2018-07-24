package com.ycn.springcloud.utils;

import java.io.File;
import java.io.FileOutputStream;

/**
 * 文件操作工具类
 */
public class FileUtils {

    /**
     * 文件上传
     *
     * @param file     文件byte数组
     * @param filePath 上传文件路径
     * @param fileName 文件名称
     * @throws Exception
     */
    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(targetFile);
        out.write(file);
        out.flush();
        out.close();
    }
}
