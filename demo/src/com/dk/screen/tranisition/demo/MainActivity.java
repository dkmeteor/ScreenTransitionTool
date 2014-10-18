package com.dk.screen.tranisition.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.dk.screen.tranisition.MoveAndScaleTransition;
import com.dk.screen.tranisition.R;
import com.dk.screen.tranisition.TransitionTool;

public class MainActivity extends Activity {

    private ListView mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_2);

        mList = (ListView)findViewById(R.id.main_list);
        mList.setAdapter(new WeiboAdapter());
        mList.setOnItemClickListener(mOnItemClickListener);

    }

    private AdapterView.OnItemClickListener mOnItemClickListener = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(MainActivity.this, NextActivity.class);
            TransitionTool.getInstance().setTransition(new MoveAndScaleTransition());
            TransitionTool.getInstance().startActivity(MainActivity.this, view, intent);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    class WeiboAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 100;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.view_list_item, null);
            }

            return convertView;
        }

    }
}
