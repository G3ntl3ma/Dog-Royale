/**
 * @author: leisen
 */
package com.example.myapplication;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FinishedGamesViewHolder extends RecyclerView.ViewHolder{

    FinishedGamesAdapter adapter;

    TextView gameID, winnerID;
    public FinishedGamesViewHolder(@NonNull View itemView){
        super(itemView);
        gameID =  itemView.findViewById(R.id.gameIDWinner);
        winnerID = itemView.findViewById(R.id.Winner);
    }

    public FinishedGamesViewHolder linkAdapter(FinishedGamesAdapter adapter){
        this.adapter = adapter;
        return this;
    }
}
