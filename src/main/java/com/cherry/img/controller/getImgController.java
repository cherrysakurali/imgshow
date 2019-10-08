package com.cherry.img.controller;

import com.alibaba.fastjson.JSON;
import com.cherry.img.utils.FileTool;
import com.cherry.img.utils.ImgTool;
import com.cherry.img.utils.filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class getImgController {
    @Autowired
    private FileTool fileTool;
    @Autowired
    private ImgTool imgTool;


    @RequestMapping("/getDirectory")
    public String getDirectory(HttpSession session) {
        File[] files = fileTool.getDirectory("/home/share");
        List<String> fileList = new ArrayList<String>();
        for (File file : files) {
            fileList.add(file.getName());
        }
        session.setAttribute("dire", fileList);
        return "directory";
    }

    @RequestMapping("/getThumbImg")
    public String getImg(Integer directory, HttpSession session) throws IOException {
        List<String> list = (List<String>) session.getAttribute("dire");
        String pack = list.get(directory);
        File dire = new File("/home/share/" + pack);
        File thumbdire = new File("/home/share/" + pack + "/thumb");
//        File dire = new File("/home/share" + pack);
//        File thumbdire = new File("/home/share" + pack + "/thumb");
        if (thumbdire.listFiles().length == 0) {
            imgChange(dire.getAbsolutePath());
        }
        File[] files = fileTool.getImg(thumbdire);
        List<String> fileList = new ArrayList<String>();
        for (File file : files) {
            fileList.add(file.getName());
        }
        session.setAttribute("pack", pack);
        session.setAttribute("local", pack + "\\" + thumbdire.getName());
        session.setAttribute("img", fileList);
        return "img";
    }

    @RequestMapping("/getImg")
    public String getImg(String filepath, HttpServletRequest request) {
        String RealFile = filepath.replace("-thumbnail", "");
        request.setAttribute("realpath", RealFile);
        return "oriImg";
    }

    public static void imgChange(String abs_dire) {
        File file = new File(abs_dire);
        File[] files = file.listFiles();
        for (File f : files) {
            try {
                ImgTool.generateThumbnail2Directory(0.25f, abs_dire + "/thumb", f.getAbsolutePath());
            } catch (IOException e) {
                try {
                    ImgTool.generateThumbnail2Directory(0.25f, abs_dire + "/thumb", files[files.length - 1].getAbsolutePath());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
