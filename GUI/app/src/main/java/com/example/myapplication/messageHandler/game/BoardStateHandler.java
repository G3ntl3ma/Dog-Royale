package GUI.app.src.main.java.com.example.myapplication.messageHandler.game;


import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.Figure_handler;
import com.example.myapplication.GameboardViewModel;
import com.example.myapplication.databinding.FragmentGameBoardBinding;
import com.example.myapplication.messages.game.BoardState;

public class BoardStateHandler {
    private GameboardViewModel viewModel;

    private FragmentGameBoardBinding binding;

    private int playerCount = viewModel.getPlayer_count().getValue();

    public void handle(com.example.myapplication.messages.game.BoardState message) {
        // TODO implement here
//
//        int[] FiguresInBank = new int[playerCount]; //playernumber - 1 is index for player
//
//        Figure_handler figure_handler = viewModel.getFigure_handler().getValue();
//        for (BoardState.Piece piece : message.getPieces()) {
//            figure_handler.moveFigure(piece.getClientId(), piece.getPosition(), piece.getIsOnBench(), piece.getInHousePosition());
//            if (piece.getIsOnBench()) {
//                FiguresInBank[piece.getClientId() - 1]++;
//            }
//        }
//        for (int i = 0; i < playerCount; i++) {
//        }
   };
}
