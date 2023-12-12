package com.example.myapplication.GUILogic;

import android.graphics.PorterDuff;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.core.content.ContextCompat;

import com.example.myapplication.R;

public class Game_field {



    //the layout the View is added to. (only RelativeLayout works rn)
    private RelativeLayout layout;

    //the width and height of the field
    private int width;

    //the x position in the layout
    private int x;

    //the y position in the layout
    private int y;

    //the type of the field (normal/homefield/startfield)
    private String type;

    //the id of a player for a homefield, normal fields just use 0
    private int player;

    //the id you want to give the image.
    private int id;

    //the color of the field
    private int color;

    /**
     * Constructor for normal fields
     * @param layout is the layout the field is created in
     * @param width is the width of the field
     * @param x is the x position of the field
     * @param y is the y position of the field
     * @param id is the id you want to give the image
     */

    public Game_field(RelativeLayout layout, int width, int x, int y, int id) {
        this.layout = layout;
        this.width = width;
        this.x = x;
        this.y = y;
        this.type = "normal"; //sets type to normal to create normal fields
        this.id = id;
    }
    /**
     * Constructor for home/startfields
     * @param layout is the layout the field is created in
     * @param width is the width of the field
     * @param x is the x position of the field
     * @param y is the y position of the field
     * @param type is the type of the field (homefield/startfield)
     * @param player is the id of the player for creating homefields and the color. normal fields just use 0
     * @param id is the id you want to give the image
     * @param color is the color of the field
     */
    public Game_field(RelativeLayout layout, int width, int x, int y, String type, int player, int id, int color) {
        this.layout = layout;
        this.width = width;
        this.x = x;
        this.y = y;
        this.type = type;
        this.player = player;
        this.id = id;
        this.color = color;
    }


    public RelativeLayout getLayout() {
        return layout;
    }

    public void setLayout(RelativeLayout layout) {
        this.layout = layout;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    /**
     * creates the field in the layout
     */
    public void create_field() {
        ImageView imageView = new ImageView(this.layout.getContext()); //create ImageView
        imageView.setImageResource(R.drawable.spielfeld); //adding the image resource
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(1, 1);
        params = new RelativeLayout.LayoutParams(this.width, this.width); //size of picture
        params.setMargins(this.x, this.y, 0, 0); //set position in layoutParams (using Margins)
        String tag;
        imageView.setLayoutParams(params); // set position of image

        switch (type) { //test on type of field
            case "homefield": //if homefield
                imageView.setColorFilter(ContextCompat.getColor(this.layout.getContext(), this.color), PorterDuff.Mode.MULTIPLY);
                tag = this.type + this.player + "_" + this.id; //creates tag in format
                imageView.setTag(tag); //add Tag to View
                break;
            case "startfield": //if startfield
                tag = "normal" + this.id; //creates tag in format
                imageView.setTag(tag);  //Add Tag to View
                        //setting colofilter to the image with player color
                imageView.setColorFilter(ContextCompat.getColor(this.layout.getContext(), this.color), PorterDuff.Mode.MULTIPLY);
                break;
            default:    //if normal field add normal tag
                tag = this.type + this.id; //creates tag in format
                imageView.setTag(tag); //Add Tag to View
                break;
        }

        this.layout.addView(imageView); //add View to layout
    }
}
