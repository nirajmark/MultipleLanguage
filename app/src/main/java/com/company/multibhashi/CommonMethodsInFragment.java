package com.company.multibhashi;

import android.content.Context;
import android.os.Bundle;
import android.widget.ViewSwitcher;

/**
 * Created by niraj.markandey on 25/10/17.
 */

public interface CommonMethodsInFragment {
    String getResultFromBundle(Bundle bundle);

    ViewSwitcher.ViewFactory getViewFactory(Context context);
}
