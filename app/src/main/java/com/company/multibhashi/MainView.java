package com.company.multibhashi;

import android.support.v4.view.ViewPager;

import com.company.multibhashi.model.DataObject;

/**
 * Created by niraj.markandey on 22/10/17.
 */

public interface MainView {
    void callAPIService(String url);

    void callDownLoadService(String url,String fileName);

    void setUpViewPagerWithData(String result);

    void showProgress();

    void hideProgress();

    void showMessage(String message);


}
