package com.example.DogRoyalClient;

import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.DogRoyalClient.GameInformationClasses.Order;
import com.example.DogRoyalClient.messages.game.BoardState;

import java.util.ArrayList;
import java.util.Arrays;

public class BoardUpdater {

    private GameboardViewModel viewModel;
    private Figure_handler figure_handler;
    private PlayerInformationTable playerInformationTable;
    private GameInformation gameInformation;

    private LinearLayout winnerOrder;

    public BoardUpdater()
    {

        this.viewModel = MainActivity.getGameboardViewModel();
        this.figure_handler = viewModel.getFigure_handler().getValue();
        this.playerInformationTable = viewModel.getPlayerInformationTable().getValue();
        this.gameInformation = viewModel.getGameInformation().getValue();
        ConstraintLayout layout = (ConstraintLayout) figure_handler.getLayout().getParent();
        this.winnerOrder = layout.findViewById(R.id.WinnerTable);
    }

    /**
     * Updates the board with the information from BoardState
     * @param boardState is the BoardState from which we get the information of the new boardState
     */
    public void UpdateBoard(BoardState boardState)
    {
        if (boardState.getGameOver())
        {

            System.out.println("1");
            TextView textView = new TextView(winnerOrder.getContext());

            System.out.println("1");
            textView.setText("Game Over");

            System.out.println("3");

        }
        System.out.println("1");
        viewModel.setFiguresInBank(new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0 ,0 )));
        //setting the the figures to their new position
        for (   BoardState.Piece piece : boardState.getPieces()) {

            System.out.println("2");
            figure_handler.moveFigure(piece.getClientId(), piece.getPieceId(), piece.getPosition(), piece.getIsOnBench(), piece.getInHousePosition(), boardState.getRound(), boardState.getMoveCount());

            System.out.println("OWO3");
            }
        //changing the FiguresInBank Info in the PlayerInformationTable
        for ( Order order : gameInformation.getPlayerOrder().getOrder()) {

            System.out.println("3");
        playerInformationTable.changeFigureInfo(order.getClientId(), viewModel.getFiguresInBank().getValue().get(gameInformation.getPlayerClientNumber(order.getClientId())));

            System.out.println("4");
            }

        System.out.println("5");
        //changing the CardsInHand Info in the PlayerInformationTable
        playerInformationTable.changeCardInfoDynamically(viewModel.getLastPlayer().getValue(), gameInformation);

        System.out.println("6");
        viewModel.setLastPlayer(boardState.getNextPlayer());


    }

}
