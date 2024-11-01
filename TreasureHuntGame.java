import java.util.Random;
import java.util.Scanner;

public class TreasureHuntGame {

    // Grid dimensions
    private int gridSize;
    private int playerX, playerY;
    private int treasureX, treasureY;
    private boolean isGameRunning;
    private Random random;

    // Constructor
    public TreasureHuntGame(int gridSize) {
        this.gridSize = gridSize;
        this.random = new Random();
        initializeGame();
    }

    // Initialize game setup
    private void initializeGame() {
        isGameRunning = true;
        placePlayer();
        placeTreasure();
    }

    // Place player at a random location
    private void placePlayer() {
        playerX = random.nextInt(gridSize);
        playerY = random.nextInt(gridSize);
        System.out.println("Player starting at (" + playerX + ", " + playerY + ")");
    }

    // Place treasure at a random location
    private void placeTreasure() {
        treasureX = random.nextInt(gridSize);
        treasureY = random.nextInt(gridSize);
    }

    // Calculate distance to the treasure
    private int calculateDistance() {
        return Math.abs(playerX - treasureX) + Math.abs(playerY - treasureY);
    }

    // Display distance hint to player
    private void displayDistanceHint() {
        int distance = calculateDistance();
        System.out.println("You are " + distance + " steps away from the treasure.");
    }

    // Move the player in the specified direction
    private void movePlayer(String direction) {
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

    // Check if the player found the treasure
    private void checkForTreasure() {
        if (playerX == treasureX && playerY == treasureY) {
            System.out.println("Congratulations! You found the treasure!");
            isGameRunning = false;
        }
    }

    // Main game loop
    public void playGame() {
        Scanner scanner = new Scanner(System.in);

        while (isGameRunning) {
            System.out.println("Enter move (up, down, left, right): ");
            String move = scanner.nextLine();
            movePlayer(move);
        }

        scanner.close();
    }

    // Main method to start the game
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Welcome to the Treasure Hunt Game!");
        System.out.print("Enter grid size: ");
        int gridSize = scanner.nextInt();
        
        TreasureHuntGame game = new TreasureHuntGame(gridSize);
        game.playGame();
    }
}
