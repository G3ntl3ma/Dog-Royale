package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.GUILogic.Timer;
import com.example.myapplication.databinding.FragmentSpectateGamesBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RunningGames#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RunningGames extends Fragment {

    private FragmentSpectateGamesBinding binding;

    //games enthält alle Spiele und für jedes Element in games wird der Adapter einen Eintrag in Spectate Games erstellen
    List<Game> games = new ArrayList<>();
    RunningGamesAdapter adapter;

    private ServerViewModel viewModel;

    public RunningGames() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CurrentGames.
     */
    public static RunningGames newInstance() {
        RunningGames fragment = new RunningGames();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        Timer timer = new Timer(60_000);
        timer.startTimer();
        LastCard lastCard = new LastCard();
        lastCard.setNewLastCard(CardType.MAGNET);
        lastCard.showLastCard(true);

        adapter = new RunningGamesAdapter(getContext(), games, RunningGames.this);
        binding.SpectateGamesRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.SpectateGamesRecycleView.setAdapter(adapter);
        viewModel = new ViewModelProvider(requireActivity()).get(ServerViewModel.class);

        viewModel.getSpectateGames().observe(getViewLifecycleOwner(), list -> {
            if(games.size()> 0){
                for(int i = games.size()-1; i>=0; i--){
                    games.remove(i);
                }
            }
            for(int i = 0; i < list.size(); i++) {
                games.add(list.get(i));
            }
            adapter.notifyDataSetChanged();
        });
        viewModelTest viewModelTest = new viewModelTest();
        binding.currentBackToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(RunningGames.this)
                        .navigate(R.id.action_RunningGames_to_FirstFragment);

            }

        });



        binding.spectateGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(RunningGames.this)
                        .navigate(R.id.action_SpectateGames_to_waitingScreen);
            }

        });
    }


    public void addGame(Game game){
        viewModel.addGame(game);
    }
}
