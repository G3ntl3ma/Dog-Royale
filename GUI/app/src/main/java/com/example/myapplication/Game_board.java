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
    private int player_count = 6;
    //die wievielten Spielfelder Startfelder sind.
    private int figure_count = 12;
    private int[] Start_positions = new int[player_count];

    //Farben der Start/hausfelder
    private int[] start_colors = {R.color.p1_color, R.color.p2_color, R.color.p3_color, R.color.p4_color, R.color.p5_color, R.color.p6_color};

    private int[] draw_fields = new int[5];
    private LastCard last_card;
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
        /* if needed in dp

        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        */
        pxWidth = displayMetrics.widthPixels;
        int pxHeight = displayMetrics.heightPixels;

        //set board in middle of screen
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                Math.round(displayMetrics.widthPixels)
        );
        params.topToTop = binding.gaBoConstL.getId();
        params.bottomToBottom = binding.gaBoConstL.getId();
        RelativeLayout GameBoard = binding.gameBoardLayout;
        GameBoard.setLayoutParams(params);

        field_size = 17;
        //Erstellung der Spielfelder
        createFields(GameBoard, pxWidth, field_size, player_count);

        //Farben der Hausfelder/Startfelder

        int pcolor = 0;

        //testweise
        Start_positions = new int[]{0, 2, 4, 6 ,8, 10};
        Tuple[] homefields = new Tuple[player_count];

        //Farbenzuweisung der Startfelder
        for(int x = 1 ; x<=Start_positions.length; x++)
        {
            ImageView imageView = GameBoard.findViewWithTag("normal" + Start_positions[x-1]);
            imageView.setColorFilter(ContextCompat.getColor(getContext(), start_colors[pcolor]), PorterDuff.Mode.MULTIPLY); // sets the Color and TintMode to multiply
            //imageView.setImageTintMode(PorterDuff.Mode.MULTIPLY);
            pcolor +=1;
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) imageView.getLayoutParams(); //LayoutParams of the startfield
            homefields[x-1] = new Tuple(lp.leftMargin + lp.width/2, lp.topMargin + lp.width/2); //saving startfield positions for homefield creation with position in middle of the field

        }

        //Erstellung der Hausfelder
        createHomeFields(GameBoard, homefields, pxWidth, figure_count);
        draw_fields = new int[]{3, 5, 7, 13, 15};
        createDrawFields(GameBoard, draw_fields);

        //instanziert die Figuren in das Layout
        for (int i = 1; i<= player_count; i++)
        {
            for (int j = 1; j<= figure_count; j++) {
                createFigure(GameBoard, i, j);
            }
        }

        Timer timer = new Timer (600_000, binding);
        timer.startTimer();
        last_card = new LastCard(binding);
        last_card.lastCardAvailable(true);


        //NUR ZUM TESTEN

        binding.moveFigure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (((RelativeLayout.LayoutParams) GameBoard.findViewWithTag("figure1_1").getLayoutParams()).leftMargin == pxWidth)
                {
                    moveFigure(GameBoard, "figure1_1", 1, position, false, null );
                }
                else {
                    position += 2;
                    moveFigure(GameBoard, "figure1_1", 1, position, false, null);
                }
            }

        });}

    /**
     *erstellt die Spiefleder für das Spielbrett
     *
     * @param layout ist das layout in das die Felder gesetzt werden
     * @param width ist die Weite des Spielbrett
     * @param n ist die Anzahl der Felder
     * @param figure_count Anzahl der Figuren
     **/
    public void createFields(RelativeLayout layout, int width, int n, int figure_count){
        CoordinateCalculator playingField = new CoordinateCalculator(n, 4, 540);

        //Falls weniger als 20 Felder erstellt werden feste Spielfeldgröße
        if (n<= 20) {
            playingField = new CoordinateCalculator(n, player_count, width /2 - width/10);
            Tuple result = new Tuple(0,0);  //Tuple in das die Werte der Koordinaten kommen
            for (int i = 0; i < n; i++) {
                result = playingField.calculateFloatCoordinates(i); //berechne x,y Koordinaten eines Feldes
                //erstelle Feld und Zentrierung
                createField(layout, width/10, (int) Math.round(result.getX() + width/2 - (  width/20 ))  , (int) Math.round(result.getY()  + width/2 - (width/20)), i, "normal", 0);
            }
        }
        else {
            playingField = new CoordinateCalculator(n, player_count, width /2 - width / n);
            Tuple result = new Tuple(0,0); //Tuple in das die Werte der Koordinaten kommen
            for (int i = 0; i < n; i++) {
                result = playingField.calculateFloatCoordinates(i); //berechne x,y Koordinaten eines Feldes
                //erstelle Feld und Zentrierung
                createField(layout, width/n * 2, (int) Math.round(result.getX() + width / 2 - ( width / n)), (int) Math.round(result.getY() + width / 2 - (width / n)), i, "normal", 0);
            }
        }
    }


    /**
     *Use this  to create a single gamefield on your board
     *
     *
     * @param layout is the layout the View is added to. (only RelativeLayout works rn)
     * @param width is the width and height of the image
     * @param x is the x position
     * @param y is the y position
     * @param id is the id you want to give the image.
     * @param type is the type of the Field (normal/homefield_playernumber)
     * @param j is the int for the color and player number, nomral fields just use 0
     **/
    public void createField(RelativeLayout layout, int width, int x, int y, int id, String type, int j){
        ImageView imageView = new ImageView(getContext()); //erstelle ImageView
        imageView.setImageResource(R.drawable.spielfeld); //hinzufügen von Bildressource
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(1, 1);
        params = new RelativeLayout.LayoutParams(width, width); //Größe des Bildes einstellen
        params.setMargins(x, y, 0, 0); //Position in layout einstellen (mithilfe von margins)
        String tag;
        imageView.setLayoutParams(params); // Position des Views setzen
        if (type == "homefield")
        {
            imageView.setColorFilter(ContextCompat.getColor(getContext(), start_colors[j-1]), PorterDuff.Mode.MULTIPLY);

            tag = type + j + "_" + id;
            imageView.setTag(tag);

        }
        else
        {
            tag = type + id;
            imageView.setTag(tag); //Füge der View einen Tag zu
        }
        layout.addView(imageView); //füge dem Layout das View hinzu
        /* For testing
        TextView textView = new TextView(getContext());
        String id_s = "" + id;
        textView.setText(id_s);
        textView.setLayoutParams(params);
        layout.addView(textView);
        For testing */

    }

    /**
     *
     * @param layout layout the Views get added to
     * @param homefields Array of the homefields
     * @param width Width of the Layout the fields are added to
     * @param n number of figurines
     */
    public void createHomeFields(RelativeLayout layout, Tuple[] homefields, int width, int n )
    {
        Tuple mid = new Tuple(width /2, width/2);
        for (int j = homefields.length; j >=1; j--)
        {
            Tuple vek = new Tuple((mid.getX() - homefields[j-1].getX()) , (mid.getY() - homefields[j-1].getY()) ); //berechnet den Vektor zwischen der Mitte und einem Hausfeld
            if(field_size>20) {
                vek.setX(vek.getX() - 1/(vek.vek_length()/(2*pxWidth/field_size)) * vek.getX());
                vek.setY(vek.getY() - 1/(vek.vek_length()/(2*pxWidth/field_size)) * vek.getY());
            }
            else{
                vek.setX(vek.getX() - 1/(vek.vek_length()/(2*pxWidth/20)) * vek.getX());
                vek.setY(vek.getY() - 1/(vek.vek_length()/(2*pxWidth/20)) * vek.getY());
            }
            //System.out.println(mid.getX() + " und " + homefields[j-1].getX() + " also " + (mid.getX() - homefields[j-1].getX()) ); //fürs testen
            System.out.println("Hausfeld" + homefields[j-1]);
            System.out.println("Länge: " + vek.vek_length());
            System.out.println("Vektor: " + vek);
            for (int k = 0; k<n;k++) {
                Tuple pos = fh(width/n, vek, homefields[j-1] , k , n + 2 ); //berechnet position auf dem Vektor
                createField(layout, (int) Math.round(vek.vek_length()/(n + 1)), (int) Math.round(pos.getX() - (vek.vek_length()/(n + 1))/2), (int) Math.round(pos.getY() -  (vek.vek_length()/(n + 1))/2), k, "homefield", j ); // erstellt das Feld
            }
        }
    }


    /** berechnet i.te von n Position auf Vektor (für Hausfelder)
     *
     * @param width
     * @param vek Vektor auf dem der Punkt liegen soll
     * @param home Position eines Hausfeldes als Tupel
     * @param i Wievielte Hausfeld in einer Reihe
     * @param n Anzahl an Hausfeldern/Spielfiguren
     * @return gibt Tupel mit Position eine Hausfeldes wieder.
     */
    public Tuple fh(int width, Tuple vek, Tuple home, int i, int n)
    {
        double home_x = home.getX() ;
        double home_y = home.getY() ;
        double vek_x = vek.getX();
        double vek_y = vek.getY();
        double offset;

        if (field_size<= 20){
            offset = vek.vek_length()/(1.5 * pxWidth/20) ;
        }
        else {
            offset = vek.vek_length() / ( 1.5 * pxWidth / field_size);
        }
        System.out.println(offset);
        //ome_x + = vek.vek_length()/n


        //System.out.println(home_x);
        //System.out.println(vek_x);
        //System.out.println(home_x + vek_x);
        return (new Tuple(home_x + 1/offset*vek_x + vek_x/n * i,home_y + 1/offset*vek_y + vek_y/n * i));
    }

    public void createDrawFields(RelativeLayout layout, int[] positions)
    {
        for (int i = 0; i< positions.length; i++) {

            ImageView imageView = layout.findViewWithTag("normal"+positions[i]);
            imageView.setImageResource(R.drawable.ziehfeld);
        }
    }
    public void createFigure(RelativeLayout layout, int playernumber, int numbernumber)
    {
        ImageView imageView = new ImageView(getContext()); //instanciate empty imageview
        imageView.setImageResource(R.drawable.figure); //set Image
        imageView.setColorFilter(ContextCompat.getColor((getContext()), start_colors[playernumber -1 ]), PorterDuff.Mode.MULTIPLY);
        ImageView zfield = layout.findViewWithTag("normal"+ 0); //get field 0
        int w = zfield.getLayoutParams().width;
        RelativeLayout.LayoutParams layoutparams = new RelativeLayout.LayoutParams( w, w);
        layoutparams.setMargins(pxWidth, pxWidth, 0, 0);// set fields to bottom right corner of layout (-> created but not visible) (watch out for bugs :) )
        imageView.setTag("figure" + playernumber + "_" + numbernumber); // set Tag of figure (figure_pnumber_fignumber
        imageView.setLayoutParams(layoutparams); //apply layoutparams to imageview

        layout.addView(imageView); //instanciate imageView in layout
    }
    public void moveFigure(RelativeLayout layout, String pieceId, int playernumber, Integer position, boolean isOnBench, Integer inHousePosition)
    {
        ImageView figure = layout.findViewWithTag(pieceId);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) figure.getLayoutParams();
        if (isOnBench)
        {

        }
        else
        {
            if (position == null)
            {
                ImageView newpos_view = layout.findViewWithTag("homefield" + playernumber +"_" + (figure_count - inHousePosition));
                RelativeLayout.LayoutParams newpos = (RelativeLayout.LayoutParams) newpos_view.getLayoutParams();
                layoutParams.setMargins(newpos.leftMargin + newpos.width - layoutParams.width, newpos.topMargin + newpos.width - layoutParams.width, 0, 0);
                figure.setLayoutParams(layoutParams);
            }
            else
            {
                ImageView newpos_view = layout.findViewWithTag("normal" + position);
                RelativeLayout.LayoutParams newpos = (RelativeLayout.LayoutParams) newpos_view.getLayoutParams();
                System.out.println(newpos.leftMargin + "und" + newpos.width);
                layoutParams.setMargins(newpos.leftMargin + newpos.width - layoutParams.width, newpos.topMargin + newpos.width - layoutParams.width, 0 ,0 );
                figure.setLayoutParams(layoutParams);
            }
        }

    }
}
