package com.yahoo.imageviewer.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.yahoo.imageviewer.R;
import com.yahoo.imageviewer.models.Settings;

public class SettingsActivity extends Activity implements AdapterView.OnItemSelectedListener {

    protected Spinner spImageSize;
    protected Spinner spImageType;
    protected Spinner spColorFilter;
    protected EditText etSiteFiler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getActionBar().setBackgroundDrawable(getWallpaper());
        getActionBar().removeAllTabs();


        spImageSize = (Spinner) findViewById(R.id.spImageSize);
        spImageType = (Spinner) findViewById(R.id.spImageType);
        spColorFilter = (Spinner) findViewById(R.id.spColorFilter);
        etSiteFiler = (EditText) findViewById(R.id.etSiteFilter);

//        spImageSize.setOnItemSelectedListener(this);
//        spImageType.setOnItemSelectedListener(this);
//        spColorFilter.setOnItemSelectedListener(this);


        //Image Size
        ArrayAdapter<CharSequence> imageSizeAdapter = ArrayAdapter.createFromResource(this,
                R.array.image_size, android.R.layout.simple_spinner_item);
        imageSizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spImageSize.setAdapter(imageSizeAdapter);


        //ImageType
        ArrayAdapter<CharSequence> imageTypeAdapter = ArrayAdapter.createFromResource(this,
                R.array.image_type, android.R.layout.simple_spinner_item);
        imageTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spImageType.setAdapter(imageTypeAdapter);


        //ColorFilter
        ArrayAdapter<CharSequence> colorFilterAdapter = ArrayAdapter.createFromResource(this,
                R.array.color_filter, android.R.layout.simple_spinner_item);
        colorFilterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spColorFilter.setAdapter(colorFilterAdapter);

        Settings settings = (Settings) getIntent().getSerializableExtra("settings");
        //Copy default values for initial settings
        if (settings.getColorFilter().length() == 0 && settings.getImageSize().length() == 0
                && settings.getImageType().length() == 0 && settings.getSiteFilter().length() == 0) {
            //This means settings model is fresh and update that with xml defaults.
            settings.setImageType(spImageType.getSelectedItem().toString());
            settings.setImageSize(spImageSize.getSelectedItem().toString());
            settings.setColorFilter(spColorFilter.getSelectedItem().toString());
        } else {
//            imageSizeAdapter.setDropDownViewResource(imageSizeAdapter.getPosition(settings.getImageSize()));
//            imageTypeAdapter.setDropDownViewResource(imageTypeAdapter.getPosition(settings.getImageType()));
//            colorFilterAdapter.setDropDownViewResource(colorFilterAdapter.getPosition(settings.getColorFilter()));
//          etSiteFiler.setText(settings.getSiteFilter());

            spImageSize.setSelection(imageSizeAdapter.getPosition(settings.getImageSize()));
            spImageType.setSelection(imageTypeAdapter.getPosition(settings.getImageType()));
            spColorFilter.setSelection(colorFilterAdapter.getPosition(settings.getColorFilter()));
            etSiteFiler.setText(settings.getSiteFilter());
        }
    }

    //LAYOUT METHOD
    public void onClickSaveSettings(View v) {
        Settings settings = new Settings();
        settings.setColorFilter((String) spColorFilter.getSelectedItem());
        settings.setImageSize((String) spImageSize.getSelectedItem());
        settings.setImageType((String) spImageType.getSelectedItem());
        settings.setSiteFilter(etSiteFiler.getText().toString());
        Intent data = new Intent();
        data.putExtra("settings", settings);
        // Activity finished ok, return the data
        setResult(RESULT_OK, data); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//        Toast.makeText(this, "Autosaved!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
