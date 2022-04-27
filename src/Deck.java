
import java.util.concurrent.ThreadLocalRandom;

//Deck class that contains all the necessary methods for the game. Length and index numbering is similar to the one of a usual Array
public class Deck {
    final private Card[] deckCard;
    private int length;
    public Deck(){
        length=0;
        //Every Deck is 0 at initialize, Growing,Desizing,Regrowing accordingly to the process of removing and adding card.
        deckCard = new Card[104];
    }
    public int length(){
        return this.length;
    }
    public Card getCard(int index){
        if(index<0||index>length){
            throw new ArrayIndexOutOfBoundsException("Index must be between 0 and "+(length-1));
        }
        return deckCard[index];
    }
    public void put(Card card){
        this.deckCard[length]=card;
        this.length++;
    }
    public void pull(int index){
        if(index<0||index>=length){
            throw new ArrayIndexOutOfBoundsException("Index must be between 0 and "+(length-1));
        }
        this.length--;
        while(index<length){
            deckCard[index]=deckCard[index+1];
            index++;
        }
    }
    private void swap(int index1,int index2){
        Card temp = deckCard[index1];
        deckCard[index1] = deckCard[index2];
        deckCard[index2] = temp;
    }
    public void sort(){
        for(int i=0;i<this.length-1;i++){
            if(deckCard[i].getValue()>deckCard[i+1].getValue()){
                swap(i, i+1);
                for(int z=i;z!=0;z--){
                    if(deckCard[z].getValue()<deckCard[z-1].getValue()){
                        swap(z, z-1);
                    }
                    else {
                        break;
                    }
                }
            }
        }
    }
    public void shuffle(){
        Deck unshuffled = new Deck();
        for(int i = 0;i<length;i++){
            unshuffled.put(deckCard[i]);
        }
        for(int i = 0;i<this.length();i++){
            int r = ThreadLocalRandom.current().nextInt(0, unshuffled.length());
            deckCard[i]=unshuffled.getCard(r);
            unshuffled.pull(r);
        }
    }
}
