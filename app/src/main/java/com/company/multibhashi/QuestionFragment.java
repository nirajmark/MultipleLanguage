package com.company.multibhashi;


import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextSwitcher;

import com.company.multibhashi.model.DataObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by niraj.markandey on 22/10/17.
 */

public class QuestionFragment extends Fragment implements FragmentView, FragmentView.QuestionFragmentView {

    String TAG = getClass().getName();
    private MediaRecorder recorder = null;

    @BindView(R.id.concept_name_tv)
    TextSwitcher conceptName;
    @BindView(R.id.pronunciation_tv) TextSwitcher pronunciation;

    @BindView(R.id.play)
    ImageButton record;

    String result;
    List<DataObject> questionObjectList;
    FragmentPresenter fragPresenter;
    CommonMethodsInFragment commonMethods;
    private String filename;

    int currentIndex=-1;
    final private String type = "question,quiz";

    private int currentFormat = 0;
    private int output_formats[] = { MediaRecorder.OutputFormat.MPEG_4};
    private String file_exts[] = { ".mp4"};

    public QuestionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        commonMethods = new CommonMethodsInFragmentImpl();
        result = commonMethods.getResultFromBundle(getArguments());
        questionObjectList = new ArrayList<DataObject>();

        fragPresenter = new FragmentPresenterImpl(this);
        questionObjectList = fragPresenter.parseData(result,type);

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_learn, container, false);
        ButterKnife.bind(this, rootView);
        record.setImageResource(R.mipmap.record);

        conceptName.setFactory(commonMethods.getViewFactory(getContext()));
        pronunciation.setFactory(commonMethods.getViewFactory(getContext()));

        record.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        Log.d(TAG, "onTouch: Starting Recording");
                        startRecording(filename);
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.d(TAG, "onTouch: Stopping Recording");
                        stopRecording();
                        break;
                }
                return false;
            }
        });
        showNext();

        return rootView;
    }

    public void showNext() {
        currentIndex++;
        if(currentIndex==questionObjectList.size())
            currentIndex=0;
        conceptName.setText(questionObjectList.get(currentIndex).getConceptName());
        pronunciation.setText(questionObjectList.get(currentIndex).getPronunciation() + " ("+ questionObjectList.get(currentIndex).getTargetScript()+" )");
//        script.setText(questionObjectList.get(currentIndex).getPronunciation());
        filename = questionObjectList.get(currentIndex).getConceptName();


    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    private String getFilename(){
        String filepath = getContext().getFilesDir()+"/recording"+file_exts[currentFormat];
        return filepath;
    }



    @Override
    public void startRecording(String filename) {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(output_formats[currentFormat]);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(getFilename());
        recorder.setOnErrorListener(errorListener);
        recorder.setOnInfoListener(infoListener);


        try {
            recorder.prepare();
            recorder.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private MediaRecorder.OnErrorListener errorListener = new MediaRecorder.OnErrorListener() {
        @Override
        public void onError(MediaRecorder mr, int what, int extra) {
            Log.d(TAG, "Error: " + what + ", " + extra);
        }
    };

    private MediaRecorder.OnInfoListener infoListener = new MediaRecorder.OnInfoListener() {
        @Override
        public void onInfo(MediaRecorder mr, int what, int extra) {
            Log.d(TAG, "Warning: " + what + ", " + extra);
        }
    };

    @Override
    public void stopRecording() {
        if(null != recorder){
            recorder.stop();
            recorder.reset();
            recorder.release();

            recorder = null;
        }
    }
}
