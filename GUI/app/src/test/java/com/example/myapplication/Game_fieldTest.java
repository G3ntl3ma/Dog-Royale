package com.example.myapplication;

import static org.junit.Assert.*;

import android.widget.RelativeLayout;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.exceptions.base.MockitoAssertionError;

public class Game_fieldTest {

    @Mock
    private Game_field game_field;
    private RelativeLayout relativeLayout;

    @Test
    public void create_field() {
        game_field.create_field();
        Mockito.verify(game_field).create_field();
    }
}