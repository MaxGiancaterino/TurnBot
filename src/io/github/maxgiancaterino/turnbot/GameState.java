package io.github.maxgiancaterino.turnbot;

import java.util.Collection;

public interface GameState extends Cloneable {
	
	Collection<Turn> getPossibleTurns();
	void execute(Turn turn);
	GameState clone();
	double value();
	boolean gameOver();
	boolean isBotsTurn();
}
