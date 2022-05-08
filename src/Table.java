import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Table {
    private final Deck mainDeck;
    private final Deck playingPile;
    private final int playerNum;
    private final Deck[][] playerDeck;

    //For each player 0 would be playing deck,1 would be the showing deck and 2 would be the hidden one
    Table(int playerNum) {
        if (playerNum < 2 || playerNum > 5) {
            throw new IllegalArgumentException("Player number must be between 2 and 4 ");
        }
        int cardPerDeck = 3;
        //3 cards per deck like game rule
        this.playerNum = playerNum;
        this.playerDeck = new Deck[playerNum][3];
        //3 mentioned deck per person
        this.playingPile = new Deck();
        this.mainDeck = new Deck();
        //initialize various fields
        for (int f = 0; f < playerNum; f++) {
            for (int z = 0; z < playerDeck[f].length; z++) playerDeck[f][z] = new Deck();
        }
        addNewDeckToMain();
        if (playerNum > 2) {
            addNewDeckToMain();
        }
        //If there's more than 2 players use two decks instead of one.
        mainDeck.shuffle();
        for (int i = 0; i < playerDeck.length; i++) {
            for (int f = 0; f < playerDeck[i].length; f++) {
                for (int z = 0; z < cardPerDeck; z++) {
                    distribute(i, f);
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < playerNum; i++) {
            if (i == 0) {
                s.append("Playing Pile(").append(playingPile.length()).append(")");
                if (playingPile.length() != 0) {
                    s.append(":").append(playingPile.getCard(playingPile.length() - 1));
                }
                s.append("                     Drawing Deck(").append(mainDeck.length()).append(")");
                s.append("\n\n");
            }
            for (int b = 0; b < playerDeck[i].length - 1; b++) {
                if (b == 0) {
                    s.append("Player ").append(i + 1).append(": On hand(").append(playerDeck[i][b].length()).append(")");
                } else if (b == 1) {
                    s.append("\n          ").append("Showing(").append(playerDeck[i][b].length()).append(")");
                }
                for (int k = 0; k < playerDeck[i][b].length(); k++) {
                    s.append("   ").append(k + 1).append(") ").append(playerDeck[i][b].getCard(k).toString());
                }
            }
            s.append("\n           ").append("Hidden(");
            s.append(playerDeck[i][2].length()).append(")");
            s.append("\n\n");
        }
        return s.toString();
    }

    public void preGame(int player, int a, int b) {
        if (a > 2 || a < 0 || b > 2 || b < 0) {
            throw new IllegalArgumentException();
        }
        Card temp = playerDeck[player][0].getCard(a);
        playerDeck[player][0].pull(a);
        playerDeck[player][0].put(playerDeck[player][1].getCard(b));
        playerDeck[player][1].pull(b);
        playerDeck[player][1].put(temp);
    }


    private void addNewDeckToMain() {
        for (int z = 2; z <= 14; z++) {
            mainDeck.put(new Card(Card.suit.SPADE, z));
            mainDeck.put(new Card(Card.suit.HEART, z));
            mainDeck.put(new Card(Card.suit.CLUB, z));
            mainDeck.put(new Card(Card.suit.DIAMOND, z));
        }
        //To add a standard blackjack deck without joker
    }

    private void distribute(int player, int deck) {
        Card temp = mainDeck.getCard(0);
        mainDeck.pull(0);
        playerDeck[player][deck].put(temp);
    }

    public boolean play(int[] selections, int player) {
        int curDeck = 0;
        for (int i = 0; i < playerDeck[player].length; i++) {
            if (playerDeck[player][i].length() != 0) {
                curDeck = i;
                break;
            }
        }
        int cardNum = playerDeck[player][curDeck].getCard(selections[0]).getValue();
        for (int selection : selections) {
            if (playerDeck[player][curDeck].getCard(selection).getValue() != cardNum) {
                System.out.println("Value from Card selections are not the same,try again");
                return false;
            }
        }
        if(playingPile.length()==0){
            StringBuilder str = new StringBuilder();
            str.append("Player ").append(player+1).append(" played:  ");
            for (int selection : selections) {
                playingPile.put(playerDeck[player][curDeck].getCard(selection));
                str.append(playerDeck[player][curDeck].getCard(selection)).append(" ");
            }
            ArrayList<Integer> selectionsList = new ArrayList<>(selections.length);
            for (int i : selections)
            {
                selectionsList.add(i);
            }
            Collections.sort(selectionsList);
            int z = 0;
            for(Integer e:selectionsList){
                playerDeck[player][curDeck].pull(e-z);
                z++;
            }
            System.out.println(str);
            return true;
        }
        Card curCard = playingPile.getCard(playingPile.length() - 1);
        Card toPlay = playerDeck[player][curDeck].getCard(selections[0]);
        if (toPlay.getValue() == 10) {
            playingPile.purge();
            for (int selection : selections) {
                playerDeck[player][curDeck].pull(selection);
            }
            System.out.println("Player "+(player+1)+" played 10, one more turn");
            System.out.println(this);
            return false;
        } else if (toPlay.compareTo(curCard) < 0) {
            System.out.println("Card smaller than current card on pile, try again");
            return false;
        } else {
            StringBuilder str = new StringBuilder();
            str.append("Player ").append(player+1).append(" played:  ");
            for (int selection : selections) {
                playingPile.put(playerDeck[player][curDeck].getCard(selection));
                str.append(playerDeck[player][curDeck].getCard(selection)).append(" ");
            }
            ArrayList<Integer> selectionsList = new ArrayList<>(selections.length);
            for (int i : selections)
            {
                selectionsList.add(i);
            }
            Collections.sort(selectionsList);
            int z = 0;
            for(Integer e:selectionsList){
                playerDeck[player][curDeck].pull(e-z);
                z++;
            }
            System.out.println(str);
        }
        return true;
    }
    public boolean play (char c,int player){
        boolean turnOver = false;
        switch(c){
            case 'p' -> {
                if(playingPile.length()==0){
                    System.out.println("Pile empty");
                }
                else {
                    for(int i = 0;i<playingPile.length();i++){
                        playerDeck[player][0].put(playingPile.getCard(i));
                    }
                    playingPile.purge();
                    turnOver=true;
                }
            }
            case 'd' -> {
                if(playingPile.length()==0){
                    System.out.println("Draw deck empty");
                }
                else {
                    Card drew = mainDeck.getCard(0);
                    mainDeck.pull(0);
                    System.out.println("Player "+(player+1)+" drew "+drew);
                    if(drew.compareTo(playingPile.getCard(playingPile.length()-1))<0){
                        System.out.println("Drew card is smaller than current card on pile,picking the pile up");
                        playerDeck[player][0].put(drew);
                        for(int i = 0;i<playingPile.length();i++){
                            playerDeck[player][0].put(playingPile.getCard(i));
                        }
                        playingPile.purge();
                        turnOver=true;
                    }
                    else if(playingPile.length()==0|| !(drew.compareTo(playingPile.getCard(playingPile.length() - 1)) < 0)) {
                        System.out.println("Drew success");
                        playingPile.put(drew);
                        turnOver=true;
                    }
                }
            }
            case 'm' -> System.out.println(Palace.manual());
            case 'r' -> {
                int curDeck = 3;
                for (int i = 0; i < playerDeck[player].length-1; i++) {
                    if (playerDeck[player][i].length() != 0) {
                        curDeck = i;
                        break;
                    }
                }
                if (curDeck == 3){
                    System.out.println("No available deck");
                }
                else {
                    System.out.println(Ai.recommend(playingPile.getCard(playingPile.length() - 1),playerDeck[player][curDeck]));
                }
            }
            case 's' -> {
                int curDeck = 3;
                for (int i = 0; i < playerDeck[player].length-1; i++) {
                    if (playerDeck[player][i].length() != 0) {
                        curDeck = i;
                        break;
                    }
                }
                if (curDeck == 3){
                    System.out.println("No available deck");
                }
                else {
                    playerDeck[player][curDeck].sort();
                    System.out.println("Sorted");
                    System.out.println(this +"\nInput:");
                }
            }
            default -> System.out.println("Invalid input");
        }
        return turnOver;
    }
    public boolean verify(int currentPlayer) {
        boolean gameOver = false;
        if(playerDeck[currentPlayer][2].length()==0){
            gameOver=true;
        }
        else if(mainDeck.length()!=0){
            if(playerDeck[currentPlayer][0].length()<3){
                StringBuilder str = new StringBuilder();
                str.append("Player ").append(currentPlayer+1).append(" drew:");
                for(int i = 0;i<=(3-playerDeck[currentPlayer][0].length())&&mainDeck.length()!=0;i++){
                    playerDeck[currentPlayer][0].put(mainDeck.getCard(0));
                    str.append("  ").append(mainDeck.getCard(0));
                    mainDeck.pull(0);
                }
                System.out.println(str);
            }
        }
        return gameOver;
    }
}
