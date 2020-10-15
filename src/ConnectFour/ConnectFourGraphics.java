package ConnectFour;

import TurnSolver.GameGraphics;
import TurnSolver.GameState;
import TurnSolver.Turn;

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

public class ConnectFourGraphics extends GameGraphics {

    ConnectFourGameState state;
    ConnectFourTurn thisTurn = null;
    public final CyclicBarrier latch = new CyclicBarrier(2);
    private BufferedImage yImg, rImg;

    public ConnectFourGraphics(int n, int toWin, boolean isBotsTurn) {
        state = new ConnectFourGameState(n, toWin, isBotsTurn);
        try {
            yImg = ImageIO.read(new File("yellow.png"));
            rImg = ImageIO.read(new File("red.png"));

        } catch (IOException e) {
            System.out.println("Internal Error: " + e.getMessage());
        }
        setFocusable(true);
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                Dimension d = getParent().getSize();
                int w = d.width / n;
                int x = e.getX() / w;
                if (x >= 0 && x < n) {
                    try {
                        handleClick(x);
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

        ConnectFourPiece[][] boardToDraw = state.board;
        Dimension d = getParent().getSize();
        int w = d.width / boardToDraw.length;
        for (int i = 0; i < boardToDraw.length; i++) {
            for (int j = 0; j < boardToDraw[0].length; j++) {
                int xCoord = i * w;
                int yCoord = j * w;
                g.drawRect(xCoord, yCoord, w + 1, w + 1);
                if (boardToDraw[i][j] != null) {
                    switch (boardToDraw[i][j]) {
                        case YELLOW:
                            g.drawImage(yImg, xCoord, yCoord, w, w, null);
                            break;
                        case RED:
                            g.drawImage(rImg, xCoord, yCoord, w, w, null);
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

    private void handleClick(int x) throws BrokenBarrierException, InterruptedException {
        if (!state.isBotsTurn) {
            ConnectFourPiece toMark = state.isBotsTurn ? ConnectFourPiece.RED : ConnectFourPiece.YELLOW;
            int y = 0;
            while (y + 1 < state.board.length && state.board[x][y+1] == null) {
                y++;
            }
            ConnectFourTurn possibleTurn = new ConnectFourTurn(toMark, x, y);

            Collection<Turn> allTurns = state.getPossibleTurns();
            if (allTurns.contains(possibleTurn)) {
                thisTurn = possibleTurn;
                latch.await();
            }
        }
    }

    @Override
    public String getGameOverMessage() {
        if (state.hasYellowWon()) {
            return "Yellow has won!";
        }
        if (state.hasRedWon()) {
            return "Red has won!";
        }
        if (state.isTie()) {
            return "Draw!";
        }
        return null;
    }
}
