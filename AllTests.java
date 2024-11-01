import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

class AllTests {
    private Grid grid;
    private Player player;
    private Monster monster;
    private TreasureHuntGame game;

    @BeforeEach
    void setUp() {
        grid = new Grid(5); // Testing with a 5x5 grid
        player = new Player(grid);
        monster = new Monster(5, "scary");
        game = new TreasureHuntGame(5, 2); // Testing with 2 monsters
    }

    @Test
    void testGridInitialization() {
        assertEquals(5, grid.getSize(), "Grid size should be 5");
        assertNotNull(grid.getMonsters(), "Monsters list should be initialized");
    }

    @Test
    void testPlaceTreasure() {
        grid.placeTreasure();
        assertTrue(grid.getTreasureDistance(0, 0) >= 0, "Treasure should be placed within the grid bounds");
    }

    @Test
    void testIsTreasureFound() {
        grid.placeTreasure();
        int distance = grid.getTreasureDistance(player.getX(), player.getY());
        if (distance == 0) {
            assertTrue(grid.isTreasureFound(player.getX(), player.getY()), "Treasure should be found at the player's location");
        } else {
            assertFalse(grid.isTreasureFound(player.getX(), player.getY()), "Treasure should not be found if the player is not on the treasure's location");
        }
    }

    @Test
    void testAddMonster() {
        grid.addMonster(monster);
        ArrayList<Monster> monsters = grid.getMonsters();
        assertEquals(1, monsters.size(), "There should be 1 monster in the grid");
    }

    @Test
    void testCheckForMonster() {
        grid.addMonster(monster);
        Monster foundMonster = grid.checkForMonster(monster.getX(), monster.getY());
        assertNotNull(foundMonster, "Monster should be found at its placed location");
    }

    @Test
    void testPlayerMove() {
        int startX = player.getX();
        int startY = player.getY();

        player.move("up");
        if (startY < grid.getSize() - 1) {
            assertEquals(startY + 1, player.getY(), "Player should move up correctly");
        }

        player.move("down");
        if (startY > 0) {
            assertEquals(startY, player.getY(), "Player should move down correctly");
        }

        player.move("left");
        if (startX > 0) {
            assertEquals(startX - 1, player.getX(), "Player should move left correctly");
        }

        player.move("right");
        if (startX < grid.getSize() - 1) {
            assertEquals(startX + 1, player.getX(), "Player should move right correctly");
        }
    }

    @Test
    void testDistanceHint() {
        int distance = grid.getTreasureDistance(player.getX(), player.getY());
        assertTrue(distance >= 0, "Distance to treasure should be non-negative");
    }

    @Test
    void testMonsterGreeting() {
        Monster scaryMonster = new Monster(5, "scary");
        Monster friendlyMonster = new Monster(5, "friendly");
        Monster aggressiveMonster = new Monster(5, "aggressive");

        assertEquals("A scary monster appears! You feel a chill down your spine!", scaryMonster.greetPlayer());
        assertEquals("A friendly monster waves at you!", friendlyMonster.greetPlayer());
        assertEquals("An aggressive monster roars and charges towards you!", aggressiveMonster.greetPlayer());
    }

    @Test
    void testGameOverOnMonsterEncounter() {
        Monster scaryMonster = new Monster(grid.getSize(), "scary");
        grid.addMonster(scaryMonster);

        boolean isGameOver = false;
        if (grid.checkForMonster(player.getX(), player.getY()) != null) {
            isGameOver = true;
        }
        
        assertTrue(isGameOver, "Game should be over if player encounters a scary or aggressive monster");
    }

    @Test
    void testTreasureHuntWinCondition() {
        int playerX = player.getX();
        int playerY = player.getY();

        grid.placeTreasure();
        if (grid.isTreasureFound(playerX, playerY)) {
            assertTrue(player.checkForTreasure(), "Player should win if they find the treasure");
        }
    }

    public static void main(String[] args) {
        AllTests test = new AllTests();
        
        try {
            test.setUp();
            test.testGridInitialization();
            test.testPlaceTreasure();
            test.testIsTreasureFound();
            test.testAddMonster();
            test.testCheckForMonster();
            test.testPlayerMove();
            test.testDistanceHint();
            test.testMonsterGreeting();
            test.testGameOverOnMonsterEncounter();
            test.testTreasureHuntWinCondition();

            System.out.println("All tests passed!");
        } catch (AssertionError e) {
            System.out.println("Test failed: " + e.getMessage());
        }
    }
}
