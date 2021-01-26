import com.royalchess.engine.board.Board;
import com.royalchess.engine.board.BoardUtils;
import com.royalchess.gui.Table;

import java.io.IOException;

public class RoyalChess {
    public static void main(String[] args) throws IOException {
        Board board = Board.createStandardBoard();
        System.out.println(board);
        Table table = new Table();
    }
}
