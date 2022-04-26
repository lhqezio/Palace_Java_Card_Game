import Java.util.Scanner
public class UserInput{
  private scanner sc;
  private final String welMesg = "Please Input your option";
  private final String errMesg = "Invalid value,Please Try again";
  UserInput(){
    this.sc = new Scanner(System.in);
    }
  public int menu(){
    System.out.println(welMsg);
    while(sc<1||sc>4){
      try{
        int input = sc.nextInt();
        if(sc<1||sc>4){
          throws java.util.InputMismatchException;
        }
      }
      catch (java.util.InputMismatchException e){
        System.out.println(errMesg);
        sc.next();
      }
    }
    return input;
  }
}
    
    
    
      
