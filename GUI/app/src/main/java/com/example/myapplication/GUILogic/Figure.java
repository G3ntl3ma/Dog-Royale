package com.example.myapplication.GUILogic;

import androidx.core.content.ContextCompat;

import android.graphics.PorterDuff;
import android.media.Image;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.myapplication.R;

public class Figure {
    //the id of the figure
    public int id;
    //the id of the player the figure belongs to
    public int playernumber;
    //the x position of the figure
    public int x;
    //the y position of the figure
    public int y;
    //the width of the figure
    public int width;

    /**
     * Constructor for a figure
     * @param id is the id of the figure
     * @param playernumber is the id of the player the figure belongs to
     * @param x is the x position of the figure
     * @param y is the y position of the figure
     * @param width is the width of the figure
     */
    public Figure(int id, int playernumber, int x, int y, int width) {
        this.id = id;
        this.playernumber = playernumber;
        this.x = x;
        this.y = y;
        this.width = width;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getPlayernumber() {
        return playernumber;
    }
    public void setPlayernumber(int playernumber) {
        this.playernumber = playernumber;
    }
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }

    /**creates a figure in the layout
     *
     * @param layout is the layout to create the figure in
     * @param color is the color of the figure
     */
    public void createFigure(RelativeLayout layout, int color) {

        ImageView figure = new ImageView(layout.getContext());  //creating the View for the figure
        figure.setImageResource(R.drawable.figure);         //setting the image resource
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(this.width, this.width); //setting new layoutparams width width and height of figure
        params.setMargins(this.x, this.y, 0, 0); //setting margins to bottom of where to be created (bottom of layout)
        figure.setColorFilter(ContextCompat.getColor(layout.getContext(),color), PorterDuff.Mode.MULTIPLY); //setting the color of the player to the figure
        figure.setTag("figure" + playernumber + "_" +  id); //creating tag for the figure
        figure.setLayoutParams(params);     //setting the layoutparams
        layout.addView(figure);        //adding the figure to the layout
    }
}
