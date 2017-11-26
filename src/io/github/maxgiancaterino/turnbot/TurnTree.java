package io.github.maxgiancaterino.turnbot;

import java.util.Collection;

public class TurnTree {

	public Turn generateBotTurn(GameState current, int depth) {
		Collection<Turn> possibleTurns = current.getPossibleTurns();
		
		double bestValue = Double.MIN_VALUE;
		Turn bestTurn = null;
		double alpha = Double.MIN_VALUE;
		double beta = Double.MAX_VALUE;
		for (Turn turn : possibleTurns) {
			GameState currentClone = current.clone();
			currentClone.execute(turn);
			double value = minimax(currentClone, alpha, beta, depth - 1);
			if (value > bestValue) {
				bestValue = value;
				bestTurn = turn;
			}
			alpha = Math.max(alpha, bestValue);
			if (alpha >= beta) {
				break;
			}
		}
		return bestTurn;
	}
	
	private double minimax(GameState current, double alpha, double beta, int depth) {
		if (depth == 0 || current.gameOver()) {
			return current.value();
		}
		Collection<Turn> possibleTurns = current.getPossibleTurns();
		if (current.isBotsTurn()) {
			double bestValue = Double.MIN_VALUE;
			for (Turn turn : possibleTurns) {
				GameState currentClone = current.clone();
				currentClone.execute(turn);
				double value = minimax(currentClone, alpha, beta, depth - 1);
				bestValue = Math.max(bestValue, value);
				alpha = Math.max(alpha, bestValue);
				if (alpha >= beta) {
					break;
				}
			}
			return bestValue;		
		} else {
			double bestValue = Double.MAX_VALUE;
			for (Turn turn : possibleTurns) {
				GameState currentClone = current.clone();
				currentClone.execute(turn);
				double value = minimax(currentClone, alpha, beta, depth - 1);
				bestValue = Math.min(bestValue, value);
				beta = Math.min(alpha, bestValue);
				if (alpha >= beta) {
					break;
				}
			}
			return bestValue;
		}
	}
}
