import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

class Board {
    private final int WINNING_POSITION = 100;
    private final Map<Integer, Integer> snakes;
    private final Map<Integer, Integer> ladders;

    public Board() {
        snakes = new HashMap<>();
        ladders = new HashMap<>();
        initializeBoard();
    }

    private void initializeBoard() {
        // Snakes (Head -> Tail)
        snakes.put(99, 7);
        snakes.put(92, 35);
        snakes.put(80, 54);
        snakes.put(70, 55);
        snakes.put(50, 2);

        // Ladders (Start -> End)
        ladders.put(4, 25);
        ladders.put(10, 29);
        ladders.put(22, 41);
        ladders.put(47, 88);
        ladders.put(65, 95);
    }

    public int getNewPosition(int position) {
        if (snakes.containsKey(position)) {
            System.out.println(" Oh no! A snake pulled you down to " + snakes.get(position));
            return snakes.get(position);
        } else if (ladders.containsKey(position)) {
            System.out.println(" Great! A ladder took you up to " + ladders.get(position));
            return ladders.get(position);
        }
        return position;
    }

    public boolean isWinningPosition(int position) {
        return position == WINNING_POSITION;
    }
}

class Player {
    private String name;
    private int position;

    public Player(String name) {
        this.name = name;
        this.position = 0;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public void move(int diceRoll, Board board) {
        if (position + diceRoll <= 100) {
            position += diceRoll;
            position = board.getNewPosition(position);
        } else {
            System.out.println("Roll is too high, you need exact " + (100 - position) + " to win.");
        }
    }

    public boolean hasWon(Board board) {
        return board.isWinningPosition(position);
    }
}

public class snakeLadderGame1 {
    private static final Random random = new Random();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to Snake and Ladder Game! ");
        
        System.out.print("Enter Player 1 Name: ");
        Player player1 = new Player(scanner.nextLine());

        System.out.print("Enter Player 2 Name: ");
        Player player2 = new Player(scanner.nextLine());

        Board board = new Board();
        playGame(player1, player2, board);
    }

    private static void playGame(Player player1, Player player2, Board board) {
        Player currentPlayer = player1;
        
        while (true) {
            System.out.println("\n " + currentPlayer.getName() + "'s turn! Press Enter to roll the dice...");
            scanner.nextLine();
            
            int diceRoll = rollDice();
            System.out.println(" You rolled a " + diceRoll);

            currentPlayer.move(diceRoll, board);
            System.out.println(" " + currentPlayer.getName() + " is now at position: " + currentPlayer.getPosition());

            if (currentPlayer.hasWon(board)) {
                System.out.println(" Congratulations " + currentPlayer.getName() + "! You reached 100 and won the game! ");
                break;
            }
            
            // Switch player turn
            currentPlayer = (currentPlayer == player1) ? player2 : player1;
        }
    }

    private static int rollDice() {
        return random.nextInt(6) + 1;
    }
}
