package TurnSolver;

import java.util.Collection;

public class TurnTree {

	public static Turn generateBotTurn(GameState current, int depth) {
		Collection<Turn> possibleTurns = current.getPossibleTurns();
		
		double bestValue = Integer.MIN_VALUE;
		Turn bestTurn = null;
		double alpha = Integer.MIN_VALUE;
		double beta = Integer.MAX_VALUE;
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
	
	private static double minimax(GameState current, double alpha, double beta, int depth) {
		if (depth == 0 || current.isGameOver()) {
			return current.value();
		}
		Collection<Turn> possibleTurns = current.getPossibleTurns();
		if (current.isBotsTurn()) {
			double bestValue = Integer.MIN_VALUE;
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
			double bestValue = Integer.MAX_VALUE;
			for (Turn turn : possibleTurns) {
				GameState currentClone = current.clone();
				currentClone.execute(turn);
				double value = minimax(currentClone, alpha, beta, depth - 1);
				bestValue = Math.min(bestValue, value);
				beta = Math.min(beta, bestValue);
				if (alpha >= beta) {
					break;
				}
			}
			return bestValue;
		}
	}
}
