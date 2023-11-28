package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class spectatingGamesAdapter extends RecyclerView.Adapter<ViewHolder>{
    List<String> items;

    public spectatingGamesAdapter(List<String> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.game, parent, false);
        return new ViewHolder(view).linkAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

class ViewHolder extends RecyclerView.ViewHolder{
    TextView textView;
    private spectatingGamesAdapter adapter;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        textView = itemView.findViewById(R.id.spectate_games_recycler);
        itemView.findViewById(R.id.spectateButton).setOnClickListener(view -> {
            adapter.items.remove(getAdapterPosition());
            adapter.notifyItemRemoved(getAdapterPosition());
        });
    }

    public ViewHolder linkAdapter(spectatingGamesAdapter adapter){
        this.adapter = adapter;
        return this;
    }
}