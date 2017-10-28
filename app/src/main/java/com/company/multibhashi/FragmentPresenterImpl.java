package com.company.multibhashi;

import android.util.Log;

import com.company.multibhashi.model.DataObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by niraj.markandey on 24/10/17.
 */

public class FragmentPresenterImpl implements FragmentPresenter {
    private String TAG = getClass().getName();
    FragmentView fragView;
    CommonPresenterMethods commonPresenterMethod = new CommonPresenterMethodImpl();

    public FragmentPresenterImpl(FragmentView fragview) {
        this.fragView = fragview;
    }

    @Override
    public List<DataObject> parseData(String result, String type) {
        Log.d(TAG, "parseData: result = "+result);
        List<DataObject> dataObjectList = new ArrayList<DataObject>();


        try {
            JSONObject resultObject = new JSONObject(result);

            JSONArray lessonDataArray = resultObject.getJSONArray("lesson_data");
            for (int i=0;i<lessonDataArray.length();i++){
                JSONObject dataObject = (JSONObject) lessonDataArray.get(i);
                if (type.contains(",")){
                    String[] typeList = type.split(",");
                    for (int j=0;j<typeList.length;j++){
                        if (dataObject.getString("type").equalsIgnoreCase(typeList[j])){
                            Log.d(TAG, "parseData: added  = "+ dataObject.getString("conceptName"));
                            dataObjectList.add(new DataObject(dataObject));
                        }
                    }
                }else{
                    if (dataObject.getString("type").equalsIgnoreCase(type)){
                        Log.d(TAG, "parseData: added  = "+ dataObject.getString("conceptName"));
                        dataObjectList.add(new DataObject(dataObject));
                    }
                }

            }



        } catch (JSONException e) {
            Log.d(TAG, "parseData: result is not parcable");
            //todo: show error msg in view
        }

        return dataObjectList; //can be null in case of exception //todo : handle null case


    }

    @Override
    public String getFilenameFromUrl(String url) {
        return commonPresenterMethod.getFilenameFromUrl(url);
    }


}
