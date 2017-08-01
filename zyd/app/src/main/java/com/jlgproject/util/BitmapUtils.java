package com.jlgproject.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 王锋 on 2017/7/4.
 */

public class BitmapUtils {

    public static List<Bitmap> getBitmap(List<String> urlList) {
        Bitmap bm = null;
        List<Bitmap> bitampList = new ArrayList<>();
        if (urlList != null && urlList.size() != 0) {
            for (int i = 0; i < urlList.size(); i++) {
                try {
                    URL iconUrl = new URL(urlList.get(i));
                    URLConnection conn = iconUrl.openConnection();
                    HttpURLConnection http = (HttpURLConnection) conn;

                    int length = http.getContentLength();

                    conn.connect();
                    // 获得图像的字符流
                    InputStream is = conn.getInputStream();
                    BufferedInputStream bis = new BufferedInputStream(is, length);
                    bm = BitmapFactory.decodeStream(bis);
                    bitampList.add(bm);
                    bis.close();
                    is.close();// 关闭流
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return bitampList;
    }


    public static ArrayList<String> saveBitmapToJpg(Context context, List<Bitmap> faceBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
            Log.i("+=+=+++===++++", "SD *****>> SD卡不存在");
        } else {
            Log.i("+=+=+++===++++", "SD *****>> SD卡 存在");
        }
        ArrayList<String> pathList = new ArrayList<>();
        for (int i = 0; i < faceBitmap.size(); i++) {
            // 创建图片保存目录
            File faceImgDir = new File(Environment.getExternalStorageDirectory(), "zyd");
            if (!faceImgDir.exists()) {
                faceImgDir.mkdir();
            }
            // 以系统时间命名文件
            String faceImgName = "forum_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
            File file = new File(faceImgDir, faceImgName);
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                faceBitmap.get(i).compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 保存后要扫描一下文件，及时更新到系统目录（一定要加绝对路径，这样才能更新）
            MediaScannerConnection.scanFile(context, new String[]{Environment.getExternalStorageDirectory() + File.separator + "zyd" + File.separator + faceImgName}, null, null);
            String str = (Environment.getExternalStorageDirectory() + File.separator + "zyd" + File.separator + faceImgName);
            pathList.add(str);
        }
        return pathList;
    }

}
