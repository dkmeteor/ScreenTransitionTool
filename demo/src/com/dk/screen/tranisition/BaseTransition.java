package com.dk.screen.tranisition;

import android.app.Activity;
import android.view.View;

public abstract class BaseTransition {
    public abstract void prepare(View sharedElement, Activity currActivity);

    public abstract void clean(Activity activity);

    public abstract void cancel();

    public abstract void animate(final Activity destActivity, View sharedElement, final int duration);

    public abstract void prepareAnimation(Activity destActivity, View sharedElement);
}
