import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameMapTest {

    @Test
    void mapLoadsWithCorrectDimensions() {
        assertEquals(20, GameMap.getHeight());
        assertEquals(20, GameMap.getWidth());
    }

    @Test
    void isInBoundsValidPositions() {
        assertTrue(GameMap.isInBounds(0, 0));
        assertTrue(GameMap.isInBounds(5, 5));
        assertTrue(GameMap.isInBounds(19, 19));
    }

    @Test
    void isInBoundsBoundaryEdges() {
        assertTrue(GameMap.isInBounds(0, 0));
        assertTrue(GameMap.isInBounds(19, 0));
        assertTrue(GameMap.isInBounds(0, 19));
        assertTrue(GameMap.isInBounds(19, 19));
    }

    @Test
    void isInBoundsOutOfBoundsNegative() {
        assertFalse(GameMap.isInBounds(-1, 0));
        assertFalse(GameMap.isInBounds(0, -1));
        assertFalse(GameMap.isInBounds(-1, -1));
    }

    @Test
    void isInBoundsOutOfBoundsPastSize() {
        assertFalse(GameMap.isInBounds(20, 0));
        assertFalse(GameMap.isInBounds(0, 20));
        assertFalse(GameMap.isInBounds(20, 20));
    }

    @Test
    void getTileGrass() {
        // Row 5: BggB_ggSggg... -> col 5 = 'g'
        assertEquals('g', GameMap.getTile(5, 5));
    }

    @Test
    void getTileBrickWall() {
        assertEquals('B', GameMap.getTile(0, 0));
    }

    @Test
    void getTileWater() {
        // Row 8: BgggggggggWWWWgggggB -> col 10 = 'W'
        assertEquals('W', GameMap.getTile(8, 10));
    }

    @Test
    void getTileStoneWall() {
        // Row 14: BSSSSSSSSSggg... -> col 1 = 'S'
        assertEquals('S', GameMap.getTile(14, 1));
    }

    @Test
    void isBlockingBrickWall() {
        assertTrue(GameMap.isBlocking(0, 0));   // B
    }

    @Test
    void isBlockingStoneWall() {
        assertTrue(GameMap.isBlocking(14, 1));  // S
    }

    @Test
    void isBlockingWater() {
        assertTrue(GameMap.isBlocking(8, 10));  // W
    }

    @Test
    void isBlockingGrassWalkable() {
        assertFalse(GameMap.isBlocking(1, 1));  // g
    }

    @Test
    void isBlockingDirtWalkable() {
        // Row 17: Bgg______________ggB -> col 5 = '_'
        assertFalse(GameMap.isBlocking(17, 5));  // _
    }

    @Test
    void isBlockingTreeWalkable() {
        // Row 10: BgggggtttggWWW... -> col 6 = 't'
        assertFalse(GameMap.isBlocking(10, 6));  // t
    }

    @Test
    void isBlockingWoodenBoardsWalkable() {
        // Row 12: BggwwwwwgggggggggggB -> col 3 = 'w'
        assertFalse(GameMap.isBlocking(12, 3));  // w
    }

    @Test
    void playerStartPositionIsWalkable() {
        assertFalse(GameMap.isBlocking(5, 5));
        assertTrue(GameMap.isInBounds(5, 5));
        assertEquals('g', GameMap.getTile(5, 5));
    }
}
