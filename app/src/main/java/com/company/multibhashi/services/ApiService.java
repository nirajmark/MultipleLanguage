package com.company.multibhashi.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import android.util.Log;

import com.company.multibhashi.MainPresenter;
import com.company.multibhashi.MainPresenterImpl;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by niraj.markandey on 22/10/17.
 */

public class ApiService extends IntentService {

    public static final int UPDATE_PROGRESS = 8345;
    public static final String DONE_STATUS = "DONE";
    String TAG = getClass().getName();
    public ApiService() {
        super("APIService");

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String urlToCall = intent.getStringExtra("url");
        Log.d(TAG, "onHandleIntent: url = "+urlToCall);
        String REQUEST_METHOD = intent.getStringExtra("REQUEST_METHOD");
        int READ_TIMEOUT = intent.getIntExtra("READ_TIMEOUT",15000);
        int CONNECTION_TIMEOUT = intent.getIntExtra("CONNECTION_TIMEOUT",15000);
        ResultReceiver receiver = (ResultReceiver) intent.getParcelableExtra("receiver");

        String result;
        String inputLine;
        try {
            URL url = new URL(urlToCall);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(REQUEST_METHOD);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);

            connection.connect();
            //Create a new InputStreamReader
            InputStreamReader streamReader = new
                    InputStreamReader(connection.getInputStream());
            //Create a new buffered reader and String Builder
            BufferedReader reader = new BufferedReader(streamReader);
            StringBuilder stringBuilder = new StringBuilder();
            //Check if the line we are reading is not null
            while((inputLine = reader.readLine()) != null){
                stringBuilder.append(inputLine);
            }
            //Close our InputStream and Buffered reader
            reader.close();
            streamReader.close();
            //Set our result equal to our stringBuilder
            result = stringBuilder.toString();

            Log.d(TAG, "onHandleIntent: result = "+result);

            Bundle resultData = new Bundle();
            resultData.putString("result" ,result);
            resultData.putString("status" ,DONE_STATUS);
            receiver.send(UPDATE_PROGRESS, resultData);






        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
