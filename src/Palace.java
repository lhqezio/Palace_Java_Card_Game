import java.util.InputMismatchException;
import java.util.Scanner;

public class Palace {
    public static void main(String[] args) {
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Palace\n1)Play\n2)Manual\n3)Quit");
            String choice = sc.nextLine().toLowerCase();
            switch (choice) {
                case "3", "quit" -> {
                    return;
                }
                case "2", "manual" -> System.out.println(manual());

                case "1", "play" -> {
                    System.out.println("How many human player (2-5) ?");
                    int numPlayer;
                    int bot=0;
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
                    if((5-numPlayer)>0){
                        System.out.println("How many bot (0-"+(5-numPlayer));
                        while (true) {
                            try {
                                bot = sc.nextInt();
                                if (bot < 0 || bot > (5-numPlayer)) {
                                    throw new InputMismatchException();
                                }
                                break;
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid input try again.");
                            }

                        }
                    }
                    Table t = new Table(numPlayer+bot);
                    Player p = new Player(numPlayer,bot);
                    System.out.println(t);
                    System.out.println("Phase 1: Sort your card");
                    int curPlayer = 0;
                    while (curPlayer < numPlayer) {
                        System.out.println("Player " + (curPlayer + 1) + " do you want to swap card(y/n)");
                        char ans = sc.next().charAt(0);
                        switch (ans) {
                            case 'y' -> {
                                nested:
                                while (true) {
                                    try {
                                        System.out.println("Player " + (curPlayer + 1) + "\nInput the card on the playing deck");
                                        int fcard = sc.nextInt() - 1;
                                        System.out.println("Input the card on the showing deck");
                                        int scard = sc.nextInt() - 1;
                                        t.preGame(curPlayer, fcard, scard);
                                        System.out.println(t);
                                        System.out.println("Are you done(y/n) ?");
                                        char c = sc.next().charAt(0);
                                        switch (c) {
                                            case 'y':
                                                curPlayer++;
                                                break nested;
                                            case 'n':
                                                break;
                                            default:
                                                throw new InputMismatchException();

                                        }
                                    } catch (InputMismatchException e) {
                                        System.err.println("Invalid value");
                                        sc.next();
                                    } catch (IllegalArgumentException e) {
                                        System.err.println("Either value is wrong or out of bounds, please try again.");
                                    }
                                }
                            }
                            case 'n' -> curPlayer++;
                            default -> System.err.println("Invalid value");
                        }
                    }
                    System.out.println("Play Order\n" + p.whoGoFirst());
                }
                default -> System.err.println("Invalid input please try again");
            }
        }
    }

    public static String manual() {
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