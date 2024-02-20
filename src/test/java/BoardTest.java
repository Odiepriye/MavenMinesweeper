import org.example.Board;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {
    private final Board board;
    public BoardTest(){
        board = new Board();
        board.normal();
    }
    @Test
    public void testInitialization() {
        assertEquals(10,board.getRow());
        assertEquals(8,board.getCol());
        assertEquals(10,board.viewFlags());
        assertEquals(70,board.getFlippables());
        assertFalse( "lost game", board.isGameLost());
        assertFalse( "won game", board.isGameWon());
        assertEquals("* \t\tFlip a Tile: Click on a tile to reveal what's underneath it.\n" +
                "* \t\tFlag a Tile: Right-click on a tile to mark it as a potential mine.\n" +
                "* \t\tClear Flag : Right-click again on a flagged tile to unflag it.\n" +
                "* \t\tWin the Game: Successfully reveal all non-mine tiles on the board without triggering any mines.\n" +
                "* \t\tLose the Game: Hit a mine and Loose.\n" +
                "* \t\tRestart Game: Clear the board and restart the game.\n", board.viewRules());
    }
    @Test
    public void testLeftClickTile() {
        assertFalse( "game has been started", board.isGameStarted());
        board.firstFlip((byte) 0, (byte) 0);
        assertFalse( "lost game", board.isGameLost());
        assertFalse( "won game", board.isGameWon());
        board.leftClickTile((byte) 1, (byte) 1);
        assertFalse( "lost game", board.isGameLost());
        assertFalse( "won game", board.isGameWon());
    }

    @Test
    public void testRightClickTile() {
        board.rightClickTile((byte) 4, (byte) 4);
        assertTrue( "didnt get flagged", board.tiles[4][4].isFlagged());
        assertEquals( "flags didnt reduce",9, board.viewFlags());
    }
    @Test
    public void testRestartGame() {
        board.restartGame();
        assertFalse( "lost game", board.isGameLost());
        assertFalse( "won game", board.isGameWon());
        assertFalse( "game has been started", board.isGameStarted());
        assertFalse( "numbers are set", board.isNumberSet());
        assertFalse(restartFlipTest());
    }
    public boolean restartFlipTest(){
        for (int i = 0; i < board.getRow(); i++) {
            for (int j = 0; j < board.getCol(); j++) {
                if (board.tiles[i][j].isFlipped()) {
                return true; // If any tile is flipped, return true immediately
                }
            }
        }
        return false;
    }
}
