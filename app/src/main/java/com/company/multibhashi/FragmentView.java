package com.company.multibhashi;

/**
 * Created by niraj.markandey on 22/10/17.
 */

public interface FragmentView {


    void showNext();

    void showProgress();

    void hideProgress();

    public interface LearnFragmentView{
        void playAudio(String filename);
    }

    public interface QuestionFragmentView{
        void startRecording(String filename);
        void stopRecording();
    }

}
