package com.vinay.betforparty;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

public class MypredictionActivity extends AppCompatActivity {

    TextView TotalCoontribution;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypredictions);

        TotalCoontribution = findViewById(R.id.totalsum);
        TotalCoontribution.setText("₹ "+Integer.toString(HomeActivity.MyTotAmt));

        RecyclerView program = findViewById(R.id.listview12);
        program.setLayoutManager(new LinearLayoutManager(this));
        program.setAdapter(new MatchlistAdapter(HomeActivity.myPreds));
        program.setHasFixedSize(true);
    }
}