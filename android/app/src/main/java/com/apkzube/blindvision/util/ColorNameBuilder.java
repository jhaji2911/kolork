package com.apkzube.blindvision.util;

import android.graphics.Color;
import android.util.Log;

import com.apkzube.blindvision.model.ColorBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Color Detection
 * Created by Vishal Nagvadiya on 24-05-2020.
 */
public class ColorNameBuilder {

    public ArrayList<ColorBean> initColor(InputStream is) throws JSONException {

        ArrayList<ColorBean> colorList = new ArrayList<>();

        JSONObject nameArray = new JSONObject(loadJSONFromFile(is));
        JSONArray colorArray = nameArray.getJSONArray("names");
        ColorBean colorBean;
        for (int i = 0; i < colorArray.length(); i++) {
            JSONArray colorLine = (JSONArray) colorArray.get(i);
            //colorBean = new ColorBean(colorLine.getString(1), "#"+colorLine.getString(0));
            colorBean = new ColorBean(colorLine.getString(1),
                    "#" + colorLine.getString(0),
                    colorLine.getInt(2),
                    colorLine.getInt(3),
                    colorLine.getInt(4),
                    colorLine.getInt(5),
                    colorLine.getInt(6),
                    colorLine.getInt(7)

            );
            colorList.add(colorBean);
        }

        return colorList;
        //Log.d("BlindVision", "initColor: "+colorList.size());
    }


    public static String loadJSONFromFile(InputStream is) {
        String json = null;
        try {
            // File file = new File("file:///android_asset/ntc.json");
            //InputStream is =
            int size = is.available();
            byte[] buffer = new byte[size];

            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            Log.d("BlindVision", "loadJSONFromFile: " + ex.getMessage());
        }
        return json;
    }


    public static int[] getHSLFromRGB(String colorHex) {
        float min, max, delta, h, s, l;

        float r= Color.red(Color.parseColor(colorHex))/255f;
        float g=Color.green(Color.parseColor(colorHex))/255f;
        float b=Color.blue(Color.parseColor(colorHex))/255f;

        min = Math.min(r, Math.min(g, b));
        max = Math.max(r, Math.max(g, b));
        delta = max - min;
        l = (min + max) / 2;

        s = 0;
        if (l > 0 && l < 1) {
            s = delta / (l < 0.5 ? (2 * l) : (2 - 2 * l));
        }

        h = 0;
        if (delta > 0) {
            if (max == r && max != g) h += (g - b) / delta;
            if (max == g && max != b) h += (2 + (b - r) / delta);
            if (max == b && max != r) h += (4 + (r - g) / delta);
            h /= 6;
        }

        int[] arr = new int[3];
        arr[0] = (int) (h * 255);
        arr[1] = (int) (s * 255);
        arr[2] = (int) (l * 255);
        return arr;
    }

    public String getColorName(ArrayList<ColorBean> colorList, String color) {
        String colorName = "";

        color.toUpperCase();
        if (color.length() < 3 || color.length() > 7) {
            return "Invalid Color:";
        }
        if (color.length() % 3 == 0) {
            color = "#" + color;
        }
        if (color.length() == 4) {
            color = "#" + color.substring(1, 1) + color.substring(1, 1) + color.substring(2, 1) + color.substring(2, 1) +
                    color.substring(3, 1) + color.substring(3, 1);
        }

        float ndf1 = 0, ndf2 = 0, ndf = 0;
        int cl = -1;
        float df = -1;

        int r= Color.red(Color.parseColor(color));
        int g= Color.green(Color.parseColor(color));
        int b= Color.blue(Color.parseColor(color));

        int arr[]=getHSLFromRGB(color);

        int h=arr[0];
        int s=arr[1];
        int l=arr[2];

        ColorBean colorBean;
        for (int i = 0; i < colorList.size(); i++) {

            colorBean=colorList.get(i);

            if (color == "#" + colorBean.getHexCode()) {
                return colorBean.getColorName();
            }

            ndf1 = (float) (Math.pow(r - colorList.get(i).getR(), 2) +
                    Math.pow(g - colorList.get(i).getG(), 2) +
                    Math.pow(b - colorList.get(i).getB(), 2));

            ndf2 = (float) (Math.pow(h - colorList.get(i).getH(), 2)
                    + Math.pow(s - colorList.get(i).getS(), 2) +
                    Math.pow(l - colorList.get(i).getL(), 2));
            ndf = ndf1 + ndf2 * 2;

            if (df < 0 || df > ndf) {
                df = ndf;
                cl = i;
            }
        }

        if(cl<0){
            colorName="Invalid Color Code";
        }else{
            colorName=colorList.get(cl).getColorName();
        }

        return colorName;
    }

}
