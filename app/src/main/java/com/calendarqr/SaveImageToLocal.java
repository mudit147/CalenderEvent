package com.calendarqr;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SaveImageToLocal {

    public static boolean saveImageToGallery(Context context, Bitmap bmp) {
        File storePath = Environment.getExternalStorageDirectory();
        // Make a new path for all our QR codes to be saved
        File appDir = new File(storePath+"/CalenderQR/");

        if (!appDir.exists()) {
            appDir.mkdirs();
        }
        // Make a png file
        String fileName = System.currentTimeMillis() + ".png";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            //Compress and save pictures by io stream
            boolean saved = bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();

            // Update the database by sending broadcast notifications after saving pictures
            Uri uri = Uri.fromFile(file);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            if (saved)return true;
            else return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
