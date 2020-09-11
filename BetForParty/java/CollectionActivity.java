package com.vinay.betforparty;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Collections;

public class CollectionActivity extends AppCompatActivity {

    private ListView collectionlv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        Collections.sort(HomeActivity.contribution_lists);

        collectionlv = findViewById(R.id.collectview);
        CustomAdapter customAdapter = new CustomAdapter();
        collectionlv.setAdapter(customAdapter);
    }

    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return HomeActivity.contribution_lists.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.collectionlist_layout,parent,false);
            TextView Name = convertView.findViewById(R.id.collection_name);
            TextView Amt = convertView.findViewById(R.id.collection_amt);

            Name.setText(HomeActivity.contribution_lists.get(position).getName());
            //Amt.setText(HomeActivity.contribution_lists.get(position).getName());
            Amt.setText(Integer.toString(HomeActivity.contribution_lists.get(position).getAmt()));

            return convertView;
        }
    }
}
