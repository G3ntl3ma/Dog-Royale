package com.example.myapplication;

import android.widget.RelativeLayout;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
@RunWith(RobolectricTestRunner.class)
public class Game_board_creatorTest {

    @Mock
    private RelativeLayout mockLayout;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateFields() {
        // Mock data
        int width = 100; // Replace with your desired width
        int player_count = 4; // Replace with your desired player_count
        int field_size = 30; // Replace with your desired field_size
        int figure_count = 2; // Replace with your desired figure_count
        List<Integer> colors = new ArrayList<>(); // Replace with your desired colors
        List<Integer> start_fields = new ArrayList<>(); // Replace with your desired start_fields
        List<Integer> card_draw_fields = new ArrayList<>(); // Replace with your desired card_draw_fields
        Integer maxRounds = 10; // Replace with your desired maxRounds

        Game_board_creator gameBoardCreator = new Game_board_creator(
                mockLayout,
                width,
                player_count,
                field_size,
                figure_count,
                colors,
                start_fields,
                card_draw_fields,
                maxRounds
        );

        gameBoardCreator.createFields();
    }
}
