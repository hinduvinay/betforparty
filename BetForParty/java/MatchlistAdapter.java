package com.vinay.betforparty;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MatchlistAdapter extends RecyclerView.Adapter<MatchlistAdapter.MatchlictViewHolder> {

    private ArrayList<Prediction> PredictionArrayList;
    public MatchlistAdapter(ArrayList<Prediction> Predictions){
        this.PredictionArrayList = Predictions;
    }

    @NonNull
    @Override
    public MatchlictViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.myallpred_layout,viewGroup,false);
        return new MatchlictViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchlictViewHolder matchlictViewHolder, int i) {
        Prediction Prediction1 = HomeActivity.myPreds.get(i);
        matchlictViewHolder.Mcnt.setText(Integer.toString(Prediction1.getMcnt()));
        matchlictViewHolder.Date.setText(Prediction1.getDate().substring(5));
        matchlictViewHolder.TeamA.setText(Prediction1.getTeam1());
        matchlictViewHolder.TeamB.setText(Prediction1.getTeam2());
        matchlictViewHolder.Pred.setText(Prediction1.getPred());
        matchlictViewHolder.Winner.setText(Prediction1.getWinner());
        matchlictViewHolder.Amt.setText(Integer.toString(Prediction1.getAmt()));
    }

    @Override
    public int getItemCount() {
        return PredictionArrayList.size();
    }

    public class MatchlictViewHolder extends RecyclerView.ViewHolder{
        TextView Mcnt,Date,TeamA,TeamB,Pred,Winner,Amt;

        public MatchlictViewHolder(@NonNull View itemView) {
            super(itemView);
            Mcnt = itemView.findViewById(R.id.rvmcnt);
            Date = itemView.findViewById(R.id.rvdate);
            TeamA = itemView.findViewById(R.id.rvteama);
            TeamB = itemView.findViewById(R.id.rvteamb);
            Pred = itemView.findViewById(R.id.rvpred);
            Winner = itemView.findViewById(R.id.rvwinner);
            Amt = itemView.findViewById(R.id.rvamt);
        }
    }
}