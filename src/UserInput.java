import java.util.Scanner;
import java.util.InputMismatchException;
 
public class UserInput{
  private Scanner sc;
  private final String welMesg = "Please Input your option";
  private final String errMesg = "Invalid value,Please Try again";
  private final String man = "For game rules, visit: https://www.wikihow.com/Play-the-Palace-Card-Game\nType the numeration of the desired card(1-52)\ntype quit to quit at any point\nrec for recommedation\nman for manual ";
  UserInput(){
    this.sc = new Scanner(System.in);
  }
  public int menu(){
    System.out.println(welMesg);
    int input =  sc.nextInt();
    while(input<1||input>4){
      try{
        input = sc.nextInt();
        if(input<1||input>4){
          throw new InputMismatchException();
        }

      }
      catch (java.util.InputMismatchException e){
        System.out.println(errMesg);
        sc.next();
      }
    }
    return input;
  }
  public int inGame(){
    System.out.println(welMesg+" man for manual");
    while(true){
      String input =  sc.next();
      for (int i=1;i<53;i++){
        if(input.equals(String.valueOf(i))){
          return i;
        }
      }
      switch(input){
        case "quit":return 0;
        case "man":System.out.println(man);break;
        case "rec":break;
        default:System.out.println(errMesg);break;
      }
    }
  }
}
    
    
    
      
