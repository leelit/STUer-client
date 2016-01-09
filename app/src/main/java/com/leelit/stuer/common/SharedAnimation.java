package com.leelit.stuer.common;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;

/**
 * Created by Leelit on 2016/1/9.
 */
public class SharedAnimation {

    public static void postScaleAnimation(View v) {
        ScaleAnimation scaleAnimation1 = new ScaleAnimation(1.0f, 0.5f, 1.0f, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation1.setDuration(200);
        ScaleAnimation scaleAnimation2 = new ScaleAnimation(0.5f, 1.5f, 0.5f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation2.setDuration(100);
        AnimationSet set = new AnimationSet(true);
        set.addAnimation(scaleAnimation1);
        set.addAnimation(scaleAnimation2);
        v.startAnimation(set);
    }
}
