package com.example.myapplication;

import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.List;

public class Figure_handler {
    private RelativeLayout layout; //Layout the figures are created in

    private int figure_count; //number of figures per player

    private int player_count; //number of players

    private List<Integer> colors; //colors for the players

    private int figure_size; //size of the figures

    private int homefield_size; //size of homefields

    private int screen_width; //width of the screen the field is created in

    private PlayerInformationTable playerInformationTable; //is the PlayerInformationTable in which we want to change the information about the figures in bank

    private GameboardViewModel viewModel; // is the GameBoardViewModel in which we store information about the Gameboard globally




    /** Constructor
     *
     * @param layout is the layout the figures are created in
     * @param figure_count is the number of figures per player
     * @param player_count is the number of players
     * @param colors is the list of colors for the players
     * @param figure_size is the size of the figures
     * @param homefield_size is the size of the homefields
     * @param screen_width is the width of the screen the field is created in
     */
    public Figure_handler(RelativeLayout layout, int figure_count, int player_count, List<Integer> colors, int figure_size, int homefield_size, int screen_width, PlayerInformationTable playerInformationTable)
    {
        this.layout = layout;
        this.figure_count = figure_count;
        this.player_count = player_count;
        this.colors = colors;
        this.figure_size = figure_size;
        this.homefield_size = homefield_size;
        this.screen_width = screen_width;
        this.playerInformationTable = playerInformationTable;

        this.viewModel =  MainActivity.getGameboardViewModel();
    }
    public void setLayout(RelativeLayout layout)
    {
        this.layout = layout;
    }

    public RelativeLayout getLayout()
    {
        return layout;
    }

    public void setFigure_count(int figure_count)
    {
        this.figure_count = figure_count;
    }

    public int getFigure_count()
    {
        return figure_count;
    }

    public void setPlayer_count(int player_count)
    {
        this.player_count = player_count;
    }

    public int getPlayer_count()
    {
        return player_count;
    }

    public void setColors(List<Integer> colors)
    {
        this.colors = colors;
    }

    public void setFigure_size(int figure_size)
    {
        this.figure_size = figure_size;
    }

    public int getFigure_size()
    {
        return figure_size;
    }


    /** creates figures in the layout
     *
     */

    public void create_figures()
    {
        for (int j = 0; j < player_count; j++) //for each player
        {
            int pieceId = 0; //id for piece
            for (int i = 0; i < figure_count; i++)  //create figure_count figures
            {
                Figure figure = new Figure(pieceId, j, screen_width,screen_width ,figure_size); //instanciating new figure
                figure.createFigure(layout, colors.get(j));
                pieceId++;//increasing pieceId by 1;
            }
        }

    }

    /** moves the figure to the given position
     *
     * @param playernumber is the number of the player for finding the right homefield
     * @param pieceId is the id of the figure
     * @param position is the position on the board, null if in House
     * @param isOnBench is true, if the figure is on the bench
     * @param inHousePosition is the position in the house, null if not in House
     * @param round is the current round
     */
    public void moveFigure(int playernumber, int pieceId, Integer position, boolean isOnBench, Integer inHousePosition, Integer round, Integer move)
    {

        ImageView figure = layout.findViewWithTag("figure" + playernumber + "_" + pieceId);         //finding the figure in the layout
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) figure.getLayoutParams();  //getting the layoutparams of the figure

        if (isOnBench)
        {
            layoutParams.setMargins(layout.getWidth(), layout.getWidth(), 0, 0);    //setting the layoutparams to the bench (bench is just out of screen -> invisible)
            viewModel.changeFigureInBankValue(playernumber, 1); //changing the value of figures in bank for the given player
            System.out.println("Figures in Bank:" + viewModel.getFiguresInBank());
        }
        else
        {
           if (position == null) //if the figure is in the house
           {
                        //getting the layoutparams of the homefield (containing the position)
               RelativeLayout.LayoutParams newpos = (RelativeLayout.LayoutParams) layout.findViewWithTag("homefield" + playernumber + "_" + inHousePosition).getLayoutParams();
                        //setting the margins of the figure to those of the new position (considering the size of the field and the figure since the figure doesnt neccessaily match the size of homefields atm)
               layoutParams.setMargins(newpos.leftMargin + newpos.width/2 - layoutParams.width/2, newpos.topMargin + newpos.width/2 - layoutParams.width/2, 0, 0);
                        //setting the Margins to the figure (repositioning it)
               figure.setLayoutParams(layoutParams);
           }
           else
           {
                        //getting the layoutparams of the field the figure wants to move to (containing the new position)
               RelativeLayout.LayoutParams newpos = (RelativeLayout.LayoutParams) layout.findViewWithTag("normal" + position).getLayoutParams();
                        //setting the Margins(position) of the figure to those of the new position
               layoutParams.setMargins(newpos.leftMargin + newpos.width - layoutParams.width, newpos.topMargin + newpos.width - layoutParams.width, 0 ,0 );
                        //setting the Margins to the figure (repositioning it)
               figure.setLayoutParams(layoutParams);
           }
        }
        ConstraintLayout constraintLayout = (ConstraintLayout) layout.getParent(); //getting the Layout the TextView for turns is in
        TextView turns = constraintLayout.findViewById(R.id.Turns); //getting the TextView for the turns
        //setting the text
        turns.setText("Move: " + move +"/" + viewModel.getGameInformation().getValue().getMaximumTotalMoves().toString());
        TextView rounds = constraintLayout.findViewById(R.id.Rounds); //getting the TextView for the rounds
        rounds.setText("Round: " + round.toString());
    }


}
