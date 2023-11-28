package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.databinding.FragmentSpectateGamesBinding;

import java.util.LinkedList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SpectateGames#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SpectateGames extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    int counter = 0;
    private FragmentSpectateGamesBinding binding;
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SpectateGames() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CurrentGames.
     */
    // TODO: Rename and change types and number of parameters
    public static SpectateGames newInstance(String param1, String param2) {
        SpectateGames fragment = new SpectateGames();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        List<String> items= new LinkedList<String>();
        items.add("Code it");
        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.spectate_games_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        spectatingGamesAdapter adapter = new spectatingGamesAdapter(items);
        recyclerView.setAdapter(adapter);
        System.out.println("Point A has been reached");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentSpectateGamesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.currentBackToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SpectateGames.this)
                        .navigate(R.id.action_current_games_to_FirstFragment);

            }

        });
        binding.spectateGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SpectateGames.this)
                        .navigate(R.id.action_SpectateGames_to_game_board_layout);

            }

        });

    }
}
