package com.dk.screen.tranisition.demo;

import com.dk.screen.tranisition.MoveAndScaleTransition;
import com.dk.screen.tranisition.R;
import com.dk.screen.tranisition.TransitionTool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class ThirdActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        TransitionTool.getInstance().prepareAnimation(this, findViewById(R.id.target));
        TransitionTool.getInstance().animate(this, findViewById(R.id.target), 1000);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ThirdActivity.this, NextActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        TransitionTool.getInstance().setTransition(new MoveAndScaleTransition());
        TransitionTool.getInstance().startActivity(ThirdActivity.this, findViewById(R.id.target), intent);

        finish();
        overridePendingTransition(R.anim.no, R.anim.no);
    }
}
