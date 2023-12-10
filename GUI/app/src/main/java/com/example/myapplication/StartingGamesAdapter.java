package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StartingGamesAdapter extends RecyclerView.Adapter<StartingScreenViewHolder>{

    Context context;

    List<Game> games;

    StartingGames startinggames;

    public StartingGamesAdapter(Context context, List<Game> games, StartingGames startinggames) {

        this.context = context;

        this.games = games;

        this.startinggames = startinggames;
    }

    @NonNull
    @Override
    public StartingScreenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new StartingScreenViewHolder(LayoutInflater.from(context).inflate(R.layout.game_view, parent, false)).linkAdapter(this);
    }

    public void onBindViewHolder(@NonNull StartingScreenViewHolder holder, int position) {
        //current_game hat die Infos für das Spiel
        Game current_game = games.get(holder.getAdapterPosition());
        holder.gameID.setText(current_game.getGameID());
        holder.players.setText(Integer.toString(current_game.getCurrentPlayers()) + "/" + Integer.toString(current_game.getMaxPlayers()));

        holder.spectate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    CurrentGameViewModel currentGameViewModel = MainActivity.getCurrentGameViewModel();
                    currentGameViewModel.setCurrent_game_id(current_game.getGameID());
                    GameboardViewModel viewModel = MainActivity.getGameboardViewModel();
                    //changed - deletet stuff

                NavHostFragment.findNavController(startinggames)
                        .navigate(R.id.action_SpectateGames_to_waitingScreen);
            }
        });
    }

    @Override
    public int getItemCount() {return games.size();}
}
