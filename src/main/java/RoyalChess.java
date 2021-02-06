import com.royalchess.gui.ChessBoard;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class RoyalChess {
    public static void main(String[] args) throws IOException {
        JFrame frame = new ChessBoard();
        final BufferedImage image = ImageIO.read(new File("../art/misc/royallogo.png"));
        Image dimg = image.getScaledInstance(40 ,40, Image.SCALE_SMOOTH);
        frame.setIconImage(dimg);
        frame.setTitle("RoyalChess");
        frame.setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable( false );
        frame.pack();
        frame.setLocationRelativeTo( null );
        frame.setVisible(true);
    }
}
