package com.vinay.betforparty;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Collections;

public class MyfriendActivity extends AppCompatActivity {

    ListView Myfriendlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myfriend);

        Collections.sort(HomeActivity.prediction_lists);

        Myfriendlist = findViewById(R.id.myfriendlist);
        MyfriendAdapter myfriendAdapter = new MyfriendAdapter();
        Myfriendlist.setAdapter(myfriendAdapter);
    }

    class MyfriendAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return HomeActivity.prediction_lists.size();
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

            convertView = getLayoutInflater().inflate(R.layout.myfriend_layout,parent,false);

            TextView Name = convertView.findViewById(R.id.myfrienname);
            TextView Mcnt = convertView.findViewById(R.id.myfrienmcnt);
            TextView Team1 = convertView.findViewById(R.id.myfrienteam1);
            TextView Team2 = convertView.findViewById(R.id.myfrienteam2);
            TextView Pred = convertView.findViewById(R.id.myfrienpred);

            Name.setText(HomeActivity.prediction_lists.get(position).getName());
            Mcnt.setText(HomeActivity.prediction_lists.get(position).getMcnt());
            Team1.setText(HomeActivity.prediction_lists.get(position).getTeamA());
            Team2.setText(HomeActivity.prediction_lists.get(position).getTeamB());
            Pred.setText(HomeActivity.prediction_lists.get(position).getPred());

            return convertView;
        }
    }
}
