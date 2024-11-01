import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

class Grid {
    private int size;
    private int treasureX;
    private int treasureY;
    private int fakeTreasureX;
    private int fakeTreasureY;
    private ArrayList<Monster> monsters;

    public Grid(int size) {
        this.size = size;
        this.monsters = new ArrayList<>();
        placeTreasure();
        placeFakeTreasure();
    }

    public void placeTreasure() { // user req 9
        Random random = new Random();
        treasureX = random.nextInt(size);
        treasureY = random.nextInt(size);
    }

    public void placeFakeTreasure() {
        Random random = new Random();
        do {
            fakeTreasureX = random.nextInt(size);
            fakeTreasureY = random.nextInt(size);
        } while (fakeTreasureX == treasureX && fakeTreasureY == treasureY); // Avoid placing the fake treasure at the real treasure's location
    }

    public boolean isTreasureFound(int x, int y) {
        return x == treasureX && y == treasureY;
    }

    public boolean isFakeTreasureFound(int x, int y) {
        return x == fakeTreasureX && y == fakeTreasureY;
    }

    public int getTreasureDistance(int x, int y) {
        return Math.abs(x - treasureX) + Math.abs(y - treasureY);
    }

    public int getFakeTreasureDistance(int x, int y) {
        return Math.abs(x - fakeTreasureX) + Math.abs(y - fakeTreasureY);
    }

    public int getSize() {
        return size;
    }

    public void addMonster(Monster monster) {
        monsters.add(monster);
    }

    public Monster checkForMonster(int x, int y) {
        for (Monster monster : monsters) {
            if (monster.isAtPosition(x, y)) {
                return monster;
            }
        }
        return null;
    }

    public ArrayList<Monster> getMonsters() {
        return monsters;
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

    private void placeAtRandomPosition() { // user req 5
        Random random = new Random();
        x = random.nextInt(grid.getSize());
        y = random.nextInt(grid.getSize());
        System.out.println("Player starting at (" + x + ", " + y + ")");
    }

    public void move(String direction) { // user req 6
        Random random = new Random();
        if (random.nextDouble() > 0.5) { 
            String[] directions = {"up", "down", "left", "right"};
            direction = directions[random.nextInt(directions.length)];
        }

        switch (direction.toLowerCase()) {
            case "up":
                if (y < grid.getSize() -1) y++;
                else System.out.println("reached end of grid, use another move");
                break;
            case "down":
                if (y > 0 ) y--;
                else System.out.println("reached end of grid, use another move");
                break;
            case "left":
                if (x > 0) x--;
                else System.out.println("reached end of grid, use another move");
                break;
            case "right":
                if (x < grid.getSize() - 1) x++;
                else System.out.println("reached end of grid, use another move");
                break;
            default:
                System.out.println("Invalid move. Use 'up', 'down', 'left', or 'right'.");
                return;
        }
        System.out.println("Player moved " + direction + ". Current position: (" + x + ", " + y + ")");
        displayDistanceHint();
        checkForTreasure();
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private void displayDistanceHint() { // user req 10
        int distance = grid.getTreasureDistance(x, y);
        int fakeTreasureDistance = grid.getFakeTreasureDistance(x, y);
        System.out.println("You are " + fakeTreasureDistance + " steps away from the treasure.");
        System.out.println("You are " + distance + " steps away from the treasure.");
    }

    public boolean checkForTreasure() {
        return grid.isTreasureFound(x, y);
    }

    public boolean checkForFakeTreasure() {
        return grid.isFakeTreasureFound(x, y);
    }
}

public class TreasureHuntGame {
    private Grid grid;
    private Player player;
    private boolean isGameRunning;
    private int moves_left;

    public TreasureHuntGame(int gridSize, int monsterCount) {
        grid = new Grid(gridSize);
        player = new Player(grid);
        isGameRunning = true;
        addMonsters(monsterCount);
        moves_left = gridSize * gridSize;
    }

    private void addMonsters(int count) { // user req 3
        String[] monsterTypes = {"scary", "friendly", "aggressive"}; // user req 7
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            String type = monsterTypes[random.nextInt(monsterTypes.length)];
            Monster monster = new Monster(grid.getSize(), type);
            grid.addMonster(monster);
        }
    }

    private void checkForMonsters() {
        Monster monster = grid.checkForMonster(player.getX(), player.getY());
        if (monster != null) {
            System.out.println(monster.greetPlayer());
            if (monster.getType().equals("scary") || monster.getType().equals("aggressive")) {
                System.out.println("You were caught by a monster. Game Over!");
                isGameRunning = false;
            }
        }
    }

    public void playGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Find the treasure by moving up, down, left, or right!");

        while (isGameRunning && moves_left > 0) {
            System.out.print("\n");
            System.out.print("Enter move (up, down, left, right): ");
            String direction = scanner.nextLine();
            player.move(direction);
            checkForMonsters();
            moves_left -= 1;

            if (player.checkForTreasure()) {
                System.out.println("Congratulations! You found the treasure!"); // user req 2
                isGameRunning = false;
                break;
            }

            if (player.checkForFakeTreasure()) {
                System.out.println("Unlucky! The treasure chest is empty!");
            }
        }
        if (moves_left <= 0){
            System.out.println("You took too long");
        }
        scanner.close();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Treasure Hunt Game!");

        System.out.print("Enter grid size: "); // user req 1
        int gridSize = scanner.nextInt();
        System.out.print("Enter number of monsters: ");
        int monsterCount = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        TreasureHuntGame game = new TreasureHuntGame(gridSize, monsterCount);
        game.playGame();

        scanner.close();
    }
}


// with a grid size of 100x100, 50 monsters, 0.5 stochastic prob
// ,moves limit of 10,000 and 1 fake treasure
// there is a probability of 0.00000000369% or 1 in 27 billion
// Getting hit by lightning twice in your life: The odds are around 1 in 9 million.
// safe to say, no one will beat this game at a high grid size.