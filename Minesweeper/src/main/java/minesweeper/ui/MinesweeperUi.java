package minesweeper.ui;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import minesweeper.domain.Board;


public class MinesweeperUi extends JFrame {

    private final int FRAME_WIDTH = 390;
    private final int FRAME_HEIGHT = 440;

    
    public MinesweeperUi() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setTitle("Minesweeper");
        add(new Board());

    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            
            @Override
            public void run() {                
                JFrame ex = new MinesweeperUi();
                ex.setVisible(true);                
            }
        });
    }
}
