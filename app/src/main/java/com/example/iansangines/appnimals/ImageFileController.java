package com.example.iansangines.appnimals;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.util.Pair;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by iansangines on 18/04/2016.
 */
public class ImageFileController {
    private static File fullSizeDir;  //STATIC
    private static File thumbnailDir;


    public void ImageFileController(){


    }

    public void CreateDirectories (){
        fullSizeDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/../AppnimalsImages");
        thumbnailDir =  new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/../AppnimalsImages/Thumbnails");


        if(fullSizeDir.mkdir())Log.d("fullsizeDir", "created with path: " + fullSizeDir.getPath() );

        else if(fullSizeDir.exists()) Log.d("fullsizeDir", "exists with path: " +fullSizeDir.getPath());

        if(thumbnailDir.mkdir()) Log.d("thumbnailDir", "created with path: " + thumbnailDir.getPath());
        else if(thumbnailDir.exists()) Log.d("thumbnailDir", "exists with path: " + thumbnailDir.getPath());
    }

    public String getFullSizePath(){
        String imageName = new SimpleDateFormat("ddMMyyy_HHmmss").format(new Date());
        File fullSizeImage = new File(fullSizeDir + imageName + ".jpg");
        return fullSizeImage.getPath();
    }

    public String saveFullSizeImage (Bitmap imageBitmap){

        String imageName = new SimpleDateFormat("ddMMyyy_HHmmss").format(new Date());
        File fullSizeImage = new File(fullSizeDir + imageName + ".png");

        try {
            FileOutputStream image = new FileOutputStream(fullSizeImage);

            imageBitmap.compress(Bitmap.CompressFormat.JPEG,100,image);
            image.flush();
            image.close();
            return fullSizeImage.getPath();
        }
        catch (IOException x){
            System.err.format("IOException %s%n",x);
            return null;
        }

    }

    public Pair<String,Bitmap> saveThumbnailImage (Bitmap imageBitmap){

        String imageName = new SimpleDateFormat("ddMMyyy_HHmmss").format(new Date()) + "_thumbnail";
        File thumbnailImage = new File(thumbnailDir + imageName + ".jpg");

        Log.d("THUMBNAILIMAGEPATH", thumbnailImage.getPath());
        Log.d("THUMBNAILIMAGEPATH", thumbnailImage.getPath());

        try {
            FileOutputStream image = new FileOutputStream(thumbnailImage);
            Bitmap scaled = Bitmap.createScaledBitmap(imageBitmap, imageBitmap.getWidth()/10, imageBitmap.getHeight()/10 - 10, true);
            scaled.compress(Bitmap.CompressFormat.PNG, 80, image);
            image.flush();
            image.close();
            return new Pair<>(thumbnailImage.getPath(),scaled);
        }
        catch (IOException x){
            System.err.format("IOException %s%n",x);
            return null;
        }

    }

    public boolean deleteImageFile (String path){
        File fileToDelete = new File(path);
        return fileToDelete.delete();
    }

}
