package com.example.myapplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import android.app.Fragment;
import android.content.Context;
import android.graphics.PorterDuff;
import android.icu.text.RelativeDateTimeFormatter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.core.content.ContextCompat;


import com.example.myapplication.databinding.FragmentGameBoardBinding;


public class Game_board_creator{
    private RelativeLayout layout;
    private int width; //Width of the board the fields are created in

    private int player_count; //number of players

    private int field_size; //number of normal fields to be created (including card draw fields)

    private int figure_count; //number of figures per player

    private List<Integer> colors; //List with colors for the players

    private List<Integer> start_fields; //List with the start fields

    private Tuple[] start_fields_position; //Array with the start fields positions

    private List<Integer> card_draw_fields; //List with the card draw fields

    private int field_width; //width (and height) of the fields

    private int homefield_size; //width and height of the homefields


    // private FragmentGameBoardBinding binding;


    /** Constructor
     *
     * @param Layout is the layout the fields are created in
     * @param width is the width of the board the fields are created in
     * @param player_count is the number of players
     * @param field_size is the number of fields on the board
     * @param figure_count is the number of figures per player
     * @param colors is the list of colors for the players
     * @param start_fields is the list of start fields
     * @param card_draw_fields is the list of card draw fields
     */
    public Game_board_creator(RelativeLayout Layout, int width, int player_count, int field_size, int figure_count, List<Integer> colors, List<Integer> start_fields, List<Integer> card_draw_fields)
    {

        this.layout = Layout;
        this.width = width;
        this.player_count = player_count;
        this.field_size = field_size;
        this.figure_count = figure_count;
        this.colors = colors;
        this.start_fields = start_fields;
        this.start_fields_position = new Tuple[player_count];
        this.card_draw_fields = card_draw_fields;
    }


    /** Sets the Layout to create in
     *
     * @param layout
     */
    public void setLayout(RelativeLayout layout)
    {
        this.layout = layout;
    }

    /** Sets the Width of the board the fields are created in
     *
     * @param width
     */
    public void setWidth(int width)
    {
        this.width = width;
    }

    /** Sets the player count
     *
     * @param player_count
     */
    public void setPlayer_count(int player_count)
    {
        this.player_count = player_count;
    }

    /** Set the number of fields
     *
     * @param field_size is the number of fields on the board
     */
    public void setField_size(int field_size)
    {
        this.field_size = field_size;
    }

    /** sets the number of figures, that have to be created per player
     *
     * @param figure_count
     */
    public void setFigure_count(int figure_count)
    {
        this.figure_count = figure_count;
    }

    /** sets the colors for the players
     *
     * @param colors
     */
    public void setColors(List<Integer> colors)
    {
        this.colors = colors;
    }

    /** returns the layout
     *
     * @return the layout
     */
    public RelativeLayout getLayout() {return this.layout;}

    /** returns the width of the board
     *
     * @return the width
     */
    public int getWidth() {return this.width;}

    /** returns the number of players
     *
     * @return
     */
    public int getPlayer_count() {return this.player_count;}

    /** returns the number of fields
     *
     * @return
     */
    public int getField_size() {return this.player_count;}
    /** returns the number of figures per player
     *
     * @return
     */
    public int getFigure_count() {return this.figure_count;}

    /**
     *
     * @return the colors
     */
    public List<Integer> getColors() {return this.colors;}

    /** returns the start fields
     *
     * @return
     */
    public List<Integer> getStart_fields() {return this.start_fields;}

    public List<Integer> getCard_draw_fields() {return this.card_draw_fields;}

    public void setCard_draw_fields(List<Integer> card_draw_fields) {this.card_draw_fields = card_draw_fields;}

    /** sets the start fields
     *
     * @param start_fields
     */
    public void setStart_fields(List<Integer> start_fields) {this.start_fields = start_fields;}

    public int getField_width() {return this.field_width * 2;}

    public int getHomefield_size() {return this.homefield_size;}

