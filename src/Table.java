public class Table {
    private Deck mainDeck;
    private Deck playingPile;
    private int playerNum;
    private final int playing = 0;
    private final int hidden = 1;
    private final int showing = 2;
    private Deck[][] playerDeck;
    //For each player 0 would be playing deck,1 would be the showing deck and 2 would be the hidden one
    Table (int playerNum){
        if(playerNum<2||playerNum>4){
            throw new IllegalArgumentException("Player number must be between 2 and 4 ");
        }
        this.playerNum=playerNum;
        playerDeck = new Deck[playerNum][3];
        for(int i = 2;i<playerNum;i++){
            if(i==2){
                addNewDecktoMain();
                if(playerNum>2){
                    addNewDecktoMain();
                }
                mainDeck.shuffle();
            }
            for(int z=0;z<playerDeck[i].length;z++){
                distribute(i,z);
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

    private void distribute(int player,int deck){
        Card draw = mainDeck.getCard(0);
        mainDeck.pull(0);
        playerDeck[player][deck].put(draw);
    }
    public boolean draw(int player) {
        boolean success=false;
        if(!mainEmpty()){
            distribute(player,playing);
            success = true;
        }
        return success;
    }
    public boolean mainEmpty(){
        return mainDeck.length()==0;
    }
    public boolean winLose() {
        for(int i = 0;i<this.playerNum;i++){
            if(playerDeck[i][playing].length()==0){
                if(mainEmpty()){
                    if(playerDeck[i][showing].length()!=0){
                        for(int z = 0;z<playerDeck[i][showing].length();z++)
                        playerDeck[i][playing].put(playerDeck[i][showing].getCard(z));
                    }
                    else if()
                }
                else{
                    for(int z = 0;z<mainDeck.length()||z<3;z++){
                        draw(i);
                    }
                }
            }
        }
    }
}
