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

import com.example.myapplication.databinding.FragmentSpectateGamesBinding;

import java.util.ArrayList;
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

    private FragmentSpectateGamesBinding binding;
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    //games enthält alle Spiele und für jedes Element in games wird der Adapter einen Eintrag in Spectate Games erstellen
    List<Game> games = new ArrayList<>();
    SpectateGamesAdapter adapter;

    private ServerViewModel viewModel;
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

        adapter = new SpectateGamesAdapter(getContext(), games, SpectateGames.this);
        binding.SpectateGamesRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.SpectateGamesRecycleView.setAdapter(adapter);
        viewModel = new ViewModelProvider(requireActivity()).get(ServerViewModel.class);

        viewModel.getGames().observe(getViewLifecycleOwner(), list -> {
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
                NavHostFragment.findNavController(SpectateGames.this)
                        .navigate(R.id.action_SpectateGames_to_FirstFragment);

            }

        });
        binding.spectateGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addGame(new Game("Hello", 15, 4 , 6, 100, 20, new int[]{0,1,6,99,33}, new int[]{0,24,49,74}));
            }

        });
    }


    public void addGame(Game game){
        viewModel.addGame(game);
    }
}
