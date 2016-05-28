package com.example.iansangines.appnimals.Controllers;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Build;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

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


    public ImageFileController() {
        fullSizeDir = null;
        thumbnailDir = null;
    }

    public void CreateDirectories() {
        fullSizeDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/../AppnimalsImages/");
        thumbnailDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/../AppnimalsImages/Thumbnails/");


        if (fullSizeDir.mkdir())
            Log.d("fullsizeDir", "created with path: " + fullSizeDir.getPath());

        else if (fullSizeDir.exists())
            Log.d("fullsizeDir", "exists with path: " + fullSizeDir.getPath());
    }

    public File getFullSizeFile() {
        String imageName = new SimpleDateFormat("ddMMyyy_HHmmss").format(new Date());
        File fullSizeImage = new File(fullSizeDir, imageName + ".jpg");
        return fullSizeImage;
    }

    public File getThumbnailFile() {
        String imageName = new SimpleDateFormat("ddMMyyy_HHmmss").format(new Date());
        File fullSizeImage = new File(thumbnailDir, "_thumbnail" + imageName + ".jpg");
        return fullSizeImage;
    }

    public void saveFullSizeImage(Bitmap imageBitmap, File fullSizeFile) {

        try {
            FileOutputStream image = new FileOutputStream(fullSizeFile);

            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 70, image);
            image.flush();
            image.close();
        } catch (IOException x) {
            System.err.format("IOException %s%n", x);
        }

    }

    public Bitmap saveThumbnailImage(Bitmap imageBitmap, File thumbnailFile) {

        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;

        try {


            FileOutputStream image = new FileOutputStream(thumbnailFile);
            if (height / 5 < imageBitmap.getHeight() && width / 5 - 10 < imageBitmap.getWidth()) {
                Bitmap scaled = Bitmap.createScaledBitmap(imageBitmap, imageBitmap.getWidth() / 10, imageBitmap.getHeight() / 10 - 10, true);
                scaled.compress(Bitmap.CompressFormat.PNG, 75, image);
                image.flush();
                image.close();
                return scaled;
            } else {
                imageBitmap.compress(Bitmap.CompressFormat.PNG, 75, image);
                return imageBitmap;
            }
        } catch (IOException x) {
            System.err.format("IOException %s%n", x);
        }
        return null;
    }

    public boolean deleteImageFile(File imageFile) {
        return imageFile.delete();
    }


}
