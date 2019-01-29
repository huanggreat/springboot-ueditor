package com.udemo.cont.controller;

import com.baidu.ueditor.ActionEnter;
import com.google.gson.Gson;
import com.udemo.cont.util.FileUtil;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/ueditor/jsp")
public class UeditorController {
    @RequestMapping(value = "/controller",method = RequestMethod.GET)
    @ResponseBody
    public void getConfigInfo(HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException {
        response.setContentType("application/json");
        String rootPath = ResourceUtils.getURL("classpath:").getPath();
        try {
            String exec = new ActionEnter(request, rootPath).exec();
            PrintWriter writer = response.getWriter();
            writer.write(exec);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @RequestMapping(value = "/controller", method = RequestMethod.POST)
    @ResponseBody
    public void imgUpload3(MultipartFile upfile, HttpServletResponse response) {
        System.out.println("开始上传");
        Map<String, Object> result = new HashMap<String, Object>();
        String path = "D:\\IMG\\";
        String FileName = "";
        try {
                FileName = UUID.randomUUID().toString() + "_" + upfile.getOriginalFilename();
                FileUtil.uploadFile(upfile.getBytes(),path, FileName);
                Object object = null;
                File file = new File(path+FileName);
                result.put("url", "http://192.168.1.68:8080/"+FileName);
                result.put("size",String.valueOf(file.length()));
                result.put("type",file.getName().substring(file.getName().lastIndexOf(".")));
                result.put("title",upfile.getOriginalFilename());
                result.put("original",upfile.getOriginalFilename());
                result.put("state", "SUCCESS");
                result.put("error",null);
                OutputJson(response,new Gson().toJson(result));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
    public void OutputJson(HttpServletResponse response , String res) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            response.getWriter().print(res);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            response.getWriter().flush();
            response.getWriter().close();
        }
    }
}