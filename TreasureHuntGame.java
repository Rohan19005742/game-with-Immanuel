import java.util.Random;
import java.util.Scanner;

public class TreasureHuntGame {

    private int gridSize;
    private int playerX, playerY;
    private int treasureX, treasureY;
    private boolean isGameRunning;
    private Random random;

    public TreasureHuntGame(int gridSize) {
        this.gridSize = gridSize;
        this.random = new Random();
        initializeGame();
    }

    private void initializeGame() {
        isGameRunning = true;
        placePlayer();
        placeTreasure();
    }

    private void placePlayer() { // user requirement 5
        playerX = random.nextInt(gridSize);
        playerY = random.nextInt(gridSize);
        System.out.println("Player starting at (" + playerX + ", " + playerY + ")");
    }

    private void placeTreasure() { // user requirement 9
        treasureX = random.nextInt(gridSize);
        treasureY = random.nextInt(gridSize);
    }

    private int calculateDistance() {
        return Math.abs(playerX - treasureX) + Math.abs(playerY - treasureY);
    }

    private void displayDistanceHint() { // user requirement 10
        int distance = calculateDistance();
        System.out.println("You are " + distance + " steps away from the treasure.");
    }

    private void movePlayer(String direction) { // user requirement 6, needs fixing
        switch (direction.toLowerCase()) {
            case "up":
                playerX = Math.max(0, playerX - 1);
                break;
            case "down":
                playerX = Math.min(gridSize - 1, playerX + 1);
                break;
            case "left":
                playerY = Math.max(0, playerY - 1);
                break;
            case "right":
                playerY = Math.min(gridSize - 1, playerY + 1);
                break;
            default:
                System.out.println("Invalid move. Use 'up', 'down', 'left', or 'right'.");
        }
        displayDistanceHint();
        checkForTreasure();
    }

    private void checkForTreasure() {
        if (playerX == treasureX && playerY == treasureY) {
            System.out.println("Congratulations! You found the treasure!"); // user requirement 2
            isGameRunning = false;
        }
    }

    public void playGame() {
        Scanner scanner = new Scanner(System.in);

        while (isGameRunning) {
            System.out.println("Enter move (up, down, left, right): ");
            String move = scanner.nextLine();
            movePlayer(move);
        }

        scanner.close();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Welcome to the Treasure Hunt Game!");
        System.out.print("Enter grid size: "); // user requirement 1
        int gridSize = scanner.nextInt();
        
        TreasureHuntGame game = new TreasureHuntGame(gridSize);
        game.playGame();
    }
}
