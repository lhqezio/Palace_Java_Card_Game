import java.util.Random;

public class Deck {
    private Card[] deckCard;
    private int length;
    private Random random;
    public Deck(int length){
        if(length<0||length>52){
            throw new IllegalArgumentException("Deck must contains at least 0 cards and up to 52 card");
        }
        this.length=length;
        deckCard = new Card[52];
        this.random = new Random();
    }
    public int length(){
        return this.length;
    }
    public Card get(int index){
        return deckCard[index];
    }
    public void equals(int index,Card card){
        if(index<0||index>=length){
            throw new ArrayIndexOutOfBoundsException("Index must be between 0 and "+(length-1));
        }
        this.deckCard[index]=card;
    }
    public void push(Card card){
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
        Deck unshuffled = new Deck(length);
        for(int i = 0;i<length;i++){
            unshuffled.equals(i, deckCard[i]);
        }
        for(int i = 0;i<unshuffled.length();i++){
            int r = random.nextInt(unshuffled.length()-i-1);
            deckCard[i]=unshuffled.get(r);
            unshuffled.pull(r);
        }
    }
}
