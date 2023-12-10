package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.databinding.FragmentStartScreenBinding;
import com.example.myapplication.messages.menu.ReturnGameList;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StartScreen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StartScreen extends Fragment {
    private StartScreenViewModel viewModel;
    FragmentStartScreenBinding binding;
    ServerViewModel serverViewModel;
    public StartScreen() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment StartScreen.
     */
    public static StartScreen newInstance(String param1, String param2) {
        StartScreen fragment = new StartScreen();
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
        binding = FragmentStartScreenBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(StartScreenViewModel.class);


        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = binding.editTextText.getText().toString();
                if (username == null || username.equals("")) {
                    binding.editTextText.setError("Please enter a name!");
                }
                else {
                    //test
                    serverViewModel = new ViewModelProvider(requireActivity()).get(ServerViewModel.class);
                    serverViewModel.addMatchHistoryList(new ArrayList<ReturnGameList.FinishedGame>(Arrays.asList(new ReturnGameList.FinishedGame(1, 1), new ReturnGameList.FinishedGame(2, 2), new ReturnGameList.FinishedGame(3, 3), new ReturnGameList.FinishedGame(4, 4), new ReturnGameList.FinishedGame(5, 5) )));
                    //serverViewModel.addSpectateGamesList(new ArrayList<ReturnGameList.StartingGame>(Arrays.asList(new ReturnGameList.StartingGame(1, 1, 2), new ReturnGameList.StartingGame(2, 2, 3), new ReturnGameList.StartingGame(3, 3, 4), new ReturnGameList.StartingGame(4, 4, 5), new ReturnGameList.StartingGame(5, 5, 6) )));
                    //serverViewModel.addRunningGameList(new ArrayList<ReturnGameList.RunningGame>(Arrays.asList(new ReturnGameList.RunningGame(1, 1, 2), new ReturnGameList.RunningGame(2, 2, 3), new ReturnGameList.RunningGame(3, 3, 4), new ReturnGameList.RunningGame(4, 4, 5), new ReturnGameList.RunningGame(5, 5, 6) )));
                    viewModel.setUsername(username);
                    NavHostFragment.findNavController(StartScreen.this)
                            .navigate(R.id.action_startScreen_to_FirstFragment);
                }
            }
        });
    }
}