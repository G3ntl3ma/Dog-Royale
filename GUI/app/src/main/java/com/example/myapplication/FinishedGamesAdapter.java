package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.messages.menu.ReturnGameList;

import java.util.List;

public class FinishedGamesAdapter extends RecyclerView.Adapter<FinishedGamesViewHolder>{

    Context context;

    List<ReturnGameList.FinishedGame> games;

    MatchHistory matchHistory;

    public FinishedGamesAdapter(Context context, List<ReturnGameList.FinishedGame> games, MatchHistory matchHistory) {

        this.context = context;

        this.games = games;

        this.matchHistory = matchHistory;
    }

    @NonNull
    @Override
    public FinishedGamesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new FinishedGamesViewHolder(LayoutInflater.from(context).inflate(R.layout.finished_game_view, parent, false)).linkAdapter(this);
    }

    public void onBindViewHolder(@NonNull FinishedGamesViewHolder holder, int position) {
        //current_game hat die Infos für das Spiel
        ReturnGameList.FinishedGame current_game = games.get(holder.getAdapterPosition());
        holder.gameID.setText(Integer.toString(current_game.getGameId()));
        holder.winnerID.setText(Integer.toString(current_game.getWinnerPlayerId()));
    }

    @Override
    public int getItemCount() {return games.size();}
}
