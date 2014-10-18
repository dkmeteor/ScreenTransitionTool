package com.dk.screen.tranisition;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

public class TransitionTool {
    private static TransitionTool mTransitionTool;
    private BaseTransition mTransition;

    private TransitionTool() {

    }

    public void setTransition(BaseTransition transition) {
        mTransition = transition;
    }

    public static TransitionTool getInstance() {
        if (mTransitionTool == null) {
            mTransitionTool = new TransitionTool();
        }
        return mTransitionTool;
    }

    public void startActivity(Activity currActivity, View sharedElement, Intent intent) {
        if (mTransition == null) {
            throw new RuntimeException("You must call setTransition() before prepare. ");
        }
        // Preparing the bitmaps that we need to show
        mTransition.prepare(sharedElement, currActivity);
        currActivity.startActivityForResult(intent, 0);
        currActivity.overridePendingTransition(R.anim.no, R.anim.no);
    }

    public void animate(final Activity destActivity, View sharedElement, final int duration) {
        mTransition.animate(destActivity, sharedElement, duration);
    }

    public void cancel(final Activity destActivity) {
        mTransition.cancel();
        mTransition.clean(destActivity);
    }

    public void prepareAnimation(final Activity destActivity, View sharedElement) {
        mTransition.prepareAnimation(destActivity, sharedElement);
    }
}
