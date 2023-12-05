package com.example.myapplication;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;


import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

//databinding
import com.example.myapplication.databinding.FragmentGameBoardBinding;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Game_board#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Game_board extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private int pxWidth;
    private FragmentGameBoardBinding binding;
    //save positions
    private ArrayList<Tuple> positions = new ArrayList<Tuple>();
    //Anzahl Spielfelder
    private int field_size = 1;
    //Anzahl Spieler
    private int player_count;
    //die wievielten Spielfelder Startfelder sind.
    private int figure_count;
    private int[] start_positions = new int[player_count];

    //Farben der Start/hausfelder
    private int[] start_colors = {R.color.p1_color, R.color.p2_color, R.color.p3_color, R.color.p4_color, R.color.p5_color, R.color.p6_color};

    private int[] draw_fields = new int[5];

    private LastCard last_card;
    private GameboardViewModel viewModel;
    //testwise
    private int position = 0;

    public Game_board() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Game_board.
     */
    // TODO: Rename and change types and number of parameters
    public static Game_board newInstance(String param1, String param2) {
        Game_board fragment = new Game_board();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * @param savedInstanceState If the fragment is being re-created from
     *                           a previous saved state, this is the state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGameBoardBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        // Inflate the layout for this fragment
        return view;
    }

    /**
     * @param view               The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     */
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //getting Display information
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();

        //getting display width in px
        pxWidth = displayMetrics.widthPixels;

        //set board in middle of screen
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                Math.round(displayMetrics.widthPixels)
        );
        params.topToTop = binding.gaBoConstL.getId();
        params.bottomToBottom = binding.gaBoConstL.getId();
        RelativeLayout GameBoard = binding.gameBoardLayout;
        GameBoard.setLayoutParams(params);

        viewModel = new ViewModelProvider(requireActivity()).get(GameboardViewModel.class);
        System.out.println(viewModel.getField_size().getValue());
        //set number of fields
        field_size = viewModel.getField_size().getValue();
        //set Player_count
        player_count = viewModel.getPlayer_count().getValue();
        //Set figure Count
        viewModel.getFigure_count().observe(getViewLifecycleOwner(), figures ->{
            figure_count = figures;
        });
        figure_count = viewModel.getFigure_count().getValue();

        //positionen der draw card felder
        draw_fields = new int[]{3, 5, 7, 8, 9};
        //position der startfelder
        start_positions = new int[]{0, 2, 4};
        //creates fields in the layout
        Game_board_creator creator = new Game_board_creator(GameBoard, pxWidth, player_count, field_size, figure_count, start_colors, start_positions, draw_fields);
        creator.createFields();

        //instanziert die Figuren in das Layout
        Figure_handler figure_handler = new Figure_handler(GameBoard, figure_count, player_count, start_colors, creator.getField_width(), creator.getHomefield_size(), pxWidth);
        figure_handler.create_figures();

        Timer timer = new Timer(600_000, binding);
        timer.startTimer();
        last_card = new LastCard(binding);
        last_card.lastCardAvailable(true);


        //NUR ZUM TESTEN für figuren movement

        binding.moveFigure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (((RelativeLayout.LayoutParams) GameBoard.findViewWithTag("figure0_1").getLayoutParams()).leftMargin == pxWidth) {
                    figure_handler.moveFigure(1, "figure0_1", position, false, null);
                    System.out.println("OwO");
                } else {
                    position += 2;
                    if (position >= field_size) {
                        figure_handler.moveFigure(1, "figure0_1", null, false, position - field_size - 1);
                    } else {
                        figure_handler.moveFigure(1, "figure0_1", position, false, null);
                    }

                }
            }

        });
    }
}
