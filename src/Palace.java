import java.util.InputMismatchException;
import java.util.Scanner;
public class Palace {
    public static void main(String[] args) {
        while(true){
            Scanner sc = new Scanner(System.in);
            System.out.println("Palace\n1)Play\n2)Manual\n3)Quit");
            String choice = sc.nextLine().toLowerCase();
            switch (choice) {
                case "3":
                case "quit":
                    return;
                case "2":
                case "manual":
                    System.out.println(manual());
                    break;
                case "1":
                case "play":
                    System.out.println("How many player (2-5) ?");
                    int numPlayer;
                    while (true) {
                        try {
                            numPlayer = sc.nextInt();
                            if (numPlayer < 2 || numPlayer > 5) {
                                throw new InputMismatchException();
                            }
                            break;
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input try again.");
                        }

                    }
                    Table t = new Table(numPlayer);
                    System.out.println(t);

                    break;
                default:
                    System.err.println("Invalid input please try again");
                    break;
            }
        }
    }
    public static String manual(){
        return "Game rules can be found here: https://gamerules.com/rules/shithead-card-game/\nAt anypoint of the game excluding the main menu and player number:\nm for manual,r for recommendation,q to quit directly f to forfeit(1 player)";
    }
    public static void pause(int sec) {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}