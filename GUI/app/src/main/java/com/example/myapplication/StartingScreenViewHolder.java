package com.example.myapplication;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StartingScreenViewHolder extends RecyclerView.ViewHolder {

   StartingGamesAdapter adapter;

    TextView gameID, time, players;

    Button spectate_button;

    public StartingScreenViewHolder(@NonNull View itemView){
        super(itemView);
        gameID =  itemView.findViewById(R.id.gameID);
        players = itemView.findViewById(R.id.Players);
        spectate_button = itemView.findViewById(R.id.Spectate_Button);
    }

    public StartingScreenViewHolder linkAdapter(StartingGamesAdapter adapter){
        this.adapter = adapter;
        return this;
    }


}
