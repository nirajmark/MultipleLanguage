package com.company.multibhashi.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by niraj.markandey on 22/10/17.
 */

public class DataObject {
    String TAG = getClass().getName();
    private String type,conceptName,pronunciation,targetScript,audio_url;

    public DataObject(String type, String conceptName, String pronunciation, String targetScript, String audio_url) {
        this.type = type;
        this.conceptName = conceptName;
        this.pronunciation = pronunciation;
        this.targetScript = targetScript;
        this.audio_url = audio_url;
    }

    public DataObject(JSONObject dataObject) {
        try {
            this.type = dataObject.getString("type");
            this.conceptName = dataObject.getString("conceptName");
            this.pronunciation = dataObject.getString("pronunciation");
            this.targetScript = dataObject.getString("targetScript");
            this.audio_url = dataObject.getString("audio_url");
        } catch (JSONException e) {
            Log.d(TAG, "DataObject: error in parsing object");
        }


    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getConceptName() {
        return conceptName;
    }

    public void setConceptName(String conceptName) {
        this.conceptName = conceptName;
    }

    public String getPronunciation() {
        return pronunciation;
    }

    public void setPronunciation(String pronunciation) {
        this.pronunciation = pronunciation;
    }

    public String getTargetScript() {
        return targetScript;
    }

    public void setTargetScript(String targetScript) {
        this.targetScript = targetScript;
    }

    public String getAudio_url() {
        return audio_url;
    }

    public void setAudio_url(String audio_url) {
        this.audio_url = audio_url;
    }


}
