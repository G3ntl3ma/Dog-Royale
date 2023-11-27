package com.example.myapplication;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


import android.text.Layout;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

//databinding
import com.example.myapplication.databinding.FragmentGameBoardBinding;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

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
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int pxWidth;
    private FragmentGameBoardBinding binding;
    //save positions
    private ArrayList<Tuple> positions = new ArrayList<Tuple>();
    //Anzahl Spielfelder
    private int field_size = 1;
    //Anzahl Spieler
    private int player_count = 2;
    //die wievielten Spielfelder Startfelder sind.
    private int[] Start_positions = new int[6];

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
        binding = FragmentGameBoardBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        // Inflate the layout for this fragment
        return view;
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //getting Display size
        DisplayMetrics displayMetrics  = getContext().getResources().getDisplayMetrics();
        /*
        if needed in dp
        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        */

        pxWidth = displayMetrics.widthPixels;
        int pxHeight = displayMetrics.heightPixels;

        //set board in middle
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                Math.round(displayMetrics.widthPixels)
        );
        params.topToTop = binding.gaBoConstL.getId();
        params.bottomToBottom = binding.gaBoConstL.getId();
        RelativeLayout GameBoard = binding.gameBoardLayout;
        GameBoard.setLayoutParams(params);

        //System.out.println(displayMetrics.heightPixels);
        //System.out.println(displayMetrics.widthPixels);

        createFields(GameBoard, pxWidth, 5);
        int[] start_colors = {R.color.p1_color, R.color.p2_color, R.color.p3_color, R.color.p4_color, R.color.p5_color, R.color.p6_color};
        int pcolor = 0;
        //testweise
        Start_positions = new int[]{0, 2, 4, 0 ,0, 0};

        for(int x = 1 ; x<=Start_positions.length; x++)
        {
            ImageView imageView = GameBoard.findViewWithTag(Start_positions[x-1]);
            imageView.setColorFilter(ContextCompat.getColor(getContext(), start_colors[pcolor]), PorterDuff.Mode.MULTIPLY);
            //imageView.setImageTintMode(PorterDuff.Mode.MULTIPLY);

            pcolor +=1;
        }

    }
    public void createFields(RelativeLayout layout, int width, int n){
        CoordinateCalculator playingField = new CoordinateCalculator(n, 4, 740);
        /*if (n<= 20) {
            playingField = new CoordinateCalculator(n, 4, width / 2 - 3 * width / 20);
        }
        else {
            playingField = new CoordinateCalculator(n, 4, width / 2 - 3 * width / n);
        }*/
        playingField = new CoordinateCalculator(n, 4, width / 2 - 3 * width / n);

        Tuple result = new Tuple(0,0);
            for (int i = 0; i < n; i++) {
                result = playingField.calculateFloatCoordinates(i);
                if (n<= 20) {
                    createField(layout, width/10, (int) Math.round(result.getX() + width/2 - (  width/20 ))  , (int) Math.round(result.getY()  + width/2 - (width/20)), i);
                }
                else {
                    createField(layout, width/n * 2, (int) Math.round(result.getX() + width / 2 - ( width / n)), (int) Math.round(result.getY() + width / 2 - (width / n)), i);
                }
            }

    }
    /*public int fx(int x, int turn, int width, int n)
    {
        int w_f  = width/n;


        switch(turn) {
            case 1:
                return( x* w_f + w_f);

            case 2:
                return(width - 2* w_f);

            case 3:
                return( width - 2*w_f -  x* w_f );

            case 4:
                return(w_f);
        }
        return(0);
    }
    public int fy(int x, int turn, int width, int n)
    {
        int w_f  = width/n;
        switch(turn) {
            case 1:
                return(w_f);

            case 2:
                return( x* w_f + w_f);

            case 3:
                return( width - 2 * w_f);

            case 4:
                return( width - 2* w_f -  x* w_f );

        }
        return (0);
    } */

    /*
    *Use this  to create a single gamefield on your board
    *
    *
    * @layout is the layout the View is added to. (only RelativeLayout works rn)
    * @width is the width and height of the image
    * @x is the x position
    * @y is the y position
    * @id is the id you want to give the image.
     */
    public void createField(RelativeLayout layout, int width, int x, int y, int id){
        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(R.drawable.spielfeld);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(1, 1);
        params = new RelativeLayout.LayoutParams(width, width);
        params.setMargins(x, y, 0, 0);
        positions.add(new Tuple(x, y));
        //if (Arrays.stream(Start_positions).anyMatch(z -> z==id))

        imageView.setTag(id);
        imageView.setLayoutParams(params);
        layout.addView(imageView);
    }
}