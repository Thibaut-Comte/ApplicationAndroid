package com.jonathan.reaction;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

/**
 * Created by thibaut on 09/03/2018.
 */

public class Screenshot {

    public static Bitmap takeScreenShot(View v) {
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache(true);
        Bitmap b = Bitmap.createBitmap(v.getDrawingCache());
        v.setDrawingCacheEnabled(false);
        return b;
    }

    public static Bitmap takeScreenShotRootView (View v) {
        return takeScreenShot(v.getRootView());
    }
}
