package com.yahoo.imageviewer.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.yahoo.imageviewer.adapters.ImageResultsAdapter;
import com.yahoo.imageviewer.models.ImageResult;
import com.yahoo.imageviewer.R;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class HomeActivity extends SherlockFragmentActivity {

    EditText etSearch;
    GridView gvSearchResultView;
    ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
    ImageResultsAdapter imageResultsAdapter = null;

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

        setupTabs();

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_SHOW_HOME);
        getSupportActionBar().setCustomView(R.layout.actionbar_title);
    }

    private void setupTabs() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
    }

//
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.home, menu);
        return super.onCreateOptionsMenu(menu);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.home, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }




    //Layout methods
    public void onClickBtnSearch(View v){
        String query = etSearch.getText().toString();
        if(query == null || query.length()==0){
            Toast.makeText(this,"Please enter search query",Toast.LENGTH_SHORT).show();
        }

        // https://ajax.googleapis.com/ajax/services/search/images
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://ajax.googleapis.com/ajax/services/search/images?v=1.0&rsz=8&q="+ query,new JsonHttpResponseHandler(){
            JSONArray imageResultsJson = null;
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("Tag1", response.toString());
                try {
                    imageResultsJson =  response.getJSONObject("responseData").getJSONArray("results");
                    imageResults.clear();
//                  imageResults.addAll(ImageResult.fromJsonArray(imageResultsJson));
                    imageResultsAdapter.addAll(ImageResult.fromJsonArray(imageResultsJson));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.d("DEBUG",imageResults.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.d("Tag1",responseString);
            }
        });
    }
}
