package com.yahoo.imageviewer.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.yahoo.imageviewer.R;
import com.yahoo.imageviewer.adapters.ImageResultsAdapter;
import com.yahoo.imageviewer.models.ImageResult;
import com.yahoo.imageviewer.models.Settings;
import com.yahoo.imageviewer.utilities.EndlessScrollListener;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class HomeActivity extends Activity {

    EditText etSearch;
    GridView gvSearchResultView;
    ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
    ArrayList<ImageResult> imageResultsTemp = new ArrayList<ImageResult>();
    ImageResultsAdapter imageResultsAdapter = null;
    String query;
    Settings settings = new Settings();
    private final int REQUEST_CODE = 1234567;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        etSearch = (EditText)findViewById(R.id.etSearch);
        gvSearchResultView = (GridView)findViewById(R.id.gvImageResults);

        imageResultsAdapter = new ImageResultsAdapter(this,imageResults);
        gvSearchResultView.setAdapter(imageResultsAdapter);

        gvSearchResultView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                 //launch the image display activity
                Intent i = new Intent(HomeActivity.this,ImageDisplayActivity.class);
                ImageResult imageResult =  imageResults.get(position);
                i.putExtra("result",imageResult);
                startActivity(i);
            }
        });

        getActionBar().setBackgroundDrawable(getWallpaper());
        gvSearchResultView.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                Log.d("DEBUG","onLoadMore called page num: "+page);
                Toast.makeText(HomeActivity.this,"on-scroll called",Toast.LENGTH_SHORT).show();
                getImagesFromGoogleApi(page);
                imageResultsAdapter.addAll(imageResultsTemp);
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_refresh:
                Toast.makeText(this, "Reset settings and query", Toast.LENGTH_SHORT).show();
                imageResultsAdapter.clear();
                settings = new Settings();
                etSearch.setText("");
                query="";
                break;
            // action with ID action_settings was selected
            case R.id.action_settings:
                //Take the user to another activity to set the search criterias
                //Take the user back to search page with settings result.
//                Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT).show();
                Intent i  = new Intent(this,SettingsActivity.class);
                i.putExtra("settings",settings);
                startActivityForResult(i, REQUEST_CODE);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            settings = (Settings)data.getSerializableExtra("settings");
        }
    }

    private String appendSettings(String url){
        if(settings.getColorFilter()!=null && settings.getColorFilter().length()>0){
            url=url+"&imgcolor="+settings.getColorFilter();
        }

        if(settings.getImageSize()!=null && settings.getImageSize().length()>0){
            if("Small".equalsIgnoreCase(settings.getImageSize())) {
                url = url + "&imgsz=small";
            }

            if("Medium".equalsIgnoreCase(settings.getImageSize())) {
                url = url + "&imgsz=medium";
            }

            if("Large".equalsIgnoreCase(settings.getImageSize())) {
                url = url + "&imgsz=xxlarge";
            }

            if("Extra Large".equalsIgnoreCase(settings.getImageSize())) {
                url = url + "&imgsz=huge";
            }

        }

        if(settings.getImageType()!=null && settings.getImageType().length()>0){
            url=url+"&imgtype="+settings.getImageType();
        }
        return url;
    }

    private void getImagesFromGoogleApi(int start){
        // https://ajax.googleapis.com/ajax/services/search/images
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&rsz=8&q="+ query+"&start="+(start*8);
        url =  appendSettings(url);
        Log.d("DEBUG",url);
        client.get(url,new JsonHttpResponseHandler(){
            JSONArray imageResultsJson = null;
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("Tag1", response.toString());
                try {
                    imageResultsJson =  response.getJSONObject("responseData").getJSONArray("results");
                    imageResultsTemp = ImageResult.fromJsonArray(imageResultsJson);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.d("DEBUG","getImagesFromGoogleApi: onFailure");
            }
        });
    }

    private void fetchAndPopulateSearchResult(int start){
        imageResultsAdapter.clear(); //Clearing before every click on search button
        query = etSearch.getText().toString();
        if(query == null || query.length()==0){
            imageResultsTemp=new ArrayList<ImageResult>();
            Toast.makeText(this,"Please enter search query",Toast.LENGTH_SHORT).show();
        }
        getImagesFromGoogleApi(start);
        imageResultsAdapter.addAll(imageResultsTemp);
    }

    //Layout methods
    public void onClickBtnSearch(View v) {
        fetchAndPopulateSearchResult(0);
    }
}
