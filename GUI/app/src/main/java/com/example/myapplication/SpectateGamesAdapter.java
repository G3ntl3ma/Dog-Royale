/**
 * @author: leisen
 */
package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.FragmentSpectateGamesBinding;

import java.util.List;

public class SpectateGamesAdapter extends RecyclerView.Adapter<SpectateGamesViewholder>{

    Context context;
    List<Game> games;

    SpectateGames spectateGames;

    public SpectateGamesAdapter(Context context, List<Game> games, SpectateGames spectateGames) {
        this.context = context;
        this.games = games;
        this.spectateGames = spectateGames;
    }

    @NonNull
    @Override
    public SpectateGamesViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SpectateGamesViewholder(LayoutInflater.from(context).inflate(R.layout.game_view, parent, false)).linkAdapter(this);
    }

    /**
     * Informationen für die einzelnen Elemente der Recyclerview werden hier zugewiesen, damit sie richtig angezeigt werden.
     *
     */
    @Override
    public void onBindViewHolder(@NonNull SpectateGamesViewholder holder, int position) {
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
                    viewModel.setField_size(current_game.getFieldSize());
                    viewModel.setPlayer_count(current_game.getCurrentPlayers());
                    viewModel.setFigure_count(current_game.getFigureCount());
                    //viewModel.setDrawFields(current_game.getDrawFields());
                    //viewModel.setStart_fields(current_game.getStartFields());
                NavHostFragment.findNavController(spectateGames)
                        .navigate(R.id.action_SpectateGames_to_waitingScreen);
            }
        });
    }



    @Override
    public int getItemCount() {
        return games.size();
    }
}
