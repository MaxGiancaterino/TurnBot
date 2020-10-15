package TurnSolver;

import javax.swing.*;

public abstract class GameGraphics extends JPanel {
    /**
     * Prompt the user for a turn
     * @return the turn given by the user
     */
    public abstract Turn promptTurn();

    /**
     * Return current state of the game
     * @return current state of the game
     */
    public abstract GameState getGameState();

    /**
     * Return message that indicates the game is over.
     * @return message that indicates the game is over
     */
    public String getGameOverMessage() {
        return "Game Over";
    }

    /**
     * Draw the current state of the game
     */
    public void draw() {
        paintComponent(getGraphics());
    }
}
