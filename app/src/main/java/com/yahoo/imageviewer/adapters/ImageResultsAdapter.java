package com.yahoo.imageviewer.adapters;

import android.content.Context;
import android.media.Image;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yahoo.imageviewer.R;
import com.yahoo.imageviewer.models.ImageResult;

import java.util.List;

/**
 * Created by jue on 9/13/14.
 */
public class ImageResultsAdapter extends ArrayAdapter<ImageResult> {


    public ImageResultsAdapter(Context context, List<ImageResult> images) {
        super(context, R.layout.image_home, images);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageResult imageResult = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.image_home, parent, false);
        }
        // Lookup view for data population
        ImageView imageView = (ImageView)convertView.findViewById(R.id.ivImage);
        TextView textView =(TextView)convertView.findViewById(R.id.tvImageTitle);

        //clear  the current image
        imageView.setImageResource(0);

        // Populate the data into the template view using the data object
        //Correcting html formatting
        textView.setText(Html.fromHtml(imageResult.title));
        Picasso.with(getContext()).load(imageResult.thumbUrl).into(imageView);

        // Return the completed view to render on screen
        return convertView;    }
}
