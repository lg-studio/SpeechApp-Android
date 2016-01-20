package com.tas.speech.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by drago_000 on 03/08/2015.
 */
public class DataSources {

    public static String getJSONStr(Context ctx, String jsonPath) {
        BufferedReader reader = null;
        StringBuilder jsonStr = new StringBuilder();

        try {
            reader = new BufferedReader(
                    new InputStreamReader(ctx.getAssets().open(jsonPath), "UTF-8"));

            // do reading, usually loop until end of file reading
            String mLine = reader.readLine();
            while (mLine != null) {
                //process line
                jsonStr.append(mLine);
                mLine = reader.readLine();
            }
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }

        return jsonStr.toString();
    }
}
