package com.company.multibhashi;

import android.util.Log;

import com.company.multibhashi.model.DataObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by niraj.markandey on 23/10/17.
 */

public class MainPresenterImpl implements MainPresenter {
    String TAG = getClass().getName();

    MainView mainView;
    FragmentView learnFrag,questionFrag;

    CommonPresenterMethods commonPresenterMethod = new CommonPresenterMethodImpl();

    public MainPresenterImpl(MainView mainView,FragmentView learnFrag,FragmentView questionFrag) {
        this.mainView = mainView;
        this.learnFrag = learnFrag;
        this.questionFrag = questionFrag;
    }

    @Override
    public void downloadCurrentAudioFile(String url) {
        Log.d(TAG, "downloadCurrentAudioFile: 2)");
        mainView.callDownLoadService(url,getFilenameFromUrl(url));
    }

    @Override
    public void downloadNextAudioFile(String url, File basePath) {
        String filename = getFilenameFromUrl(url);
        ///  check if filename presents
        if (checkIfFileIsPresent(filename,basePath)){
            //file already present
        }else{
            mainView.callDownLoadService(url,filename);
        }

    }

    private boolean checkIfFileIsPresent(String filename,File basePath) {
        String filepath = basePath+"/"+filename;
        Log.d(TAG, "checkIfFileIsPresent: filepath = "+filepath);
        File file = new File(filepath);
        if(file.exists()){
            Log.d(TAG, "checkIfFileIsPresent: above file is present");
            return true;
        } else {
            return false;
        }

    }

    @Override
    public void getCurrentData(List<DataObject> dataList) {

    }

    @Override
    public List<DataObject> getListOfData() {
        return null;
    }



    @Override
    public void showNextTab() {
        learnFrag.showNext();
        questionFrag.showNext();
    }

    @Override
    public void setupFragmentWithResult(String result) {

        mainView.setUpViewPagerWithData(result);
    }

    @Override
    public String getFilenameFromUrl(String url) {
        return commonPresenterMethod.getFilenameFromUrl(url);
    }


}
