package br.edu.ifma.icmbiodelta.Helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

public class ImageTool {

    public static String getBase64FromURI(Context context, Uri uri) {
        String base64Encoded = new String();
        InputStream inputStream;
        try {
            inputStream = context.getContentResolver().openInputStream(uri);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int bufferSize = 1024;
            byte[] buf = new byte[bufferSize];
            int leg = 0;
            while ((leg = inputStream.read(buf)) != -1) {
                byteArrayOutputStream.write(buf, 0, leg);
            }
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                base64Encoded = Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
            }
            return base64Encoded;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap getURIFromBase64(Context context, String base64) {
        Bitmap decodeByte = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                byte[] encodedString = Base64.getDecoder().decode(base64);
                decodeByte = BitmapFactory.decodeByteArray(encodedString,0,encodedString.length);
            }
            return decodeByte;
    }
}
