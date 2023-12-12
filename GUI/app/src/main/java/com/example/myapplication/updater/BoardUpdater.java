package com.example.myapplication.updater;

import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.myapplication.DiscardPileViewModel;
import com.example.myapplication.GUILogic.Figure_handler;
import com.example.myapplication.GUILogic.GameInformation;
import com.example.myapplication.GameInformationClasses.Order;
import com.example.myapplication.GameboardViewModel;
import com.example.myapplication.MainActivity;
import com.example.myapplication.GUILogic.PlayerInformationTable;
import com.example.myapplication.R;
import com.example.myapplication.messages.game.BoardState;

import java.util.ArrayList;
import java.util.Arrays;

public class BoardUpdater {

    private GameboardViewModel viewModel;
    private Figure_handler figure_handler;
    private PlayerInformationTable playerInformationTable;
    private GameInformation gameInformation;

    private DiscardPileViewModel discardPileViewModel;

    private LinearLayout winnerOrder;

    public BoardUpdater()
    {

        this.viewModel = MainActivity.getGameboardViewModel();
        this.figure_handler = viewModel.getFigure_handler().getValue();
        this.playerInformationTable = viewModel.getPlayerInformationTable().getValue();
        this.gameInformation = viewModel.getGameInformation().getValue();
        this.discardPileViewModel = MainActivity.getDiscardPileViewModel();
        ConstraintLayout layout = (ConstraintLayout) figure_handler.getLayout().getParent();
        this.winnerOrder = layout.findViewById(R.id.WinnerTable);

    }

    /**
     * Updates the board with the information from BoardState
     * @param boardState is the BoardState from which we get the information of the new boardState
     */
    public void UpdateBoard(BoardState boardState) {

        //creates Leaderboard
        if (boardState.getGameOver()) {
            int counter = 1;
            for (Integer player : boardState.getWinnerOrder()) {
                TextView textView = new TextView(winnerOrder.getContext());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
                textView.setTextAppearance(R.style.Leaderboard);
                textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                switch (counter) {
                    case 1:


                        textView.setText("1. Winner: " + gameInformation.getPlayerOrder().getName(player));
                        textView.setTextColor(Color.parseColor("#FFFFC107"));
                        textView.setTextSize(20);
                        break;
                    case 2:
                        textView.setText(counter + "." + gameInformation.getPlayerOrder().getName(player));

                        textView.setTextSize(18);
                        textView.setTextColor(Color.parseColor("#C5DFDA"));
                        break;
                    case 3:
                        textView.setText(counter + "." + gameInformation.getPlayerOrder().getName(player));

                        textView.setTextSize(16);
                        textView.setTextColor(Color.parseColor("#BA4E26"));
                        break;

                    default:
                        textView.setText(counter + "." + gameInformation.getPlayerOrder().getName(player));
                        textView.setTextColor(Color.parseColor("#FF8FA19D"));
                }

                textView.setLayoutParams(params);
                textView.setTag("place" + counter);
                winnerOrder.addView(textView);


                counter++;

            }
            winnerOrder.setVisibility(LinearLayout.VISIBLE);
        }
        else {
                viewModel.setFiguresInBank(new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0, 0)));
                //setting the the figures to their new position
                for (BoardState.Piece piece : boardState.getPieces()) {

                    figure_handler.moveFigure(piece.getClientId(), piece.getPieceId(), piece.getPosition(), piece.getIsOnBench(), piece.getInHousePosition(), boardState.getRound(), boardState.getMoveCount());

                }
                //changing the FiguresInBank Info in the PlayerInformationTable
                for (Order order : gameInformation.getPlayerOrder().getOrder()) {
                    Integer playerNumber = viewModel.getFiguresInBank().getValue().get(gameInformation.getPlayerClientNumber(order.getClientId()));
                    playerInformationTable.changeFigureInfo(order.getClientId(), playerNumber);
                }



                //changing the CardsInHand Info in the PlayerInformationTable
               // playerInformationTable.changeCardInfoDynamically(viewModel.getLastPlayer().getValue(), gameInformation);

                viewModel.setLastPlayer(boardState.getNextPlayer()); //setting the last player to the next player



        }
        this.discardPileViewModel.set_DiscardPile(boardState.getDiscardPile());
    }

}
