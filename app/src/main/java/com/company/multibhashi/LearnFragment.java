package com.company.multibhashi;


import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.company.multibhashi.model.DataObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.ContentValues.TAG;

/**
 * Created by niraj.markandey on 22/10/17.
 */

public class LearnFragment extends Fragment implements FragmentView, FragmentView.LearnFragmentView {

    @BindView(R.id.concept_name_tv) TextSwitcher conceptName;
    @BindView(R.id.pronunciation_tv) TextSwitcher pronunciation;

    @BindView(R.id.play)
    ImageButton play;

    String result;
    List<DataObject> learnObjectList;
    FragmentPresenter fragPresenter;
    CommonMethodsInFragment commonMethods;
    String filename;

    int currentIndex=-1;
    final private String type = "learn";



    public LearnFragment() {
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
        learnObjectList = new ArrayList<DataObject>();

        //getting learnObject from presenter

        fragPresenter = new FragmentPresenterImpl(this);
        learnObjectList = fragPresenter.parseData(result,type);

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_learn, container, false);
        ButterKnife.bind(this, rootView);

        conceptName.setFactory(commonMethods.getViewFactory(getContext()));

        this.pronunciation.setFactory(commonMethods.getViewFactory(getContext()));

        showNext();

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAudio(filename);
            }
        });



        return rootView;
    }


    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    public void showNext() {
        currentIndex++;
        if(currentIndex==learnObjectList.size())
            currentIndex=0;
        conceptName.setText(learnObjectList.get(currentIndex).getConceptName());
        pronunciation.setText(learnObjectList.get(currentIndex).getPronunciation() + " ("+ learnObjectList.get(currentIndex).getTargetScript()+" )");
//        script.setText(learnObjectList.get(currentIndex).getTargetScript());
        filename = fragPresenter.getFilenameFromUrl(learnObjectList.get(currentIndex).getAudio_url());
        Log.d(TAG, "showNext: 1)");
    }

    @Override
    public void playAudio(String filename) {
//        filename = "recording.mp4";
        MediaPlayer mediaPlayer = MediaPlayer.create(getContext(), Uri.parse(getContext().getFilesDir()+"/"+filename));
        mediaPlayer.start();
    }

}
