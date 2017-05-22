package com.redsparkdev.moviestalker.utilities;

import java.net.URL;

/**
 * Created by Red on 19/05/2017.
 */
public class TrailerInfo {
    private String name = "";
    private String key = "";
    private String site = "";
    private URL link;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public void setLink(URL url){
        link = url;
    }
    public URL getLink(){
        return link;
    }
}
