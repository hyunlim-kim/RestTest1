package com.example.hyunlim.resttest1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by qnqnqn1239 on 2017. 7. 25..
 */

class ImageLoaderTask extends AsyncTask<String ,Void, Bitmap> {

    private ImageView ProfileImg;

    public ImageLoaderTask(ImageView ProfileImg){
        this.ProfileImg = ProfileImg;

    }

    @Override
    protected Bitmap doInBackground(String... params) {
        String imgUrl = params[0];

        Bitmap bmp = null;

        try{
            bmp = BitmapFactory.decodeStream((InputStream)new URL(imgUrl).getContent());
        }catch (Exception e){
            e.printStackTrace();
        }
        return bmp;
    }//end doInBackground

    @Override
    protected void onPostExecute(Bitmap bitmap){
        //표시
        if(bitmap !=null) {
            ProfileImg.setImageBitmap( bitmap );
        }
    }//end onPostExecute

} //end ImageLoaderTask