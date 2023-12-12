package com.example.myapplication;

import static org.junit.Assert.*;

import android.widget.RelativeLayout;

import com.example.myapplication.GUILogic.Game_field;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

public class Game_fieldTest {

    @Mock
    private Game_field game_field;
    private RelativeLayout relativeLayout;

    @Test
    public void getLayout() {
        game_field.setLayout(relativeLayout);
        assertEquals(relativeLayout,game_field.getLayout());
    }

    @Test
    public void setLayout(){
        game_field.setLayout(relativeLayout);
        assertEquals(relativeLayout,game_field.getLayout());
    }


    @Test
    public void getWidth() {
        game_field.setWidth(1);
        assertEquals(1,game_field.getWidth());
    }

    @Test
    public void setWidth() {
        game_field.setWidth(1);
        assertEquals(1,game_field.getWidth());

    }

    @Test
    public void getX() {
        game_field.setX(1);
        assertEquals(1,game_field.getX());
    }

    @Test
    public void setX() {
        game_field.setX(1);
        assertEquals(1,game_field.getX());

    }

    @Test
    public void getY() {
        game_field.setY(1);
        assertEquals(1,game_field.getY());
    }

    @Test
    public void setY() {
        game_field.setY(1);
        assertEquals(1,game_field.getY());
    }

    @Test
    public void getType() {
        game_field.setType("egal");
        assertEquals("egal",game_field.getType());
    }

    @Test
    public void setType() {
        game_field.setType("egal");
        assertEquals("egal",game_field.getType());
    }

    @Test
    public void getPlayer() {
        game_field.setPlayer(1);
        assertEquals(1,game_field.getPlayer());
    }

    @Test
    public void setPlayer() {
        game_field.setPlayer(1);
        assertEquals(1,game_field.getPlayer());
    }

    @Test
    public void getId() {
        game_field.setId(1);
        assertEquals(1,game_field.getId());
    }

    @Test
    public void setId() {
        game_field.setId(1);
        assertEquals(1,game_field.getId());
    }

    @Test
    public void getColor() {
        game_field.setColor(1);
        assertEquals(1,game_field.getColor());
    }

    @Test
    public void setColor() {
        game_field.setColor(1);
        assertEquals(1,game_field.getColor());
    }

    @Test
    public void create_field() {
        game_field.create_field();
        Mockito.verify(game_field).create_field();
    }
}