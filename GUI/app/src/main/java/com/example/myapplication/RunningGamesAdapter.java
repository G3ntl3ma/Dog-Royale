/**
 * @author: leisen
 */
package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RunningGamesAdapter extends RecyclerView.Adapter<RunningGamesViewholder>{

    Context context;
    List<Game> games;

    RunningGames runningGames;

    public RunningGamesAdapter(Context context, List<Game> games, RunningGames runningGames) {
        this.context = context;
        this.games = games;
        this.runningGames = runningGames;
    }

    @NonNull
    @Override
    public RunningGamesViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RunningGamesViewholder(LayoutInflater.from(context).inflate(R.layout.game_view, parent, false)).linkAdapter(this);
    }

    /**
     * Informationen für die einzelnen Elemente der Recyclerview werden hier zugewiesen, damit sie richtig angezeigt werden.
     *
     */
    @Override
    public void onBindViewHolder(@NonNull RunningGamesViewholder holder, int position) {
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

                NavHostFragment.findNavController(runningGames)
                        .navigate(R.id.action_SpectateGames_to_waitingScreen);
            }
        });
    }



    @Override
    public int getItemCount() {
        return games.size();
    }
}
