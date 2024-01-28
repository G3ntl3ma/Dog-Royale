package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.databinding.FragmentStartingGamesBinding;
import com.example.myapplication.messages.menu.ReturnGameList;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StartingGames#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StartingGames extends Fragment {

    List<ReturnGameList.StartingGame> games = new ArrayList<>();
    StartingGamesAdapter adapter;

    private StartingGamesViewmodel viewModel;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private FragmentStartingGamesBinding binding;

    public StartingGames() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment upcomming_games.
     */
    // TODO: Rename and change types and number of parameters
    public static StartingGames newInstance() {
        StartingGames fragment = new StartingGames();
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

        binding = FragmentStartingGamesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Set up the Recyclerview
        adapter = new StartingGamesAdapter(getContext(), games, this);
        binding.StartingGamesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.StartingGamesRecyclerView.setAdapter(adapter);
        viewModel = new ViewModelProvider(requireActivity()).get(StartingGamesViewmodel.class);


        viewModel.getGamesList().observe(getViewLifecycleOwner(), list -> {
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
                NavHostFragment.findNavController(StartingGames.this)
                        .navigate(R.id.action_StartingGames_to_FirstFragment);


            }
        });

        binding.spectateGameButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(StartingGames.this)
                        .navigate(R.id.action_StartingGames_to_waitingScreen);

            }

        });

    }

    public void navigateToLobby() {
        NavHostFragment.findNavController(StartingGames.this)
                .navigate(R.id.action_StartingGames_to_FirstFragment);
    }
}