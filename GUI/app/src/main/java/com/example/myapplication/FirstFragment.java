package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    private boolean doubelBackToExitPressedOnce = false;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {

                if(doubelBackToExitPressedOnce) {
                    getActivity().finishAffinity();
                    return;
                }

                doubelBackToExitPressedOnce = true;
                Toast.makeText(getActivity(), "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doubelBackToExitPressedOnce = false;
                    }
                }, 2000);

            }

        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //gehe von "laufende Spiele"-Button zum Select screen.
        binding.currentGamesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.main_to_running);

            }

        });

        //gehe von "Match history"-Button zum Select screen.
        binding.buttonMatchHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_matchHistory);

            }

        });

        //gehe von "upcomming Games"-Button zum Select screen.
        binding.upcommingGames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_Main_to_starting);

            }

        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}