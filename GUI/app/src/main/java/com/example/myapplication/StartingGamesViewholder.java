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

public class StartingGamesViewholder extends RecyclerView.ViewHolder{

    StartingGamesAdapter adapter;

    TextView gameID, players;
    Button spectate_button;
    public StartingGamesViewholder(@NonNull View itemView){
        super(itemView);
        gameID =  itemView.findViewById(R.id.gameID);
        players = itemView.findViewById(R.id.Players);
        spectate_button = itemView.findViewById(R.id.Spectate_Button);
    }

    public StartingGamesViewholder linkAdapter(StartingGamesAdapter adapter){
        this.adapter = adapter;
        return this;
    }
}
