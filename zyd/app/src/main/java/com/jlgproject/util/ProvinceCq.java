package com.jlgproject.util;

import android.content.res.AssetManager;

import com.jlgproject.App;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author 王锋 on 2017/7/5.
 */

public class ProvinceCq {

    public static String InitData() {
        StringBuffer sb = new StringBuffer();
        AssetManager mAssetManager = App.getContext().getAssets();
        try {
            InputStream is = mAssetManager.open("address.json");
            byte[] data = new byte[is.available()];
            int len = -1;
            while ((len = is.read(data)) != -1) {
                sb.append(new String(data, 0, len, "utf-8"));
            }
            is.close();
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();

        }
        return null;
    }



}
