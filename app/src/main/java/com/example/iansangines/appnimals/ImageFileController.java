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


    public ImageFileController(){
        fullSizeDir = null;
        thumbnailDir = null;
    }

    public void CreateDirectories (){
        fullSizeDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/../AppnimalsImages");
        thumbnailDir =  new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/../AppnimalsImages/Thumbnails");


        if(fullSizeDir.mkdir())Log.d("fullsizeDir", "created with path: " + fullSizeDir.getPath() );

        else if(fullSizeDir.exists()) Log.d("fullsizeDir", "exists with path: " +fullSizeDir.getPath());

        if(thumbnailDir.mkdir()) Log.d("thumbnailDir", "created with path: " + thumbnailDir.getPath());
        else if(thumbnailDir.exists()) Log.d("thumbnailDir", "exists with path: " + thumbnailDir.getPath());
    }

    public File getFullSizeFile(){
        String imageName = new SimpleDateFormat("ddMMyyy_HHmmss").format(new Date());
        File fullSizeImage = new File(fullSizeDir + imageName + ".jpg");
        return fullSizeImage;
    }

    public File getThumbnailFile(){
        String imageName = new SimpleDateFormat("ddMMyyy_HHmmss").format(new Date());
        File fullSizeImage = new File(thumbnailDir + imageName+ "_thumbnail" + ".jpg");
        return fullSizeImage;
    }

    public void saveFullSizeImage (Bitmap imageBitmap, File fullSizeFile){

        try {
            FileOutputStream image = new FileOutputStream(fullSizeFile);

            imageBitmap.compress(Bitmap.CompressFormat.JPEG,100,image);
            image.flush();
            image.close();
        }
        catch (IOException x){
            System.err.format("IOException %s%n",x);
        }

    }

    public Bitmap saveThumbnailImage (Bitmap imageBitmap, File thumbnailFile){

        try {
            FileOutputStream image = new FileOutputStream(thumbnailFile);
            Bitmap scaled = Bitmap.createScaledBitmap(imageBitmap, imageBitmap.getWidth()/10, imageBitmap.getHeight()/10 - 10, true);
            scaled.compress(Bitmap.CompressFormat.PNG, 80, image);
            image.flush();
            image.close();
            return scaled;
        }
        catch (IOException x){
            System.err.format("IOException %s%n",x);
            return null;
        }

    }

    public boolean deleteImageFile (File imageFile){
        return imageFile.delete();
    }

}
