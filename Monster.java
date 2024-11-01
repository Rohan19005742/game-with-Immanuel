import java.util.Random;

public class Monster {
    private int x, y;
    private String type;

    public Monster(int gridSize, String type) {
        this.type = type;
        placeRandomly(gridSize);
    }

    private void placeRandomly(int gridSize) {
        Random random = new Random();
        this.x = random.nextInt(gridSize);
        this.y = random.nextInt(gridSize);
    }

    public boolean isAtPosition(int playerX, int playerY) {
        return this.x == playerX && this.y == playerY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getType() {
        return type;
    }

    public String greetPlayer() { // user req 11
        switch (type.toLowerCase()) {
            case "scary":
                return "A scary monster appears! You feel a chill down your spine!";
            case "friendly":
                return "A friendly monster waves at you!";
            case "aggressive":
                return "An aggressive monster roars and charges towards you!";
            default:
                return "A mysterious monster lurks here...";
        }
    }
}
