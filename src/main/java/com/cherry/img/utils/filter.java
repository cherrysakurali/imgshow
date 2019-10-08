package com.cherry.img.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Pattern;

@Component
public class filter implements FilenameFilter {
    private Pattern pattern;

    public filter() {
        pattern = Pattern.compile("^[0-9]+$");
    }

    @Override
    public boolean accept(File dir, String name) {
        return pattern.matcher(name).matches();
    }

}
