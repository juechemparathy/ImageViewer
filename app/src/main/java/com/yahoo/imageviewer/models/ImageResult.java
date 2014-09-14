package com.yahoo.imageviewer.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by jue on 9/13/14.
 */
public class ImageResult implements Serializable {
    public String fullUrl;
    public String thumbUrl;
    public String title;

    public ImageResult(JSONObject jsonObject){
        try {
            this.fullUrl= jsonObject.getString("url");
            this.thumbUrl=jsonObject.getString("tbUrl");
            this.title = jsonObject.getString("title");
        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    public static ArrayList<ImageResult> fromJsonArray(JSONArray jsonArray){
        ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
        for(int i=0;i<jsonArray.length();i++){
            try {
                imageResults.add(i,new ImageResult(jsonArray.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return  imageResults;
    }
}
