package com.company.multibhashi;

import com.company.multibhashi.model.DataObject;

import java.io.File;
import java.util.List;

/**
 * Created by niraj.markandey on 22/10/17.
 */

public interface MainPresenter {
    void downloadCurrentAudioFile(String url);

    void downloadNextAudioFile(String url,File basePath);

    void getCurrentData(List<DataObject> dataList);

    List<DataObject> getListOfData();

//    List<DataObject> parseData(String result,String type);

    void showNextTab();

    void setupFragmentWithResult(String result);

    String getFilenameFromUrl(String url);

}
