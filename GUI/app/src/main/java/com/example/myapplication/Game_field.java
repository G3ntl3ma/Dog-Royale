package com.example.myapplication;

import android.graphics.PorterDuff;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.core.content.ContextCompat;

public class Game_field {

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
     * @param j is the int for the color and player number, normal fields just use 0
     **/

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




    public Game_field(RelativeLayout layout, int width, int x, int y, String type, int player, int id) {
        this.layout = layout;
        this.width = width;
        this.x = x;
        this.y = y;
        this.type = type;
        this.player = player;
        this.id = id;
        this.color = 0;
    }

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


    public void create_field() {
        ImageView imageView = new ImageView(this.layout.getContext()); //erstelle ImageView
        imageView.setImageResource(R.drawable.spielfeld); //hinzufügen von Bildressource
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(1, 1);
        params = new RelativeLayout.LayoutParams(this.width, this.width); //Größe des Bildes einstellen
        params.setMargins(this.x, this.y, 0, 0); //Position in layout einstellen (mithilfe von margins)
        String tag;
        imageView.setLayoutParams(params); // Position des Views setzen

        switch (type) {
            case "homefield": //wenn es ein homefield ist, gebe dem View den homefieldtag
                imageView.setColorFilter(ContextCompat.getColor(this.layout.getContext(), this.color), PorterDuff.Mode.MULTIPLY);
                tag = this.type + this.player + "_" + this.id;
                imageView.setTag(tag); //Füge der View einen Tag zu
                break;
            case "startfield": //wenn es ein startfield ist, gebe dem View die richtige Farbe
                tag = "normal" + this.id;
                imageView.setTag(tag);
                imageView.setColorFilter(ContextCompat.getColor(this.layout.getContext(), this.color), PorterDuff.Mode.MULTIPLY);
                break;
            default:    //wenn es ein normales Feld ist, gebe dem View den normaltag
                tag = this.type + this.id;
                imageView.setTag(tag); //Füge der View einen Tag zu
                break;
        }

        this.layout.addView(imageView); //füge dem Layout das View hinzu
    }
}
