package TurnSolver;

import java.util.Collection;

public interface GameState<T extends Turn> extends Cloneable {

	/**
	 * Given the current state of the game, return a collection of all possible moves.
	 * @return a collection of all possible moves
	 */
	Collection<Turn> getPossibleTurns();

	/**
	 * Execute a turn and mutate the game state.
	 * @param turn the turn to execute
	 */
	void execute(T turn);

	/**
	 * Create deep copy of the game state
	 * @return a deep copy of the game state
	 */
	GameState clone();

	/**
	 * Return a double that represents how valuable this position is to the bot
	 * @return a double that represents how valuable this position is to the bot
	 */
	double value();

	/**
	 * Return true if the game is over.
	 * @return true if the game is over
	 */
	boolean isGameOver();

	/**
	 * Return true if it is the bot's turn
	 * @return true if it is the bot's turn
	 */
	boolean isBotsTurn();
}
