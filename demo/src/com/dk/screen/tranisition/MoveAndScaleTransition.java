package com.dk.screen.tranisition;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;

public class MoveAndScaleTransition extends BaseTransition {
    // private View mSharedElement;
    // private View mDecorView;
    private Bitmap mBitmapDecor;
    private Bitmap mBitmapSharedElement;
    // if has actionbar,the top is actionbar height
    private ImageView mDecoreImage;
    private ImageView mSharedElementImage;
    private RelativeLayout mContainer;
    private int top;
    private int srcX;
    private int srcY;

    @Override
    public void prepare(View sharedElement, Activity currActivity) {
        View root = currActivity.getWindow().getDecorView().findViewById(android.R.id.content);
        top = root.getTop();
        sharedElement.setDrawingCacheEnabled(true);
        mBitmapSharedElement = sharedElement.getDrawingCache();

        sharedElement.setVisibility(View.INVISIBLE);
        root.setDrawingCacheEnabled(true);
        mBitmapDecor = root.getDrawingCache();
        sharedElement.setVisibility(View.VISIBLE);

        int[] locations = new int[2];
        sharedElement.getLocationOnScreen(locations);
        srcX = locations[0];
        srcY = locations[1];
    }

    @Override
    public void clean(Activity activity) {
        if (mDecoreImage != null) {
            try {
                // Use removeViewImmediate,removeView will cause screen flash
                activity.getWindowManager().removeViewImmediate(mDecoreImage);
            } catch (Exception ignored) {
            }
        }

        if (mContainer != null) {
            try {
                // Do not use removeViewImmediate,it will make screen flash.
                // Notice : It is not same issue with before.
                activity.getWindowManager().removeView(mContainer);
            } catch (Exception ignored) {
            }
        }

        // clean all data
        mDecoreImage = null;
        mSharedElementImage = null;
        mBitmapDecor = null;
        mBitmapSharedElement = null;
        mContainer = null;
    }

    @Override
    public void cancel() {

    }

    @Override
    public void prepareAnimation(Activity destActivity, View sharedElement) {

        mDecoreImage = createImageView(destActivity, mBitmapDecor, 0, 0 + top);
        mContainer = new RelativeLayout(destActivity);
        mSharedElementImage = createImageView(destActivity, mBitmapSharedElement, srcX, srcY, mContainer);
    }

    private void animateImmediate(final Activity destActivity, final View sharedElement, final int duration) {
        sharedElement.setVisibility(View.INVISIBLE);

        int[] dest = new int[2];
        sharedElement.getLocationOnScreen(dest);

        float destWidth = sharedElement.getWidth();
        float destHeight = sharedElement.getHeight();

        float srcWidth = mBitmapSharedElement.getWidth();
        float srcHeight = mBitmapSharedElement.getHeight();

        float scaleX = destWidth / srcWidth;
        float scaleY = destHeight / srcHeight;

        float moveX = dest[0] + (destWidth - srcWidth) / 2f;
        float moveY = dest[1] + (destHeight - srcHeight) / 2f;

        ViewCompat.animate(mDecoreImage).alpha(0f).setDuration(500).setStartDelay(0);
        ViewCompat.animate(mSharedElementImage).translationX(moveX).translationY(moveY).scaleX(scaleX).scaleY(scaleY).setDuration(500)
                .setListener(new ViewPropertyAnimatorListener() {

                    @Override
                    public void onAnimationStart(View arg0) {

                    }

                    @Override
                    public void onAnimationEnd(View arg0) {
                        sharedElement.setVisibility(View.VISIBLE);
                        clean(destActivity);
                    }

                    @Override
                    public void onAnimationCancel(View arg0) {

                    }
                });

    }

    @Override
    public void animate(final Activity destActivity, final View sharedElement, final int duration) {
        if (sharedElement.getWidth() > 0 && sharedElement.getHeight() > 0) {

            animateImmediate(destActivity, sharedElement, duration);
        } else {
            sharedElement.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

                @Override
                public void onGlobalLayout() {
                    animateImmediate(destActivity, sharedElement, duration);
                    sharedElement.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            });
        }

    }

    private ImageView createImageView(Activity destActivity, Bitmap bmp, int x, int y) {
        ImageView imageView = new ImageView(destActivity);
        imageView.setImageBitmap(bmp);
        imageView.setScaleType(ScaleType.FIT_XY);
        WindowManager.LayoutParams windowParams = new WindowManager.LayoutParams();
        windowParams.gravity = Gravity.TOP;
        windowParams.x = x;
        windowParams.y = y;
        windowParams.height = bmp.getHeight();
        windowParams.width = bmp.getWidth();
        windowParams.flags = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        windowParams.format = PixelFormat.TRANSLUCENT;
        windowParams.windowAnimations = 0;
        destActivity.getWindowManager().addView(imageView, windowParams);
        return imageView;
    }

    private ImageView createImageView(Activity destActivity, Bitmap bmp, int x, int y, RelativeLayout container) {
        ImageView imageView = new ImageView(destActivity);
        imageView.setImageBitmap(bmp);
        imageView.setScaleType(ScaleType.FIT_XY);

        // get screen resolution
        DisplayMetrics dm = new DisplayMetrics();
        destActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);

        WindowManager.LayoutParams windowParams = new WindowManager.LayoutParams();
        windowParams.gravity = Gravity.TOP;
        windowParams.height = dm.heightPixels;
        windowParams.width = dm.widthPixels;
        windowParams.flags = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        windowParams.format = PixelFormat.TRANSLUCENT;
        windowParams.windowAnimations = 0;

        mContainer.addView(imageView);
        imageView.setTranslationX(x);
        imageView.setTranslationY(y);
        destActivity.getWindowManager().addView(mContainer, windowParams);
        return imageView;
    }

}
