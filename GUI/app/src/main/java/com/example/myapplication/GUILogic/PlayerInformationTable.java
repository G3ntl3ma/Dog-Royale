package com.example.myapplication.GUILogic;

import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.GUILogic.GameInformation;
import com.example.myapplication.GameInformationClasses.Order;
import com.example.myapplication.GameboardViewModel;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayerInformationTable {

    private LinearLayout layout;

    private List<Order> playerNames;

    private Integer playerCount;

    private Integer StartFigures;

    private Integer StartCards;

    private List<Integer> cardsInHand;
    private GameboardViewModel viewModel;


    /**
     * Constructor
     * @param layout is the layout the table is created in
     * @param playerNames is the list of player names plus their clientId in given Order
     * @param playerCount is the number of players
     * @param StartFigures is the number of figures in bank at the beginning
     * @param StartCards is the number of cards in hand at the beginning
     */
    public PlayerInformationTable(LinearLayout layout, List<Order> playerNames,  Integer playerCount, Integer StartFigures, Integer StartCards){
        this.layout = layout;
        this.playerNames = playerNames;
        this.playerCount = playerCount;
        this.StartFigures = StartFigures;
        this.StartCards = StartCards;

        this.viewModel = MainActivity.getGameboardViewModel();
        this.cardsInHand = new ArrayList<>(Arrays.asList(StartCards, StartCards, StartCards, StartCards, StartCards, StartCards, StartCards));
    }

    /**
     * Converts dp to px
     * @param value is the value in dp
     * @return the value in px
     */
    public int ConvertDpToPx(int value){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, layout.getContext().getResources().getDisplayMetrics());
    }

    /**
     * Creates the table with the player names, the number of cards in hand and the number of figures in bank
     */
    public void BuildTable(){

        for (Order name: playerNames) { //for every player in Order
            LinearLayout Player = new LinearLayout(layout.getContext()); //creates LinearLayout to hold Views for player
            Player.setOrientation(LinearLayout.VERTICAL);

            Player.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            Player.setMinimumHeight(ConvertDpToPx(75));
            Player.setPadding(ConvertDpToPx(5), ConvertDpToPx(5), ConvertDpToPx(5), ConvertDpToPx(5));
            Player.setMinimumWidth(ConvertDpToPx(100));

            Player.setTag("Player" + name.getClientId());

            System.out.println(Player.getTag());

            TextView PlayerText = new TextView(layout.getContext());
            PlayerText.setText(name.getName());
            PlayerText.setTextAppearance(R.style.gameBoardTheme);
            PlayerText.setTag("Player" + name.getClientId() + "Text");
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            PlayerText.setLayoutParams(layoutParams);
            Player.addView(PlayerText);

            TextView cardsInHand = new TextView(layout.getContext());
            cardsInHand.setText("Cards in Hand: " + "\n" + StartCards);
            cardsInHand.setTextAppearance(R.style.gameBoardTheme);
            cardsInHand.setTag("Player" + name.getClientId() + "Cards");
            LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams2.setMargins(0, ConvertDpToPx(10), 0, 0);
            cardsInHand.setLayoutParams(layoutParams2);
            Player.addView(cardsInHand);

            TextView figuresInBank = new TextView(layout.getContext());
            figuresInBank.setText("Figures in Bank: " + "\n" + StartFigures);
            figuresInBank.setTextAppearance(R.style.gameBoardTheme);
            figuresInBank.setTag("Player" + name.getClientId() + "Figures");

            System.out.println("gegebener Tag:" + figuresInBank.getTag());
            LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams3.setMargins(0, ConvertDpToPx(10), 0, 0);
            figuresInBank.setLayoutParams(layoutParams3);
            Player.addView(figuresInBank);

            layout.addView(Player);
        }
    }

    /**
     * Changes the value of figuresInBank and cardsInHand for the given amount for the given player
     * @param clientId is the number of the player
     * @param cardsInHand is the new value of cards in hand
     * @param figuresInBank is the new value of figures in bank
     */
    public void changePlayerInfo(Integer clientId, Integer cardsInHand, Integer figuresInBank){
        TextView cardsInHandView = layout.findViewWithTag("Player" + clientId + "Cards");
        cardsInHandView.setText("Cards in Hand: " + "\n" + cardsInHand);

        TextView figuresInBankView = layout.findViewWithTag("Player" + clientId + "Figures");
        figuresInBankView.setText("Figures in Bank: " + "\n" + figuresInBank);
    }
    /**
     * Changes the value of figuresInBank for the given amount for the given player
     * @param clientId is the number of the player
     * @param figuresInBank is the new value of figures in bank
     */
    public void changeFigureInfo(Integer clientId, Integer figuresInBank){
        //finds Textview for figures in bank for player
        TextView figuresInBankView = layout.findViewWithTag("Player" + clientId + "Figures");
        //sets the text of the TextView to the new value
        figuresInBankView.setText("Figures in Bank: " + "\n" + figuresInBank);
    }

    /**
     * Changes the value of figuresInBank for the given amount for the given player
     * @param clientId is the number of the player
     * @param cardsInHand is the new value of figures in bank
     */
    public void changeCardInfo(Integer clientId, Integer cardsInHand){
        //finds Textview for cards in hand for player
        TextView cardsInHandView = layout.findViewWithTag("Player" + clientId + "Cards");
        //sets the text of the TextView to the new value
        cardsInHandView.setText("Cards in Hand: " + "\n" + cardsInHand);
    }

    /**
     * Changes the value of figuresInBank for the given amount for the given player dynamically (everytime called -> -1 cards)
     * @param clientId is the number of the player
     * @param gameInformation is the Information we want to take ClientId to PlayerNumber Conversion from
     */
    public void changeCardInfoDynamically(Integer clientId, GameInformation gameInformation)
    {
        TextView cardsInHandView = layout.findViewWithTag("Player" + clientId + "Cards"); //finds the TextView for cards in Hand for player
        //sets the card value to card value -1 for player
        cardsInHand.set(gameInformation.getPlayerClientNumber(clientId), this.cardsInHand.get(gameInformation.getPlayerClientNumber(clientId)) - 1);
        //sets the text of the TextView to the new value
        cardsInHandView.setText("Cards in Hand: " + "\n" + this.cardsInHand.get(gameInformation.getPlayerClientNumber(clientId)));
    }
}
