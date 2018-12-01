package com.test.seefood;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends BaseAdapter {

    private Context context;
    private List<Image> images = new ArrayList();
    private boolean layout;

    public ImageAdapter(Context context, List<Image> bitmaps, boolean layout) {
        this.context = context;
        this.images = bitmaps;
        this.layout = layout;
    }

    public void setLayout(boolean bool) {
        layout = bool;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int position) {
        return images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Image image = images.get(position);

        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.image_layout, null);
        }

        final ImageView imageView = convertView.findViewById(R.id.imageCover);
        final ImageView deleteIcon = convertView.findViewById(R.id.deleteImg);
        final TextView title = convertView.findViewById(R.id.title);

        imageView.setImageBitmap(image.getImage());
        title.setText(image.getName());
        deleteIcon.setAlpha(layout ? 255 : 0);

        return convertView;
    }
}
