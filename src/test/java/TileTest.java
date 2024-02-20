import org.example.Tile;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TileTest {
    @Test
    public void tileTestFlip(){
        Tile tile = new Tile();
        assertFalse(tile.isBomb());
        assertFalse(tile.isFlipped());
        assertFalse(tile.isFlagged());
        assertEquals(0, tile.getNumber());
    }
}