package com.yahoo.imageviewer.models;

import java.io.Serializable;

/**
 * Created by jue on 9/15/14.
 */
public class Settings implements Serializable {
    private String imageSize="";
    private String imageType="";
    private String colorFilter="";
    private String siteFilter="";

    public String getImageSize() {
        return imageSize;
    }

    public void setImageSize(String imageSize) {
        this.imageSize = imageSize;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public String getColorFilter() {
        return colorFilter;
    }

    public void setColorFilter(String colorFilter) {
        this.colorFilter = colorFilter;
    }

    public String getSiteFilter() {
        return siteFilter;
    }

    public void setSiteFilter(String siteFilter) {
        this.siteFilter = siteFilter;
    }
}
