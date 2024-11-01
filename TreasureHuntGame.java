import java.util.Random;
import java.util.Scanner;

class Grid {
    private int size;
    private int treasureX;
    private int treasureY;
    private Player player;

    public Grid(int size) {
        this.size = size;
        placeTreasure();
    }

    public void placeTreasure() {
        Random random = new Random();
        treasureX = random.nextInt(size);
        treasureY = random.nextInt(size);
    }

    public boolean isTreasureFound(int x, int y) {
        return x == treasureX && y == treasureY;
    }

    public int getTreasureDistance(int x, int y) {
        return Math.abs(x - treasureX) + Math.abs(y - treasureY);
    }

    public int getSize() {
        return size;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}

class Player {
    private int x;
    private int y;
    private Grid grid;

    public Player(Grid grid) {
        this.grid = grid;
        placeAtRandomPosition();
    }

    private void placeAtRandomPosition() {
        Random random = new Random();
        x = random.nextInt(grid.getSize());
        y = random.nextInt(grid.getSize());
        System.out.println("Player starting at (" + x + ", " + y + ")");
    }

    public void move(String direction) {
        switch (direction.toLowerCase()) {
            case "up":
                if (x > 0) x--;
                break;
            case "down":
                if (x < grid.getSize() - 1) x++;
                break;
            case "left":
                if (y > 0) y--;
                break;
            case "right":
                if (y < grid.getSize() - 1) y++;
                break;
            default:
                System.out.println("Invalid move. Use 'up', 'down', 'left', or 'right'.");
                return;
        }
        System.out.println("Player moved " + direction + ". Current position: (" + x + ", " + y + ")");
        displayDistanceHint();
        checkForTreasure();
    }

    private void displayDistanceHint() {
        int distance = grid.getTreasureDistance(x, y);
        System.out.println("You are " + distance + " steps away from the treasure.");
    }

    private void checkForTreasure() {
        if (grid.isTreasureFound(x, y)) {
            System.out.println("Congratulations! You found the treasure!");
            System.exit(0); // End the game
        }
    }
}

public class TreasureHuntGame {
    private Grid grid;
    private Player player;

    public TreasureHuntGame(int gridSize) {
        grid = new Grid(gridSize);
        player = new Player(grid);
        grid.setPlayer(player);
    }

    public void playGame() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Find the treasure by moving up, down, left, or right!");
        while (true) {
            System.out.print("Enter move (up, down, left, right): ");
            String direction = scanner.nextLine();
            player.move(direction);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Treasure Hunt Game!");
        System.out.print("Enter grid size: ");
        int gridSize = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        TreasureHuntGame game = new TreasureHuntGame(gridSize);
        game.playGame();

        scanner.close();
    }
}