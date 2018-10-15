package com.example.olgam.gamebacklog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class GameAdapter extends RecyclerView.Adapter <GameAdapter.MyViewHolder> {

    //fields
    private ArrayList<Game> games;
    Context context;

    public GameAdapter(MainActivity mainActivity, ArrayList<Game> mGames) {
        this.games = mGames;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        public TextView title;
        public TextView platform;
        public TextView date;
        public TextView status;


        public MyViewHolder(View view) {
            super(view);
            cv = (CardView) itemView.findViewById(R.id.cardView);
            title = itemView.findViewById(R.id.title);
            platform = itemView.findViewById(R.id.platform);
            date = itemView.findViewById(R.id.dateView);
            status = itemView.findViewById(R.id.status);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        MyViewHolder mvh = new MyViewHolder(view);
        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int i) {
        Game game = games.get(i);
        viewHolder.title.setText(games.get(i).title);
        viewHolder.platform.setText(games.get(i).date);
        //viewHolder.date.setText(games.get(i).notes);
    }

    @Override
    public int getItemCount() {
        return games.size();
    }
}
