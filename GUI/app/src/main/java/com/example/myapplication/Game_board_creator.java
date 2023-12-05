package com.example.myapplication;

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
    private int width; //Breite des Spielfeldes

    private int player_count; //Anzahl der Spieler

    private int field_size; //Anzahl der Felder auf dem Spielfeld

    private int figure_count; //Anzahl der Figuren pro Spieler

    private int[] colors; //Array mit den Farben der Spieler

    private int[] start_fields; //Array mit den Startfeldern der Spieler

    private Tuple[] start_fields_position; //Array mit den Homefeldern der Spieler

    private int[] card_draw_fields;

    private int field_width; //width (and height) of the fields

    private int homefield_size; //width and height of the homefields


    // private FragmentGameBoardBinding binding;



    public Game_board_creator(RelativeLayout Layout, int width, int player_count, int field_size, int figure_count, int[] colors, int[] start_fields, int card_draw_fields[])
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
    public void setColors(int[] colors)
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
    public int[] getColors() {return this.colors;}

    /** returns the start fields
     *
     * @return
     */
    public int[] getStart_fields() {return this.start_fields;}

    public int[] getCard_draw_fields() {return this.card_draw_fields;}

    public void setCard_draw_fields(int[] card_draw_fields) {this.card_draw_fields = card_draw_fields;}

    /** sets the start fields
     *
     * @param start_fields
     */
    public void setStart_fields(int[] start_fields) {this.start_fields = start_fields;}

    public int getField_width() {return this.field_width * 2;}

    public int getHomefield_size() {return this.homefield_size;}

    /**
     * creates the fields on the board
     *
     **/
    public void createFields(){

    int player = 0; //Zählvariable für Spieler
        //if less than 20 fields are created set constant field size
        if (this.field_size<= 20)
        {
            this.field_width = this.width / 20;
        }
        else //else calculate field size based on number of fields
        {
            this.field_width = this.width / this.field_size;
        }
        CoordinateCalculator playingField = new CoordinateCalculator(this.field_size, this.player_count, this.width /2 - this.width / this.field_size); //calculates coordinates on a circle
        Tuple result = new Tuple(0,0); //Tuple in das die Werte der Koordinaten kommen
        for (int i = 0; i < this.field_size; i++) {
            result = playingField.calculateFloatCoordinates(i); //calculating x, y coordinates for field (still has to be adjusted because of View height and width and + half of the field width)
            int j =i;
            if (Arrays.stream(this.start_fields).anyMatch(x -> x == j)) { //falls Feld ein Startfeld ist
                Game_field field = new Game_field(this.layout, this.field_width * 2, (int) Math.round(result.getX() + this.width / 2 - ( this.field_width)), (int) Math.round(result.getY() + this.width / 2 - (this.field_width)), "startfield" , 0, i, colors[player]); //Create field (adjusting x and y coordinates)
                field.create_field(); //actually creates the field
                player++;
                start_fields_position[player - 1] = new Tuple(result.getX() + width/2, result.getY() + width/2);
                System.out.println("lalala" + result);
            }
            else { //sonst
                Game_field field = new Game_field(this.layout, this.field_width * 2, (int) Math.round(result.getX() + this.width / 2 - ( this.field_width)), (int) Math.round(result.getY() + this.width / 2 - (this.field_width)), "normal" , 0, i); //Create field (adjusting x and y coordinates)
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
        Tuple mid = new Tuple(this.width /2, this.width/2);
        for (int j = start_fields.length; j >=1; j--)
        {
            Tuple vek = new Tuple((mid.getX() - start_fields_position[j-1].getX()) , (mid.getY() - start_fields_position[j-1].getY()) ); //calculates the vector from the start field to the middle of the board
            if(field_size>20) {
                vek.setX(vek.getX() - 1/(vek.vek_length()/(2*this.width/field_size)) * vek.getX());
                vek.setY(vek.getY() - 1/(vek.vek_length()/(2*this.width/field_size)) * vek.getY());
            }
            else{
                vek.setX(vek.getX() - 1/(vek.vek_length()/(2*this.width/20)) * vek.getX());
                vek.setY(vek.getY() - 1/(vek.vek_length()/(2*this.width/20)) * vek.getY());
            }
            System.out.println("Hausfeld" + start_fields_position[j-1]);
            System.out.println("Länge: " + vek.vek_length());
            System.out.println("Vektor: " + vek);

            field_size = (int) Math.round(vek.vek_length()/(figure_count + 1));
            for (int k = 0; k<figure_count;k++) {
                Tuple pos = fh(vek, start_fields_position[j-1] , k , figure_count + 2 ); //calculates the position on the vector
                Game_field field = new Game_field(layout, field_size , (int) Math.round(pos.getX() - field_size/2), (int) Math.round(pos.getY() -  field_size/2), "homefield", j, k, colors[j - 1]); // erstellt das Feld
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
            offset = vek.vek_length()/(1.5 * this.width/20) ;
        }
        else {
            offset = vek.vek_length() / ( 1.5 * this.width / field_size);
        }
        //System.out.println(offset);
        return (new Tuple(home_x + 1/offset*vek_x + vek_x/n * i,home_y + 1/offset*vek_y + vek_y/n * i));
    }

    public void createCardDrawfields()
    {
        for (int i = 0; i< card_draw_fields.length; i++) {
            System.out.println("hi");
            ImageView imageView = layout.findViewWithTag("normal"+card_draw_fields[i]);
            System.out.println("testest");
            imageView.setImageResource(R.drawable.ziehfeld);
            System.out.println("hier fehler");
        }
    }
}
