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

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StartingGames#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StartingGames extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    List<Game> games = new ArrayList<>();
    StartingGamesAdapter adapter;

    private ServerViewModel viewModel;

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
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment upcomming_games.
     */
    // TODO: Rename and change types and number of parameters
    public static StartingGames newInstance(String param1, String param2) {
        StartingGames fragment = new StartingGames();
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

        adapter = new StartingGamesAdapter(getContext(), games, this);
        binding.StartingGamesRecyclerView.setAdapter(adapter);
        binding.StartingGamesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        viewModel = new ViewModelProvider(requireActivity()).get(ServerViewModel.class);

        viewModel.getRunningGames().observe(getViewLifecycleOwner(), list -> {
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
}