package com.udemo.cont.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtil {
    //文件上传工具类服务方法
    public static void uploadFile(byte[] file, String filePath, String fileName) {
        try{
            File targetFile = new File(filePath);
            if(!targetFile.exists()){
                targetFile.mkdirs();
            }
            FileOutputStream out = new FileOutputStream(filePath+fileName);
            out.write(file);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
