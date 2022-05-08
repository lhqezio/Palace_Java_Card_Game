import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                        System.out.println("How many bot (0-"+(5-numPlayer)+")");
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
                    System.out.println(t);
                    boolean turnOver = false;
                    boolean gameOver = false;
                    int turn = 0;
                    int total = bot + numPlayer;
                    while(!gameOver){
                        System.out.println("Current Player: "+(p.currentPlayer(turn)+1));
                        System.out.println("Input numbers of card you want to play Ex:1 or 1,2,3 or s or d or p or m (manual)");
                        while (!turnOver) {
                            try {
                                String input = sc.nextLine();
                                if (input.length() == 1) {
                                    Pattern pattern = Pattern.compile("[0-9]");
                                    Matcher matcher = pattern.matcher(input);
                                    if (matcher.find()) {
                                        int[] selection = new int[]{Integer.parseInt(input)-1};
                                        turnOver = t.play(selection, p.currentPlayer(turn));
                                    } else {
                                        turnOver = t.play(input.charAt(0), p.currentPlayer(turn));
                                    }
                                } else if (input.length() > 1) {
                                    System.out.println(input);
                                    String[] inputSeparated = input.split(",");
                                    int[] selections = new int[inputSeparated.length];
                                    for (int i = 0; i < selections.length; i++) {
                                        selections[i] = Integer.parseInt(inputSeparated[i])-1;
                                    }
                                    turnOver = t.play(selections, p.currentPlayer(turn));
                                }
                            }
                            catch (NumberFormatException e){
                                System.err.println("Invalid choice(garbage input),try again");
                            }
                            catch (ArrayIndexOutOfBoundsException e){
                                System.err.println("Invalid Card number");
                            }
                        }
                        gameOver=t.verify(p.currentPlayer(turn));
                        turn++;
                        turnOver=false;
                        System.out.println("\n\n"+t);
                        if(turn==total){
                            turn=0;
                        }
                    }
                    
                    
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