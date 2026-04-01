import org.junit.jupiter.api.BeforeEach;
// Note: @Test imported fully-qualified below to avoid shadowing our 'Test' server class
import static org.junit.jupiter.api.Assertions.*;

class MoveHandlerTest {

    @BeforeEach
    void resetPlayerPosition() {
        Test.playerY = 5;
        Test.playerX = 5;
    }

    /**
     * Replicates the movement validation logic from MoveHandler.handle()
     * without starting an HTTP server.
     */
    private boolean tryMove(int dy, int dx) {
        if (Math.abs(dy) + Math.abs(dx) > 1) return false;
        int targetY = Test.playerY + dy;
        int targetX = Test.playerX + dx;
        if (!GameMap.isInBounds(targetY, targetX)) return false;
        if (GameMap.isBlocking(targetY, targetX)) return false;
        Test.playerY = targetY;
        Test.playerX = targetX;
        return true;
    }

    @org.junit.jupiter.api.Test
    void validMoveDown() {
        // (5,5) -> (6,5) = 'g', walkable
        assertTrue(tryMove(1, 0));
        assertEquals(6, Test.playerY);
        assertEquals(5, Test.playerX);
    }

    @org.junit.jupiter.api.Test
    void validMoveUp() {
        // (5,5) -> (4,5) = '_', walkable
        assertTrue(tryMove(-1, 0));
        assertEquals(4, Test.playerY);
        assertEquals(5, Test.playerX);
    }

    @org.junit.jupiter.api.Test
    void validMoveRight() {
        // (5,5) -> (5,6) = 'g', walkable
        assertTrue(tryMove(0, 1));
        assertEquals(5, Test.playerY);
        assertEquals(6, Test.playerX);
    }

    @org.junit.jupiter.api.Test
    void validMoveLeft() {
        // (5,5) -> (5,4) = '_', walkable
        assertTrue(tryMove(0, -1));
        assertEquals(5, Test.playerY);
        assertEquals(4, Test.playerX);
    }

    @org.junit.jupiter.api.Test
    void diagonalMovementRejected() {
        assertFalse(tryMove(1, 1));
        assertEquals(5, Test.playerY);
        assertEquals(5, Test.playerX);
    }

    @org.junit.jupiter.api.Test
    void diagonalNegativeMovementRejected() {
        assertFalse(tryMove(-1, -1));
        assertEquals(5, Test.playerY);
        assertEquals(5, Test.playerX);
    }

    @org.junit.jupiter.api.Test
    void multiStepMovementRejected() {
        assertFalse(tryMove(2, 0));
        assertEquals(5, Test.playerY);
        assertEquals(5, Test.playerX);
    }

    @org.junit.jupiter.api.Test
    void movementIntoBrickWallBlocked() {
        // Place player at (5,4)='_', move left -> (5,3)='B'
        Test.playerX = 4;
        assertFalse(tryMove(0, -1));
        assertEquals(4, Test.playerX);  // position unchanged
    }

    @org.junit.jupiter.api.Test
    void movementIntoStoneWallBlocked() {
        // Place player at (5,6)='g', move right -> (5,7)='S'
        Test.playerX = 6;
        assertFalse(tryMove(0, 1));
        assertEquals(6, Test.playerX);  // position unchanged
    }

    @org.junit.jupiter.api.Test
    void movementOutOfBoundsBlocked() {
        // Place player at row 0 (border), move up -> y=-1 (out of bounds)
        Test.playerY = 0;
        Test.playerX = 5;
        assertFalse(tryMove(-1, 0));
        assertEquals(0, Test.playerY);  // position unchanged
    }
}
