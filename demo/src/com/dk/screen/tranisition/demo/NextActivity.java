package com.dk.screen.tranisition.demo;

import com.dk.screen.tranisition.MoveAndScaleTransition;
import com.dk.screen.tranisition.R;
import com.dk.screen.tranisition.TransitionTool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class NextActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        TransitionTool.getInstance().prepareAnimation(this, findViewById(R.id.card));
        TransitionTool.getInstance().animate(this, findViewById(R.id.card), 1000);

        findViewById(R.id.weibo_avatar).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NextActivity.this, ThirdActivity.class);
                TransitionTool.getInstance().setTransition(new MoveAndScaleTransition());
                TransitionTool.getInstance().startActivity(NextActivity.this, view, intent);
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

       
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        TransitionTool.getInstance().prepareAnimation(this, findViewById(R.id.weibo_avatar));
        TransitionTool.getInstance().animate(this, findViewById(R.id.weibo_avatar), 1000);
    }
}
