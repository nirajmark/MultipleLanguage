package com.company.multibhashi;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.ViewSwitcher;

/**
 * Created by niraj.markandey on 25/10/17.
 */

public class CommonMethodsInFragmentImpl implements CommonMethodsInFragment {
    @Override
    public String getResultFromBundle(Bundle bundle) {
        String result;
        try{
            result = bundle.getString("result");
        }catch (Exception e){
            result = null;
            //todo deal with this exception
        }

        return result;
    }

    @Override
    public ViewSwitcher.ViewFactory getViewFactory(final Context context) {
        ViewSwitcher.ViewFactory newViewFactory = new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                // create new textView and set the properties like clolr, size etc
                TextView myText = new TextView(context);
                myText.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
                myText.setTextSize(30);
                myText.setTextColor(Color.BLACK);
                return myText;
            }
        };
        return newViewFactory;
    }


}
