package com.example.myapplication;

import android.app.ActionBar;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
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

    private FragmentGameBoardBinding binding;

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
        //
        System.out.println("Gameboard created");
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {
            System.out.println("da hail this");
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

        int pxWidth = displayMetrics.widthPixels;
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

        createFields(GameBoard, pxWidth, 40);

        CoordinateCalculator playingField = new CoordinateCalculator(360, 4, 400);
        Tuple[] result = new Tuple[playingField.getFieldsize()];
        result = playingField.calculateFloatCoordinates();
        FileWriter writer;
        File coordinates = new File("Koordinaten.txt");
        try {
            writer = new FileWriter(coordinates, true);
            for (int i = 0; i < result.length; i++) {
                String coordinateAsString = result[i].toString();
                writer.write(coordinateAsString);
            }


            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("I did it");
    }
    public void createFields(RelativeLayout layout, int width, int n){
            for (int i = 0; i < n; i++) {
                createField(layout, width/n, width/n, fx(i, j, width, n), fy(i, j, width, n));

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
    public void createField(RelativeLayout layout, int width, int height, int x, int y){
        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(R.drawable.spielfeld);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
        params.setMargins(x, y, 0, 0);
        imageView.setLayoutParams(params);
        layout.addView(imageView);
    }
}