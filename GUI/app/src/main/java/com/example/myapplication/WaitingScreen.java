package com.example.myapplication;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.controller.ClientController;
import com.example.myapplication.databinding.FragmentWaitingScreenBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WaitingScreen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WaitingScreen extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FragmentWaitingScreenBinding binding;

    public WaitingScreen() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WaitingScreen.
     */
    // TODO: Rename and change types and number of parameters
    public static WaitingScreen newInstance(String param1, String param2) {
        WaitingScreen fragment = new WaitingScreen();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ClientController.getInstance().getWaitingScreen(this);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_waiting_screen, container, false);
    }
    public void navigateToGame(){
        NavHostFragment.findNavController(WaitingScreen.this)
                .navigate(R.id.action_waitingScreen_to_game_board_layout);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding = FragmentWaitingScreenBinding.bind(view);

        binding.continuebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        binding.goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(WaitingScreen.this)
                        .navigate(R.id.action_waitingScreen_to_FirstFragment);
            }
        });
    }

}