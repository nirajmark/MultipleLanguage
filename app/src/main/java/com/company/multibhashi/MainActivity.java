package com.company.multibhashi;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.company.multibhashi.constants.APIUrl;
import com.company.multibhashi.model.DataObject;
import com.company.multibhashi.services.ApiService;

import com.company.multibhashi.services.DownloadService;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainView{

    String TAG = getClass().getName();

    private Toolbar toolbar;

    MainPresenter presenter;
    LearnFragment learnFragment;
    QuestionFragment questionFragment;

    private  int currentCount;

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @BindView(R.id.tabs)
    TabLayout tabLayout;

    @BindView(R.id.progress)
    ProgressBar progress;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getRequiredPermission(this);

        learnFragment= new LearnFragment();
        questionFragment= new QuestionFragment();


        presenter = new MainPresenterImpl(this,learnFragment,questionFragment);

        callAPIService(APIUrl.GET_DATA);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


//        setUpViewPager();
//
//        tabLayout.setupWithViewPager(viewPager);
        
        setFAB();

    }

    private void getRequiredPermission(Activity activity) {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.RECORD_AUDIO},
                    0);
        }
    }

    private void setFAB() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                presenter.showNextTab();
                //downloading next file
                if (learnFragment.currentIndex+1<learnFragment.learnObjectList.size()){
                    presenter.downloadNextAudioFile(learnFragment.learnObjectList.get(learnFragment.currentIndex+1).getAudio_url(),getApplicationContext().getFilesDir());
                }else{
                    Log.d(TAG, "onClick: all are downloaded");
                }

            }
        });
    }


    @Override
    public void callAPIService(String url) {
        showProgress();
        Intent intent = new Intent(this, ApiService.class);
        intent.putExtra("url", url);
        intent.putExtra("REQUEST_METHOD", "GET");
        intent.putExtra("READ_TIMEOUT", 15000);
        intent.putExtra("CONNECTION_TIMEOUT", 15000);
        intent.putExtra("receiver", new DownloadReceiver(new Handler()));

        startService(intent);
    }

    @Override
    public void callDownLoadService(String url,String fileName) {
        Intent intent = new Intent(this, DownloadService.class);
        intent.putExtra("url", url); // Note: here put the url from where u want to download file
        intent.putExtra("fileName", fileName);
        intent.putExtra("receiver", new DownloadReceiver(new Handler()));
        startService(intent);
    }


    @Override
    public void setUpViewPagerWithData(String result) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        Bundle bundle=new Bundle();
        bundle.putString("result", result);

        learnFragment.setArguments(bundle);
        questionFragment.setArguments(bundle);

        adapter.addFragment(learnFragment, "learn");
        adapter.addFragment(questionFragment, "Question");

        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
    }



    @Override
    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progress.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showMessage(String message) {
        Log.d(TAG, "showMessage: message = "+message);
    }

    public class DownloadReceiver extends ResultReceiver {
        public DownloadReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            String status = resultData.getString("status");
            if (resultCode == DownloadService.UPDATE_PROGRESS) {
                Log.d(TAG, "onReceiveResult: status = "+status);
                if (status.equals(DownloadService.DONE_STATUS)){
                    hideProgress();
                }
            }else if (resultCode == ApiService.UPDATE_PROGRESS){

                String result = resultData.getString("result");
                if (status.equals(ApiService.DONE_STATUS)){

                    presenter.setupFragmentWithResult(result);
                    presenter.downloadCurrentAudioFile(learnFragment.learnObjectList.get(learnFragment.currentIndex).getAudio_url());
                    //downloading next file
                    if (learnFragment.currentIndex+1<learnFragment.learnObjectList.size()){
                        presenter.downloadNextAudioFile(learnFragment.learnObjectList.get(learnFragment.currentIndex+1).getAudio_url(),getApplicationContext().getFilesDir());
                    }

                }

            }
        }
    }
}
