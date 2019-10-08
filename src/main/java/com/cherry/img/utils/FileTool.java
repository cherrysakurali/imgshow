package com.cherry.img.utils;

import org.springframework.stereotype.Component;

import java.io.File;


@Component
public class FileTool {
    public File[] getDirectory(String dire) {
        File file = new File(dire);
        filter filter=new filter();
        File[] files = file.listFiles(filter);
        if (file.isDirectory()) {
            return files;
        }
        return null;
    }

    public File[] getImg(File dire) {
        File[] files = dire.listFiles();
        return files;
    }


}
