/**
 * @author: leisen
 */
package com.example.myapplication;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.FragmentSpectateGamesBinding;

public class SpectateGamesViewholder extends RecyclerView.ViewHolder{

    SpectateGamesAdapter adapter;

    TextView gameID, time, players;
    Button spectate_button;
    public SpectateGamesViewholder(@NonNull View itemView){
        super(itemView);
        gameID =  itemView.findViewById(R.id.gameID);
        time = itemView.findViewById(R.id.timeSecondsView);
        players = itemView.findViewById(R.id.Players);
        spectate_button = itemView.findViewById(R.id.Spectate_Button);
    }

    public SpectateGamesViewholder linkAdapter(SpectateGamesAdapter adapter){
        this.adapter = adapter;
        return this;
    }
}
