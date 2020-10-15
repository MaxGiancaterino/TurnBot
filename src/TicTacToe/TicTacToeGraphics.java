package TicTacToe;
import TurnSolver.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class TicTacToeGraphics extends GameGraphics {

    TicTacToeGameState state;
    TicTacToeTurn thisTurn = null;
    public final CyclicBarrier latch = new CyclicBarrier(2);
    private BufferedImage oImg, xImg;

    public TicTacToeGraphics(int n, boolean isBotsTurn) {
        state = new TicTacToeGameState(n, isBotsTurn);
        try {
            oImg = ImageIO.read(new File("o.png"));
            xImg = ImageIO.read(new File("x.png"));

        } catch (IOException e) {
            System.out.println("Internal Error: " + e.getMessage());
        }
        setFocusable(true);
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                Dimension d = getParent().getSize();
                int w = d.width / n;
                int x = e.getX() / w;
                int y = e.getY() / w;
                if (x >= 0 && x < n && y >= 0 && y < n) {
                    try {
                        handleClick(x, y);
                    } catch (BrokenBarrierException ex) {
                        ex.printStackTrace();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public Turn promptTurn() {
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        latch.reset();
        return thisTurn;
    }

    @Override
    public GameState getGameState() {
        return state;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        TicTacToeMark[][] boardToDraw = state.board;
        Dimension d = getParent().getSize();
        int w = d.width / boardToDraw.length;
        for (int i = 0; i < boardToDraw.length; i++) {
            for (int j = 0; j < boardToDraw[0].length; j++) {
                int xCoord = i * w;
                int yCoord = j * w;
                g.drawRect(xCoord, yCoord, w + 1, w + 1);
                if (boardToDraw[i][j] != null) {
                    switch (boardToDraw[i][j]) {
                        case O:
                            g.drawImage(oImg, xCoord, yCoord, w, w, null);
                            break;
                        case X:
                            g.drawImage(xImg, xCoord, yCoord, w, w, null);
                            break;
                    }
                }
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return getParent().getSize();
    }

    private void handleClick(int x, int y) throws BrokenBarrierException, InterruptedException {
        if (!state.isBotsTurn) {
            TicTacToeMark toMark = state.isBotsTurn ? TicTacToeMark.X : TicTacToeMark.O;
            TicTacToeTurn possibleTurn = new TicTacToeTurn(toMark, x, y);

            Collection<Turn> allTurns = state.getPossibleTurns();
            if (allTurns.contains(possibleTurn)) {
                thisTurn = possibleTurn;
                latch.await();
            }
        }
    }

    @Override
    public String getGameOverMessage() {
        if (state.hasXWon()) {
            return "X has won!";
        }
        if (state.hasOWon()) {
            return "O has won!";
        }
        if (state.isTie()) {
            return "Draw!";
        }
        return null;
    }
}
