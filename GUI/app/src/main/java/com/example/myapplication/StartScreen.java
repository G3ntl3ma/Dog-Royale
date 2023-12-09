package com.example.myapplication;

import static com.example.myapplication.handler.messageHandler.menu.MenuMessageHandler.gson;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.controller.ClientController;
import com.example.myapplication.databinding.FragmentStartScreenBinding;
import com.example.myapplication.handler.HandlingException;
import com.example.myapplication.handler.ServerHandler;
import com.example.myapplication.messages.game.TypeGame;
import com.example.myapplication.messages.menu.ConnectToServer;
import com.example.myapplication.messages.menu.ConnectedToServer;
import com.example.myapplication.messages.menu.TypeMenu;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StartScreen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StartScreen extends Fragment {
    private StartScreenViewModel viewModel;
    FragmentStartScreenBinding binding;
    ClientController clientController = ClientController.getInstance();
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
    // New method for navigation
    public void navigateToFirstFragment() {
        NavHostFragment.findNavController(StartScreen.this)
                .navigate(R.id.action_startScreen_to_FirstFragment);
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
                    viewModel.setUsername(username);
                    ConnectToServer connectToServer = new ConnectToServer();
                    connectToServer.setType(TypeMenu.connectToServer.getOrdinal());
                    connectToServer.setName(username);
                    connectToServer.setIsObserver(true);
                    binding.button.setEnabled(false);
                    try{
                        clientController.sendConnectToServerRequest(connectToServer,StartScreen.this);
                    }
                    catch (
                            HandlingException e) {
                        binding.button.setError("connection failed");
                    }



                }

            }

        });
    }
}