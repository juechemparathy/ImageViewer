package com.yahoo.imageviewer.activities;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.yahoo.imageviewer.R;

public class SettingsActivity extends Activity implements AdapterView.OnItemSelectedListener{

    protected Spinner spImageSize;
    protected Spinner spImageType;
    protected Spinner spColorFilter;
    protected EditText etSiteFiler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

         spImageSize = (Spinner) findViewById(R.id.spImageSize);
         spImageType = (Spinner) findViewById(R.id.spImageType);
         spColorFilter = (Spinner) findViewById(R.id.spColorFilter);
         etSiteFiler = (EditText)findViewById(R.id.etSiteFilter);

         spImageSize.setOnItemSelectedListener(this);
         spImageType.setOnItemSelectedListener(this);
         spColorFilter.setOnItemSelectedListener(this);
         etSiteFiler.addTextChangedListener(new TextWatcher() {
             @Override
             public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

             }

             @Override
             public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

             }

             @Override
             public void afterTextChanged(Editable editable) {
                 Toast.makeText(SettingsActivity.this,"Autosaved site filter!",Toast.LENGTH_SHORT).show();
             }
         });

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

    }

    //LAYOUT METHOD
    public void onClickSaveSettings(View v){
        this.finish();
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
        Toast.makeText(this,"Autosaved!",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
