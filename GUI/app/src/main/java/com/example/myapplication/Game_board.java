package com.example.myapplication;

import android.app.ActionBar;
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
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

//databinding
import com.example.myapplication.GameInformationClasses.Color;
import com.example.myapplication.GameInformationClasses.DrawCardFields;
import com.example.myapplication.GameInformationClasses.Observer;
import com.example.myapplication.GameInformationClasses.Order;
import com.example.myapplication.GameInformationClasses.OrderType;
import com.example.myapplication.GameInformationClasses.PlayerOrder;
import com.example.myapplication.GameInformationClasses.StartFields;
import com.example.myapplication.databinding.FragmentGameBoardBinding;
import com.example.myapplication.messages.game.BoardState;
import com.example.myapplication.messages.menu.ReturnLobbyConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Game_board#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Game_board extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private int pxWidth;
    private FragmentGameBoardBinding binding;

    GameInformation gameInformation;
    private int field_size = 1; // number of fields
    private int player_count; //number of players
    private int figure_count; //number of figures per player
    private List<Integer> start_positions; // List of Startpositions as Integer (0 to field_size-1)  (( we use id = 0 is the first field - easily changable))

    private List<com.example.myapplication.GameInformationClasses.Color> start_colors; //Colors of the startfields (still in Color class not only required color value)

    private List<Integer> draw_fields; // List with the positions of the draw card fields (0 to field_size-1)

    private LastCard last_card; //the last card played
    private GameboardViewModel viewModel; // is the GameBoardViewModel in which we store information about the Gameboard globally
    private TimerviewModel timerviewModel;
    private LastCardViewModel lastCardViewModel;
    //testwise
    private int position = 0 ; // positions for the test figure to move - I know we dont get the change but the new position but that doesnt matter
    private int move_count = 0;
    private BoardUpdater boardUpdater;
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



        viewModel = new ViewModelProvider(requireActivity()).get(GameboardViewModel.class); //creating the ViewModel


        /**DELETE AND DO WITH MESSAGE
         */
        ReturnLobbyConfig insertReturnLobbyConfig = new ReturnLobbyConfig(new Integer(6), new Integer(100), new Integer(20) , Arrays.asList(new Color(0, R.color.p1_color), new Color(1, R.color.p2_color), new Color(2, R.color.p3_color), new Color(3, R.color.p4_color), new Color(4, R.color.p5_color), new Color(5, R.color.p6_color)), new DrawCardFields(5, Arrays.asList(1, 4, 7, 10, 13)), new StartFields(6, Arrays.asList(12, 0, 24, 83, 69, 45)), new Integer(5), new PlayerOrder(OrderType.fixed, Arrays.asList(new Order(0, "OwO"), new Order(1, "UwU"), new Order(2, "AwA"), new Order(3, "QwQ"), new Order(4, "XwX"))),  Arrays.asList(new Observer(new Integer(1), "OwO")), new Integer(5), new Integer(5), new Integer(1), new Integer(15), new Integer(5));
        viewModel.setGameInformation(new GameInformation(insertReturnLobbyConfig));
        /**DELETE AND DO WITH MESSAGE
        */


        RelativeLayout GameBoard = binding.gameBoardLayout; //getting the Layout for the GameBoard

        LinearLayout playerInformationTableView = binding.playerInformationTable;


        //getting Display information
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();


        pxWidth = displayMetrics.widthPixels; //Width of the screen in px

        //setting Layout of board into the middle of the screen
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams( //creating new Layout parameters
                RelativeLayout.LayoutParams.WRAP_CONTENT, //wrapping the content -> just the screen width in this case
                Math.round(displayMetrics.widthPixels) // the height of the Layout shall be the width of the screen
        );
        params.topToTop = binding.gaBoConstL.getId();  //setting the top constraint of the LayoutParameters to the top of the ConstraintLayout
        params.bottomToBottom = binding.gaBoConstL.getId();  //setting the bottom constraint of the LayoutParameters to the bottom of the ConstraintLayout

        GameBoard.setLayoutParams(params); //setting the Layout Parameters to the GameBoard Layout

        viewModel = new ViewModelProvider(requireActivity()).get(GameboardViewModel.class); //creating the ViewModel
        timerviewModel = new ViewModelProvider(requireActivity()).get(TimerviewModel.class);
        lastCardViewModel = new ViewModelProvider(requireActivity()).get(LastCardViewModel.class);
        System.out.println("normale Felder: " + viewModel.getField_size().getValue());

        gameInformation = viewModel.getGameInformation().getValue(); //getting the GameInformation from the ViewModel (the information about the game is stored in here)
                                                                        //need to get the Value because we want the actual information not the LiveData Object
                //set number of fields for gameboard creation
        field_size = gameInformation.getFieldsize();    //getting the fieldsize from the GameInformation

        //field_size = viewModel.getField_size().getValue();                        //old
                //set number of players for gameboard creation
        player_count = gameInformation.getPlayerCount();
        //player_count = viewModel.getPlayer_count().getValue();                    //old

                //set figure Count for gameboard creation
        figure_count = gameInformation.getFiguresPerPlayer();
        //viewModel.getFigure_count().observe(getViewLifecycleOwner(), figures ->{  //old
        //    figure_count = figures;                                               //old
        //});                                                                       //old
        //figure_count = viewModel.getFigure_count().getValue()
                //set position of the draw card fields for gameboard creation
        draw_fields = gameInformation.getDrawCardFields().getPositions();
        //draw_fields = new int[]{3, 5, 7, 8, 9};                                   //old (was for testing)

                //set position of start fields for gameboard creation
        start_positions = gameInformation.getStartFields().getPositions();
        //start_positions = new int[]{0, 2, 4};                                     //old testing

                    //set colors of the start fields for gameboard creation
        start_colors = gameInformation.getColors();

        Integer maxRounds = gameInformation.getMaximumTotalMoves();
        List<Integer> colors = new ArrayList<>();
            //iterating trough colors to get the color value
        for (Color color : start_colors) {
            colors.add(color.getColor());           //getting the actual color value from the Color class
        }
                    //creating the gameboardcreator that creates the gameboard
        Game_board_creator creator = new Game_board_creator(GameBoard, pxWidth, player_count, field_size, figure_count, colors, start_positions, draw_fields, maxRounds);
        creator.createFields(); //creating the fields

        List<Order> order = gameInformation.getPlayerOrder().getOrder();
        PlayerInformationTable playerInformationTable = new PlayerInformationTable(playerInformationTableView, order, player_count, figure_count, gameInformation.getInitialCardsPerPlayer());
        playerInformationTable.BuildTable();
        viewModel.setPlayerInformationTable(playerInformationTable);
        viewModel.setLastPlayer(order.get(0).getClientId());
                    //instanziating  the figure handler
        Figure_handler figure_handler = new Figure_handler(GameBoard, figure_count, player_count, colors, creator.getField_width(), creator.getHomefield_size(), pxWidth, playerInformationTable);
        figure_handler.create_figures(); //creating the figures
        //
        //
        viewModel.setFigure_handler(figure_handler); //setting the figure handler for the viewModel to use it in different classes later on
        //
        //
        //setting the boardUpdater

        boardUpdater = new BoardUpdater();


        //Set the Last Card
        lastCardViewModel.getLastCard().observe(getViewLifecycleOwner(), type ->{
            switch (type) {
                case COPY: binding.lastCardView.setImageResource(R.drawable.card_copy);
                    break;
                case SWAP: binding.lastCardView.setImageResource(R.drawable.card_switch);
                    break;
                case MAGNET: binding.lastCardView.setImageResource(R.drawable.card_magnet);
                    break;
                case RANGE_7: binding.lastCardView.setImageResource(R.drawable.card1to7);
                    break;
                case NORMAL_2: binding.lastCardView.setImageResource(R.drawable.card2);
                    break;
                case NORMAL_3: binding.lastCardView.setImageResource(R.drawable.card3);
                    break;
                case NORMAL_5: binding.lastCardView.setImageResource(R.drawable.card5);
                    break;
                case NORMAL_6: binding.lastCardView.setImageResource(R.drawable.card6);
                    break;
                case NORMAL_8: binding.lastCardView.setImageResource(R.drawable.card8);
                    break;
                case NORMAL_9: binding.lastCardView.setImageResource(R.drawable.card9);
                    break;
                case NORMAL_10: binding.lastCardView.setImageResource(R.drawable.card10);
                    break;
                case NORMAL_12: binding.lastCardView.setImageResource(R.drawable.card12);
                    break;
                case PLUS_MINUS_4: binding.lastCardView.setImageResource(R.drawable.card_plusminus4);
                    break;
                case START_13: binding.lastCardView.setImageResource(R.drawable.card_1_or_11);
                    break;
                case START_1_11: binding.lastCardView.setImageResource(R.drawable.card_start_13);
                    break;
            }

        });

        lastCardViewModel.getShowLastCard().observe(getViewLifecycleOwner(), show ->{
            if(show){
                binding.lastCardView.setVisibility(View.VISIBLE);
            }
            else {
                binding.lastCardView.setVisibility(View.INVISIBLE);
            }

        });
        //Set the Timer
        timerviewModel.getTime().observe(getViewLifecycleOwner(), time -> {
            binding.timerView.setText(time);
        });


        //NUR ZUM TESTEN für figuren movement


        binding.moveFigure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<BoardState.Piece> pieces = new ArrayList<>();
                pieces.add(new BoardState.Piece(0, 0, position, false, 0));
                System.out.println("Name:" + gameInformation.getPlayerOrder().getName(0));
                BoardState boardState = new BoardState(pieces, null, null, 0, move_count, 0, true, new  ArrayList<Integer>(Arrays.asList(0, 1, 2, 3, 4, 5)));
                boardUpdater.UpdateBoard(boardState);
                position++;
                move_count++;
            }

        });
    }
}
