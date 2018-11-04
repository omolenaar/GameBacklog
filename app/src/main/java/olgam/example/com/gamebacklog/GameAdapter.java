package olgam.example.com.gamebacklog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_SHORT;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.MyViewHolder> {

    public interface GameClickListener {
        void gameOnClick(int i);
    }

    //fields
    private ArrayList<Game> games;
    Context context;
    final private GameClickListener mGameClickListener;


    public GameAdapter(ArrayList<Game> games, Context context, GameClickListener mGameClickListener) {
        this.games = games;
        this.context = context;
        this.mGameClickListener = mGameClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        public TextView title;
        public TextView platform;
        public TextView status;
        public TextView date;
        public View view;
        private LinearLayout main;

        public MyViewHolder(final View itemView){
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cardView);
            title = itemView.findViewById(R.id.title);
            platform = itemView.findViewById(R.id.platform);
            date = itemView.findViewById(R.id.dateView);
            status = itemView.findViewById(R.id.status);
            main = (LinearLayout) itemView.findViewById(R.id.main);
            main.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    int clickedPosition = getAdapterPosition();
                    mGameClickListener.gameOnClick(clickedPosition);
                }
            });
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int i) {
        final Game game = games.get(i);
        viewHolder.title.setText(game.getTitle());
        viewHolder.platform.setText(games.get(i).getPlatform());
        viewHolder.status.setText(games.get(i).getStatus());
        viewHolder.date.setText(games.get(i).getDate());
    }

    @Override
    public int getItemCount() {
        if (games == null)
            return 0;
        else
            return games.size();
    }
    public void swapList(ArrayList<Game> newList) {
        games = newList;
        if (newList != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }
}

