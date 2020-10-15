package TurnSolver;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Game implements Runnable {
    final int DEPTH;
    GameGraphics graphics;
    final JFrame frame = new JFrame("TurnSolver.Game");
    final int GAME_SIZE = 1000;

    public Game(GameGraphics graphics) {
        this.graphics = graphics;
        DEPTH = 10;
    }

    public Game(GameGraphics graphics, int depth) {
        this.graphics = graphics;
        DEPTH = depth;
    }

    public static void run(GameGraphics graphics) {
        Game game = new Game(graphics);
        SwingUtilities.invokeLater(game);
    }

    public static void run(GameGraphics graphics, int depth) {
        Game game = new Game(graphics, depth);
        SwingUtilities.invokeLater(game);
    }

    @Override
    public void run() {

        // main frames
        frame.setLocation(0, 0);
        final JPanel playPanel = new JPanel(new GridBagLayout());

        // play button
        final JButton play = new JButton("Play");
        play.setPreferredSize(new Dimension(200, 80));
        play.setFont(new Font("Arial" , Font.PLAIN, 40));

        // play button action listener
        play.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.remove(playPanel);
                frame.add(graphics);
                frame.setVisible(true);
                graphics.draw();
                frame.repaint();
                Thread startThread = new Thread(() -> startGame());
                startThread.start();
            }
        });

        // add play button and playPanel to frame
        playPanel.add(play);
        frame.add(playPanel);

        // setup main frame
        frame.pack();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        int topInset = frame.getInsets().top;
        frame.setSize(GAME_SIZE, GAME_SIZE + topInset);
    }

    private void startGame() {
        GameState state = graphics.getGameState();
        while (!state.isGameOver()) {
            handleTurn();
            Thread draw = new Thread(() -> graphics.draw());
            SwingUtilities.invokeLater(draw);
        }
        Thread gameOver = new Thread(() -> handleGameOver());
        SwingUtilities.invokeLater(gameOver);
    }

    private void handleTurn() {
        GameState state = graphics.getGameState();
        if (state.isBotsTurn()) {
            Turn botTurn = TurnTree.generateBotTurn(state, DEPTH);
            state.execute(botTurn);
        } else {
            state.execute(graphics.promptTurn());
        }
    }

    private void handleGameOver() {
        final JPanel gameOverPanel = new JPanel(new GridBagLayout());
        final JLabel gameOverMsg = new JLabel("<html><span bgcolor=\"yellow\">" + graphics.getGameOverMessage() + "</span></html>\"");
        gameOverMsg.setFont(new Font("Arial" , Font.PLAIN, 40));
        gameOverPanel.add(gameOverMsg);
//        frame.remove(graphics);
        graphics.add(gameOverMsg);
//        frame.add(gameOverPanel);
        frame.setVisible(true);
        frame.repaint();
    }
}
