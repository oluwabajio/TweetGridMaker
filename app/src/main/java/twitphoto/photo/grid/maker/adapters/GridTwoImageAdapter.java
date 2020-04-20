package twitphoto.photo.grid.maker.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

public class GridTwoImageAdapter  extends BaseAdapter {

    private Context mContext;
    private ArrayList<Bitmap> smallimages;
    private int imageWidth, imageHeight;

    public GridTwoImageAdapter(Context c, ArrayList<Bitmap> images){

        mContext = c;

        smallimages = images;

        imageWidth = images.get(0).getWidth();

        imageHeight = images.get(0).getHeight();

    }


    public int getCount() {

        return smallimages.size();

    }

    public Object getItem(int position) {

        return smallimages.get(position);

    }

    public long getItemId(int position) {

        return position;

    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView image;

        if(convertView == null){
            image = new ImageView(mContext);
            image.setScaleType(ImageView.ScaleType.FIT_CENTER);
            image.setLayoutParams(new GridView.LayoutParams(imageWidth - 1 , imageHeight));
            image.setPadding(0, 0, 0, 0);

        }else{
            image = (ImageView) convertView;
        }
        image.setImageBitmap(smallimages.get(position));

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });


        return image;

    }

}

