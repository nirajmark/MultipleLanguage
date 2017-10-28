package com.company.multibhashi;

import android.util.Log;

/**
 * Created by niraj.markandey on 25/10/17.
 */

public class CommonPresenterMethodImpl implements CommonPresenterMethods {
    private String TAG = getClass().getName();
    @Override
    public String getFilenameFromUrl(String url) {
        String filename;
        Log.d(TAG, "getFilenameFromUrl: url = "+url);
        String[] urlSplitArray = url.split("/");
        filename = urlSplitArray[urlSplitArray.length-1];
        Log.d(TAG, "getFilenameFromUrl: filename = "+filename);
        return filename;
    }
}