    /**
     * creates the fields on the board
     *
     **/
    public void createFields(){

    int player = 0; //count variable for the players
        //if less than 20 fields are created set constant field size
        if (this.field_size<= 20)
        {
            this.field_width = this.width / 20;
        }
        else //else calculate field size based on number of fields
        {
            this.field_width = this.width / this.field_size;
        }
        //calculator for the coordinates on a circle
        CoordinateCalculator playingField = new CoordinateCalculator(this.field_size, this.player_count, this.width /2 - this.width / this.field_size); //calculates coordinates on a circle
        Tuple result = new Tuple(0,0); //Tuple to calculate the position in
        for (int i = 0; i < this.field_size; i++) { //for loop to create the fields
            result = playingField.calculateFloatCoordinates(i); //calculating x, y coordinates for field
            int j =i;
            if (start_fields.contains(j)) { //if field is a start field
                Game_field field = new Game_field(this.layout, this.field_width * 2, (int) Math.round(result.getX() + this.width / 2 - ( this.field_width)), (int) Math.round(result.getY() + this.width / 2 - (this.field_width)), "startfield" , 0, i, colors.get(player)); //Create field (adjusting x and y coordinates)
                field.create_field(); //actually creates the field
                player++;
                start_fields_position[player - 1] = new Tuple(result.getX() + width/2, result.getY() + width/2); //saves the position of the start field
            }
            else { //sonst
                Game_field field = new Game_field(this.layout, this.field_width * 2, (int) Math.round(result.getX() + this.width / 2 - ( this.field_width)), (int) Math.round(result.getY() + this.width / 2 - (this.field_width)), i); //Create field (adjusting x and y coordinates)
                field.create_field(); //actually creates the field
            }


        }
        createHomeFields(); //create home fields

        createCardDrawfields(); //create card draw fields


    }
    /**
     *creates the home fields for the players
     *
     **/
    public void createHomeFields(){
        Tuple mid = new Tuple(this.width /2, this.width/2); //getting coordinates for middle of the layout
        for (int j = 0; j <start_fields.size(); j++) //for each player/start field create home field per figure
        {       //getting the vector to create the home fields on
            Tuple vek = new Tuple((mid.getX() - start_fields_position[j].getX()) , (mid.getY() - start_fields_position[j].getY()) ); //calculates the vector from the start field to the middle of the board
            if(field_size>20) {     //if more than 20 fields are created
                        //adjusting the vektor to the right length so the different vectors dont cross in the middle
                vek.setX(vek.getX() - 1/(vek.vek_length()/(2*this.width/field_size)) * vek.getX());
                vek.setY(vek.getY() - 1/(vek.vek_length()/(2*this.width/field_size)) * vek.getY());
            }
            else{                   //else is for considering the size of the normal fields if leq 20 fields are created
                vek.setX(vek.getX() - 1/(vek.vek_length()/(2*this.width/20)) * vek.getX());
                vek.setY(vek.getY() - 1/(vek.vek_length()/(2*this.width/20)) * vek.getY());
            }

            int field_width = (int) Math.round(vek.vek_length()/(figure_count + 1)); //getting field_width for the home fields
            for (int k = 0; k<figure_count;k++) { //for each figure create a home field
                Tuple pos = fh(vek, start_fields_position[j] , k , figure_count + 2 ); //calculates the position fpr the kth size on the vector
                        //creates the new field considering its own width and height
                Game_field field = new Game_field(layout, field_width , (int) Math.round(pos.getX() - field_width/2), (int) Math.round(pos.getY() -  field_width/2), "homefield", j, k, colors.get(j)); // erstellt das Feld
                        //actually instanciates the field in the layout
                field.create_field();
            }
        }
    }
    /**
     *calculates the i-th of n points on a vector
     *
     * @param vek is the vector
     * @param home is the start point
     * @param i is the i-th point
     * @param n is the number of points
     **/
    public Tuple fh ( Tuple vek, Tuple home, int i, int n)
    {
        double home_x = home.getX() ;
        double home_y = home.getY() ;
        double vek_x = vek.getX();
        double vek_y = vek.getY();
        double offset;

        if (field_size<= 20){
            offset = vek.vek_length()/(1.5 * this.width/20) ; //calculates offset to consider the start field and not create the first home field ontop of it
        }
        else {
            offset = vek.vek_length() / ( 1.5 * this.width / field_size); //calculates offset to consider the start field and not create the first home field ontop of it
        }
            //returns the position of the i-th point on the vector considering the offset to not create first field ontop of start field
        return (new Tuple(home_x + 1/offset*vek_x + vek_x/n * i,home_y + 1/offset*vek_y + vek_y/n * i));
    }

    /**
     *creates the card draw fields
     *
     **/
    public void createCardDrawfields()
    {
        for (int i = 0; i< card_draw_fields.size(); i++) {
            ImageView imageView = layout.findViewWithTag("normal" + card_draw_fields.get(i)); //gets the field with the tag
            imageView.setImageResource(R.drawable.ziehfeld); //changes the image of the card draw field
        }
    }
}
