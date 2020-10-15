package TicTacToe;
import TurnSolver.*;

import java.util.Collection;
import java.util.LinkedList;

public class TicTacToeGameState implements GameState<TicTacToeTurn> {

    TicTacToeMark[][] board;
    boolean isBotsTurn;

    public TicTacToeGameState(int n, boolean isBotsTurn) {
        board = new TicTacToeMark[n][n];
        this.isBotsTurn = isBotsTurn;
    }

    public TicTacToeGameState(TicTacToeGameState toCopy) {
        board = new TicTacToeMark[toCopy.board.length][toCopy.board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = toCopy.board[i][j];
            }
        }
        isBotsTurn = toCopy.isBotsTurn;
    }

    @Override
    public Collection<Turn> getPossibleTurns() {
        Collection<Turn> possibleTurns = new LinkedList<>();
        TicTacToeMark toMark = isBotsTurn ? TicTacToeMark.X : TicTacToeMark.O;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == null) {
                    Turn t = new TicTacToeTurn(toMark, i, j);
                    possibleTurns.add(t);
                }
            }
        }
        return possibleTurns;
    }

    @Override
    public void execute(TicTacToeTurn turn) {
        board[turn.x][turn.y] = turn.mark;
        isBotsTurn = !isBotsTurn;
    }


    @Override
    public GameState clone() {
        return new TicTacToeGameState(this);
    }

    @Override
    public double value() {
        if (hasXWon()) {
            return 1;
        }
        if (hasOWon()) {
            return -1;
        }
        return 0;
    }

    @Override
    public boolean isGameOver() {
        return hasXWon() || hasOWon() || isTie();
    }

    boolean hasXWon() {
        for (int i = 0; i < board.length; i++) {
            if (checkRow(i, TicTacToeMark.X)) {
                return true;
            }
            if (checkCol(i, TicTacToeMark.X)) {
                return true;
            }
        }
        return checkDiagonal(TicTacToeMark.X);
    }

    boolean hasOWon() {
        for (int i = 0; i < board.length; i++) {
            if (checkRow(i, TicTacToeMark.O)) {
                return true;
            }
            if (checkCol(i, TicTacToeMark.O)) {
                return true;
            }
        }
        return checkDiagonal(TicTacToeMark.O);
    }

    boolean isTie() {
        return getPossibleTurns().size() == 0;
    }

    private boolean checkRow(int j, TicTacToeMark mark) {
        boolean hasWon = true;
        for (int i = 0; i < board.length; i++) {
            if (board[i][j] != mark) {
                hasWon = false;
            }
        }
        return hasWon;
    }

    private boolean checkCol(int i, TicTacToeMark mark) {
        boolean hasWon = true;
        for (int j = 0; j < board[0].length; j++) {
            if (board[i][j] != mark) {
                hasWon = false;
            }
        }
        return hasWon;
    }

    private boolean checkDiagonal(TicTacToeMark mark) {
        boolean hasWonRight = true;
        for (int i = 0; i < board.length; i++) {
            if (board[i][i] != mark) {
                hasWonRight = false;
            }
        }
        boolean hasWonLeft = true;
        for (int i = 0; i < board.length; i++) {
            if (board[i][board.length - 1 - i] != mark) {
                hasWonLeft = false;
            }
        }
        return hasWonLeft || hasWonRight;
    }

    @Override
    public boolean isBotsTurn() {
        return isBotsTurn;
    }
}
