
package minesweeper.domain;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class Board extends JPanel {
    
    private int sizeOfCell = 24;
    private int numberOfMines = 4;
    private int numberOfRows = 4;
    private int numberOfColumns = 4;
    private int[] board;
    private int mineSpace = 9;
    private int emptySpace = 0;
    private int cover = 10;
    private int mineWithCover = mineSpace + cover;
    private int mineWithMark = mineWithCover + cover;
    private int boardSize = numberOfColumns*numberOfRows;
    private int mark = 11;
    private Image[] images;
    private boolean gameOn;
    private int minesLeft;

    
    public Board() {             
        images = new Image[12];
        for (int i = 0; i<12; i++) {
            images[i] = (new ImageIcon("Images/" + i+ ".png")).getImage();
        }
        
        addMouseListener(new MinesAdapter());
        setMines();
        
    }
    
    public void setMines() {
        board = new int[boardSize];
        for (int i = 0; i<boardSize; i++) {
            board[i] = cover;
        }
        minesLeft = numberOfMines;
        gameOn = true;
        int cell = 0;
        int currentColumn;
        Random random;
        random = new Random();
        int space = 0;
        int i = 0;
        while (i<numberOfMines) {
            space = (int) (boardSize*random.nextDouble());
            if (space < boardSize && board[space] != mineWithCover) {
                currentColumn = space % numberOfColumns;
                board[space] = mineWithCover;
                i++;
                if (currentColumn > 0) {
                    cell = space -1 - numberOfColumns;
                    addNumber(cell, 0);
                    cell = space - 1;
                    addNumber(cell, 0);
                    cell = space + numberOfColumns - 1;
                    addNumber(cell, boardSize);                    
                }
                
                cell = space - numberOfColumns;
                addNumber(cell, 0);
                cell = space + numberOfColumns;
                addNumber(cell, boardSize);
                if (currentColumn < (numberOfColumns - 1)) {
                    cell = space - numberOfColumns + 1;
                    addNumber(cell, 0);                
                    cell = space + numberOfColumns + 1;
                    addNumber(cell, boardSize);
                    cell = space + 1;
                    addNumber(cell, boardSize);
                }
            
            }
        }
        
    }
    
    public void addNumber(int cell, int place) {
        if (place == 0) {
            if (cell >= 0) {
                if (board[cell] != mineWithCover) {
                    board[cell] += 1;
                }
            }
        } else {
            if (cell<place) {
                if (board[cell] != mineWithCover) {
                    board[cell] += 1;
                }
            }
        }
    }
    
    public void findEmptyCells(int j) {
        int currentColumn = j % numberOfColumns;
        int cell;

        if (currentColumn > 0) { 
            cell = j - numberOfColumns - 1;
            openEmpty(cell, 0);
            cell = j - 1;
            openEmpty(cell, 0);

            cell = j + numberOfColumns- 1;
            openEmpty(cell, boardSize);
        }

        cell = j - numberOfColumns;
        openEmpty(cell, 0);
        cell = j + numberOfColumns;
        openEmpty(cell, boardSize);
        if (currentColumn < (numberOfColumns - 1)) {
            cell = j - numberOfColumns + 1;
            openEmpty(cell, 0);
            cell = j + numberOfColumns + 1;
            openEmpty(cell, boardSize);
            cell = j + 1;
            openEmpty(cell, boardSize);
        }
    }
    
    public void openEmpty(int cell, int place) {
        if (place == 0) {
            if (cell >= 0) {
                if (board[cell] > mineSpace) {
                    board[cell] -= cover;
                    if (board[cell] == emptySpace)
                        findEmptyCells(cell);
                }
            }
        } else {
            if (cell < place) {
                if (board[cell] > mineSpace) {
                    board[cell] -= cover;
                    if (board[cell] == emptySpace) {
                        findEmptyCells(cell);
                    }
                }
            }
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        int cell = 0;
        int uncover = 0;
        
        for (int i = 0; i< numberOfRows; i++) {
            for (int j = 0; j< numberOfRows; j++) {
                cell = board[(i*numberOfColumns) + j];
                
                if (gameOn && cell == mineSpace) {
                    gameOn = false;
                }
                
                if (!gameOn) {
                    if (cell == mineWithCover) {
                        cell = mineSpace;
                    } else if (cell == mineWithMark) {
                        cell = mark;
                      
                    } else if (cell > mineSpace) {
                        cell = cover;
                    } 
                } else {
                    if (cell > mineWithCover) {
                        cell = mark;
                    } else if (cell > mineSpace) {
                        cell = cover;
                        uncover++;
                    }
                }
                
                g.drawImage(images[cell], j*sizeOfCell, i*sizeOfCell, this);
                
            }
        }
        
        if (uncover == 0 && gameOn) {
            gameOn = false;
        } else if (!gameOn) {
            
        }
             
        
        
    }
    
    class MinesAdapter extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            
            int cCol = x/sizeOfCell;
            int cRow = y/sizeOfCell;
            
            boolean rep = false;
            
            if (!gameOn) {
                setMines();
                repaint();
            }
             
            int place = cRow*numberOfColumns+cCol;
            
            if ((x < numberOfColumns * sizeOfCell) && (y< numberOfRows * sizeOfCell)) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    if (board[place] > mineSpace) {
                        rep = true;
                        if (board[place] <= mineWithCover) {
                            if (minesLeft > 0 ) {
                                board[place] += mark;
                                minesLeft--;
                            }
                        } else {
                            board[place] -= mark;
                            minesLeft++;
                        }
                    }
                } else {
                    if (board[place] > mineWithCover) {
                        return;
                    }
                    if (board[place] > mineSpace && board[place] < mineWithMark ) {
                        board[place] -= cover;
                        rep = true;
                        
                        if (board[place] == mineSpace) {
                            gameOn = false;
                        }
                        if (board[place] == emptySpace) {
                            findEmptyCells(place);
                        }
                    } 
                }
                
                if (rep) {
                    repaint();
                }
            }
        }
    }
}
