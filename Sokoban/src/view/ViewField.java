package view;

import model.Board;
import model.Level;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


/**
 * This class is panel with field.
 *
 * @author Aracturat (Nikolay Dozmorov)
 * @version 0.1
 */
public class ViewField extends JPanel {

    int windowHeight = 500;

    int windowWidth = 500;

    Board board;

    public ViewField(Board board, int x, int y) {
        this.board = board;

        this.setSize(x, y);
        this.setVisible(true);

        this.repaint();
    }


    public void paint(Graphics g)
    {
        Level level = board.getCurrentLevel();
        int boardSizeX = board.getSizeX();
        int boardSizeY = board.getSizeY();
        this.setBackground(Color.white);
        windowWidth = this.getWidth();
        windowHeight = this.getHeight();
        try {
            BufferedImage box = ImageIO.read(new File("res/box.jpg"));
            BufferedImage wall = ImageIO.read(new File("res/wall.gif"));
            BufferedImage space = ImageIO.read(new File("res/space.jpg"));
            BufferedImage special = ImageIO.read(new File("res/special.png"));
            BufferedImage man = ImageIO.read(new File("res/man.jpg"));

            int blockSizeWidth =  windowWidth / boardSizeX;
            int blockSizeHeight = windowHeight / boardSizeY;
            int blockSize;
            if (blockSizeHeight > blockSizeWidth) {
                blockSize = blockSizeWidth;
            } else {
                blockSize = blockSizeHeight;
            }

            windowWidth = blockSize * boardSizeX;
            windowHeight = blockSize * boardSizeY;
            this.setSize(new Dimension(windowWidth, windowHeight));

            for (int i = 0; i < boardSizeX; i++) {
                for (int j = 0; j < boardSizeY; j++) {
                    int x = i * blockSize;
                    int y = j * blockSize;
                    BufferedImage img;
                    switch (level.getTypeOfBlock(i, j)) {
                        case BOX:
                            g.setColor(Color.pink);
                            img = box;
                            break;

                        case SPACE:
                            g.setColor(Color.white);
                            img = space;
                            break;

                        case SPECIAL:
                            g.setColor(Color.red);
                            img = special;
                            break;

                        case PUSHER:
                            g.setColor(Color.blue);
                            img = man;
                            break;

                        case WALL:
                            g.setColor(Color.LIGHT_GRAY);
                            img = wall;
                            break;
                        default:
                            img = wall;
                            break;
                    }
                    g.drawImage(img, x, y, blockSize, blockSize, null);
                    //g.fill3DRect(x,y,blockSize,blockSize,true);
                }
        }
        } catch (IOException e) {

        }
    }


    public Dimension getPreferredSize() {
        return new Dimension(windowWidth, windowHeight);
    }
}
