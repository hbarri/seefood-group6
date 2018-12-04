package com.test.seefood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends BaseAdapter {

    private Context context;
    private List<Image> images = new ArrayList();
    private boolean layout;
    private boolean fav = false;
    private boolean showFav = false;
    ImageView deleteIcon;

    /**
     * constructor for image adapter
     * @param context
     * @param bitmaps
     * @param layout
     * @param showFav
     */
    public ImageAdapter(Context context, List<Image> bitmaps, boolean layout, boolean showFav) {
        this.context = context;
        this.images = bitmaps;
        this.layout = layout;
        this.showFav = showFav;
    }

    /**
     * set images list
     * @param images
     */
    public void setImages(List<Image> images) {
        this.images = images;
    }

    /**
     * either displays or removes delete icon
     * @param bool
     */
    public void setLayout(boolean bool) {
        layout = bool;
        deleteIcon.setAlpha(layout ? 255 : 0);
    }

    /**
     * returns layout
     * @return
     */
    public boolean getLayout() {
        return layout;
    }

    /**
     * returns image size
     * @return
     */
    @Override
    public int getCount() {
        return images.size();
    }

    /**
     * returns specific item from list
     * @param position
     * @return
     */
    @Override
    public Object getItem(int position) {
        return images.get(position);
    }

    /**
     * gets item id
     * @param position
     * @return
     */
    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * set image layout
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Image image = images.get(position);

        // sets convertView if it hasnt been set
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.image_layout, null);
        }

        //finds items by id
        final ImageView imageView = convertView.findViewById(R.id.imageCover);
        deleteIcon = convertView.findViewById(R.id.deleteImg);
        final TextView title = convertView.findViewById(R.id.title);
        final ImageView favorite = convertView.findViewById(R.id.favorite);

        // enables or disables favorite button based on if it's set
        if (image.getisFavorite()) {
            favorite.setImageResource(R.drawable.like_enabled);
        } else {
            favorite.setImageResource(R.drawable.like_disabled);
        }

        // sets image
        imageView.setImageBitmap(image.getImage());
        // sets text
        title.setText(image.getName());
        // makes delete icon visible or not
        deleteIcon.setAlpha(layout ? 255 : 0);
        // makes favorite icon visible or not
        favorite.setAlpha(showFav ? 255 : 0);

        // sets image as favorited/unfavorited when clicked
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fav == false) {
                    favorite.setImageResource(R.drawable.like_enabled);
                    image.setIsFavorite(true);
                } else {
                    favorite.setImageResource(R.drawable.like_disabled);
                    image.setIsFavorite(false);
                }
            }
        });

        return convertView;
    }
}
