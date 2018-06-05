package com.example.yinlongquan.pulldownview;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.younchen.pulldownmenu.DragContainer;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DragContainer mDragContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("adsfasdf");
        }

        mDragContainer = findViewById(R.id.drag_container);
        ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(new ListAdapter(this, list));

        View btn = findViewById(R.id.btn_close);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDragContainer.closeContentView();
            }
        });
    }

    class ListAdapter extends BaseAdapter {


        private final ArrayList<String> mList;
        private Context context;

        public ListAdapter(MainActivity mainActivity, ArrayList<String> list) {
            this.mList = list;
            this.context = mainActivity;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public String getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.list_item, null, false);
            }
            TextView textView = convertView.findViewById(R.id.txt);
            textView.setText(mList.get(position));
            return convertView;
        }
    }
}
