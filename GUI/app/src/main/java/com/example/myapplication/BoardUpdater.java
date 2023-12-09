package com.example.myapplication;

import com.example.myapplication.GameInformationClasses.Order;
import com.example.myapplication.messages.game.BoardState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BoardUpdater {

    private GameboardViewModel viewModel;
    private Figure_handler figure_handler;
    private PlayerInformationTable playerInformationTable;
    private GameInformation gameInformation;

    public BoardUpdater()
    {

        this.viewModel = MainActivity.getGameboardViewModel();
        this.figure_handler = viewModel.getFigure_handler().getValue();
        this.playerInformationTable = viewModel.getPlayerInformationTable().getValue();
        this.gameInformation = viewModel.getGameInformation().getValue();
    }

    /**
     * Updates the board with the information from BoardState
     * @param boardState is the BoardState from which we get the information of the new boardState
     */
    public void UpdateBoard(BoardState boardState)
    {
        viewModel.setFiguresInBank(new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0 ,0 )));
        //setting the the figures to their new position
        for (   BoardState.Piece piece : boardState.getPieces()) {
            figure_handler.moveFigure(piece.getClientId(), piece.getPieceId(), piece.getPosition(), piece.getIsOnBench(), piece.getInHousePosition(), boardState.getRound(), boardState.getMoveCount());

            System.out.println("OWO3");
            }
        //changing the FiguresInBank Info in the PlayerInformationTable
        for ( Order order : gameInformation.getPlayerOrder().getOrder()) {
        playerInformationTable.changeFigureInfo(order.getClientId(), viewModel.getFiguresInBank().getValue().get(gameInformation.getPlayerClientNumber(order.getClientId())));
            }
        //changing the CardsInHand Info in the PlayerInformationTable
        playerInformationTable.changeCardInfoDynamically(viewModel.getLastPlayer().getValue(), gameInformation);
        viewModel.setLastPlayer(boardState.getNextPlayer());
    }

}
