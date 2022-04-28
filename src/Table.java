public class Table {
    private Deck mainDeck;
    private Deck playingPile;
    private Deck[][] playerDeck;
    Table (int playerNum){
        if(playerNum<2){
            throw new IllegalArgumentException("Player number can be negative");
        }
        playerDeck = new Deck[playerNum][3];
        for(int i = 0;i<playerNum;i++){
            if(i==0){
                addNewDecktoMain();
                if(playerNum>2){
                    addNewDecktoMain();
                }
                mainDeck.shuffle();
            }

        }
    }
    private void addNewDecktoMain(){
        for(int z = 2;z<=13;z++){
            mainDeck.put(new Card(Card.suit.SPADE,z));
            mainDeck.put(new Card(Card.suit.HEART,z));
            mainDeck.put(new Card(Card.suit.CLUB,z));
            mainDeck.put(new Card(Card.suit.CLUB,z));
        }
    }
    public boolean draw(int player) {

    }
}
