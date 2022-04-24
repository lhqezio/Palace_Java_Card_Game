import java.lang.StringBuilder;
public class Card {
    private suit suit;
    private int value;
    private enum suit { CLUB, HEART, SPADE, DIAMOND }
    Card(suit suit, int value){
        this.suit=suit;
        if(value<2||value>14){
            throw new IllegalArgumentException("Invalid value for card");
        } 
        else {
            this.value=value;
        }
    }
    public int getValue(){
        return this.value;
    }
    public suit getSuit(){
        return this.suit;
    }
    public String toString(){
        StringBuilder cardString=new StringBuilder();
        switch(value){
            case 2:case 3:case 4:case 5:case 6:case 7:case 8:case 9:case 10:cardString.append(this.value+" of ");break;
            case 11:cardString.append("Jack of ");break;
            case 12:cardString.append("Queen of ");break;
            case 13:cardString.append("King of ");break;
            case 14:cardString.append("Ace of ");break;
            default: break; //Exception handling done by Card class constructor 
        }
        switch(this.suit){
            case CLUB:cardString.append("Club");break;
            case HEART:cardString.append("Heart");break;
            case SPADE:cardString.append("Spade");break;
            case DIAMOND:cardString.append("Diamond");break;
            default:     break; //Exception handling done by Enumeration class
        }
        return cardString.toString();
    }
}