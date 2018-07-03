package com.mateusz.grabarski.redditapp.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mateusz Grabarski on 03.07.2018.
 */
public class ExtractXML {

    private String tag;
    private String xml;

    public ExtractXML(String xml, String tag) {
        this.tag = tag;
        this.xml = xml;
    }

    public List<String> start() {
        List<String> result = new ArrayList<>();

        String[] splitXML = xml.split(tag + "\"");
        int count = splitXML.length;

        for (int i = 1; i < count; i++) {
            String temp = splitXML[i];
            int index = temp.indexOf("\"");
            temp = temp.substring(0, index);
            result.add(temp);
        }

        return result;
    }
}
