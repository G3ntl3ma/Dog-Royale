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

public class StartingGamesAdapter extends RecyclerView.Adapter<StartingGamesViewholder>{

    Context context;

    List<ReturnGameList.StartingGame> games;

    StartingGames startinggames;

    public StartingGamesAdapter(Context context, List<ReturnGameList.StartingGame> games, StartingGames startinggames) {

        this.context = context;

        this.games = games;

        this.startinggames = startinggames;
    }

    @NonNull
    @Override
    public StartingGamesViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new StartingGamesViewholder(LayoutInflater.from(context).inflate(R.layout.game_view, parent, false)).linkAdapter(this);
    }

    public void onBindViewHolder(@NonNull StartingGamesViewholder holder, int position) {
        //current_game hat die Infos für das Spiel
        ReturnGameList.StartingGame current_game = games.get(holder.getAdapterPosition());
        holder.gameID.setText(Integer.toString(current_game.getGameId()));
        holder.players.setText(Integer.toString(current_game.getCurrentPlayerCount()) + "/" + Integer.toString(current_game.getMaxPlayerCount()));

        holder.spectate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NavHostFragment.findNavController(startinggames)
                        .navigate(R.id.action_StartingGames_to_waitingScreen);
            }
        });
    }

    @Override
    public int getItemCount() {return games.size();}
}
