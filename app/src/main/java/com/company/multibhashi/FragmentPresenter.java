package com.company.multibhashi;

import com.company.multibhashi.model.DataObject;

import java.util.List;

/**
 * Created by niraj.markandey on 24/10/17.
 */

public interface FragmentPresenter {
    List<DataObject> parseData(String result, String type);

    String getFilenameFromUrl(String url);
}
