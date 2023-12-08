package com.example.myapplication;

import android.os.health.SystemHealthManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.List;

public class Figure_handler {
    private RelativeLayout layout; //Layout the figures are created in

    private int figure_count; //number of figures per player

    private int player_count; //number of players

    private List<Integer> colors; //colors for the players

    private int figure_size; //size of the figures

    private int homefield_size; //size of the homefields

    private int screen_width; //width of the screen



    public Figure_handler(RelativeLayout layout, int figure_count, int player_count, List<Integer> colors, int figure_size, int homefield_size, int screen_width)
    {
        this.layout = layout;
        this.figure_count = figure_count;
        this.player_count = player_count;
        this.colors = colors;
        this.figure_size = figure_size;
        this.homefield_size = homefield_size;
        this.screen_width = screen_width;
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


    /** creates the figures in the layout
     *
     */

    public void create_figures()
    {
        for (int j = 0; j < player_count; j++)
        {
            for (int i = 0; i < figure_count; i++)
            {
                Figure figure = new Figure(i, j, screen_width,screen_width ,figure_size);
                figure.createFigure(layout, colors.get(j));
            }
        }

    }

    /** moves the figure to the given position
     *
     * @param playernumber is the number of the player
     * @param pieceId is the id of the figure
     * @param position is the position on the board
     * @param isOnBench is true, if the figure is on the bench
     * @param inHousePosition is the position in the house
     */
    public void moveFigure(int playernumber, String pieceId, Integer position, boolean isOnBench, Integer inHousePosition)
    {

        ImageView figure = layout.findViewWithTag(pieceId);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) figure.getLayoutParams();

        if (isOnBench)
        {
            layoutParams.setMargins(layout.getWidth(), layout.getWidth(), 0, 0);
        }
        else
        {
           if (position == null)
           {
               RelativeLayout.LayoutParams newpos = (RelativeLayout.LayoutParams) layout.findViewWithTag("homefield" + playernumber + "_" + inHousePosition).getLayoutParams();
               layoutParams.setMargins(newpos.leftMargin + newpos.width/2 - layoutParams.width/2, newpos.topMargin + newpos.width/2 - layoutParams.width/2, 0, 0);
               figure.setLayoutParams(layoutParams);
           }
           else
           {
               RelativeLayout.LayoutParams newpos = (RelativeLayout.LayoutParams) layout.findViewWithTag("normal" + position).getLayoutParams();
               layoutParams.setMargins(newpos.leftMargin + newpos.width - layoutParams.width, newpos.topMargin + newpos.width - layoutParams.width, 0 ,0 );
               figure.setLayoutParams(layoutParams);
           }
        }
    }


}
