package com.example.myapplication;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
public class Game_fieldTest {

    @Mock
    private RelativeLayout mockLayout;
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateField_NormalField() {
        int width = 100;
        int x = 50;
        int y = 50;
        int id = 1;

        Game_field gameField = new Game_field(mockLayout, width, x, y, id);
        gameField.create_field();

        ImageView createdImageView = verifyImageViewCreation(width, x, y);
        assertEquals("normal1", createdImageView.getTag());
    }
    private ImageView verifyImageViewCreation(int width, int x, int y) {
        verify(mockLayout).addView(Mockito.any(ImageView.class));

        ArgumentCaptor<ImageView> imageViewCaptor = ArgumentCaptor.forClass(ImageView.class);
        verify(mockLayout).addView(imageViewCaptor.capture());

        ImageView createdImageView = imageViewCaptor.getValue();
        assertNotNull(createdImageView);

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) createdImageView.getLayoutParams();
        assertEquals(width, layoutParams.width);
        assertEquals(width, layoutParams.height);
        assertEquals(x, layoutParams.leftMargin);
        assertEquals(y, layoutParams.topMargin);

        return createdImageView;
    }
}
