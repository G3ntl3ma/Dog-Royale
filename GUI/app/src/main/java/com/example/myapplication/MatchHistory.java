package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.myapplication.databinding.FragmentFirstBinding;
import com.example.myapplication.databinding.FragmentMatchHistoryBinding;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MatchHistory#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MatchHistory extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentMatchHistoryBinding binding;

    private ServerViewModel viewModel;
    public MatchHistory() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MatchHistory.
     */
    // TODO: Rename and change types and number of parameters
    public static MatchHistory newInstance(String param1, String param2) {
        MatchHistory fragment = new MatchHistory();
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

        binding = FragmentMatchHistoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(ServerViewModel.class);


        for (ServerViewModel.MatchHistory matchHistory: viewModel.getMatchHistory().getValue()) {
            LinearLayout linearLayout = new LinearLayout(getContext());
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            linearLayout.setPadding(5, 5, 5, 5);

            TextView textView = new TextView(getContext());
            System.out.println("MatchHistorygameID: " + matchHistory.getGameId());
            textView.setText("" + matchHistory.getGameId());
            LinearLayout.LayoutParams gameIdLayParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            gameIdLayParams.setMargins(20, 0, 30, 0 );
            textView.setLayoutParams(gameIdLayParams);
            textView.setTextSize(12);
            textView.setTextColor(getResources().getColor(R.color.white));
            linearLayout.addView(textView);

            TextView winnerText = new TextView(getContext());
            winnerText.setText("" + matchHistory.getWinner());
            LinearLayout.LayoutParams winnerLayParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            winnerLayParams.setMargins(20, 0, 30, 0 );
            winnerText.setLayoutParams(winnerLayParams);
            winnerText.setTextSize(12);
            winnerText.setTextColor(getResources().getColor(R.color.white));
            linearLayout.addView(winnerText);

            binding.MatchHistoryTable.addView(linearLayout);

        }



        binding.currentBackToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(MatchHistory.this)
                        .navigate(R.id.action_matchHistory_to_FirstFragment2);


            }
        });

    }
}