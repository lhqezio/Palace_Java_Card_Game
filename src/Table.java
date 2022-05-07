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
                for (int z = 0; z < playingPile.length(); z++) {
                    s.append("   ").append(playingPile.getCard(z).toString());
                }
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
        Card toPlay = playerDeck[player][curDeck].getCard(selections[0]);
        Card curCard = playingPile.getCard(playingPile.length() - 1);
        int cardNum = playerDeck[player][curDeck].getCard(selections[0]).getValue();
        for (int selection : selections) {
            if (playerDeck[player][curDeck].getCard(selection).getValue() != cardNum) {
                System.out.println("Value from Card selections are not the same,try again");
                return false;
            }
        }
        if (toPlay.getValue() == 10) {
            playingPile.purge();
            for (int selection : selections) {
                playerDeck[player][curDeck].pull(selection);
            }
            System.out.println("Player "+(player+1)+" played 10, one more turn");
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
                playerDeck[player][curDeck].pull(selection);
            }
            str.append("\nTurn over");
            System.out.println(str);
        }
        return true;
    }
    public boolean play (char c,int player){
        boolean turnOver = false;
        switch(c){
            case 'd' -> {
                if(playingPile.length()==0){
                    System.out.println("Pile empty");
                }
                else {
                    for(int i = 0;i<playingPile.length();i++){
                        playerDeck[player][0].put(playingPile.getCard(i));
                        playingPile.purge();
                    }
                    turnOver=true;
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
                    Ai.think(playingPile.getCard(playingPile.length() - 1),playerDeck[player][curDeck]);
                }
            }
            default -> System.out.println("Invalid input");
        }
        return turnOver;
    }
    public void play (int player){

    }
}
