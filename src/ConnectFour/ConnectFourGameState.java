package ConnectFour;

import TurnSolver.GameState;
import TurnSolver.Turn;

import java.util.Collection;
import java.util.LinkedList;

public class ConnectFourGameState implements GameState<ConnectFourTurn> {

    ConnectFourPiece[][] board;
    boolean isBotsTurn;
    final int toWin;

    public ConnectFourGameState(int n, int toWin, boolean isBotsTurn) {
        board = new ConnectFourPiece[n][n];
        this.isBotsTurn = isBotsTurn;
        this.toWin = toWin;
    }

    public ConnectFourGameState(ConnectFourGameState toCopy) {
        board = new ConnectFourPiece[toCopy.board.length][toCopy.board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = toCopy.board[i][j];
            }
        }
        isBotsTurn = toCopy.isBotsTurn;
        toWin = toCopy.toWin;
    }

    @Override
    public Collection<Turn> getPossibleTurns() {
        Collection<Turn> possibleTurns = new LinkedList<>();
        ConnectFourPiece toMark = isBotsTurn ? ConnectFourPiece.RED : ConnectFourPiece.YELLOW;
        for (int i = 0; i < board.length; i++) {
            if (board[i][0] == null) {
                int j = 0;
                while (j + 1 < board.length && board[i][j+1] == null) {
                    j++;
                }
                Turn t = new ConnectFourTurn(toMark, i, j);
                possibleTurns.add(t);
            }

        }
        return possibleTurns;
    }

    @Override
    public void execute(ConnectFourTurn turn) {
        board[turn.x][turn.y] = turn.piece;
        isBotsTurn = !isBotsTurn;
    }


    @Override
    public GameState clone() {
        return new ConnectFourGameState(this);
    }

    @Override
    public double value() {
        if (hasRedWon()) {
            return 1;
        }
        if (hasYellowWon()) {
            return -1;
        }
        return 0;
    }

    @Override
    public boolean isGameOver() {
        return hasYellowWon() || hasRedWon() || isTie();
    }

    boolean hasRedWon() {
        for (int i = 0; i < board.length; i++) {
            if (checkRow(i, ConnectFourPiece.RED)) {
                return true;
            }
            if (checkCol(i, ConnectFourPiece.RED)) {
                return true;
            }
        }
        return checkDiagonal(ConnectFourPiece.RED);
    }

    boolean hasYellowWon() {
        for (int i = 0; i < board.length; i++) {
            if (checkRow(i, ConnectFourPiece.YELLOW)) {
                return true;
            }
            if (checkCol(i, ConnectFourPiece.YELLOW)) {
                return true;
            }
        }
        return checkDiagonal(ConnectFourPiece.YELLOW);
    }

    boolean isTie() {
        return getPossibleTurns().size() == 0;
    }

    private boolean checkRow(int j, ConnectFourPiece mark) {
        int count = 0;
        int maxCount = 0;
        for (int i = 0; i < board.length; i++) {
            if (board[i][j] != mark) {
                maxCount = Math.max(maxCount, count);
                count = 0;
            } else {
                count++;
            }
        }
        maxCount = Math.max(maxCount, count);
        return maxCount >= toWin;
    }

    private boolean checkCol(int i, ConnectFourPiece mark) {
        int count = 0;
        int maxCount = 0;
        for (int j = 0; j < board[0].length; j++) {
            if (board[i][j] != mark) {
                maxCount = Math.max(maxCount, count);
                count = 0;
            } else {
                count++;
            }
        }
        maxCount = Math.max(maxCount, count);
        return maxCount >= toWin;
    }

    private boolean checkDiagonal(ConnectFourPiece mark) {
        for (int j = 0; j < board.length; j++) {
            if (checkUpRightDiag(mark, 0, j) || checkDownRightDiag(mark, 0, j)) {
                return true;
            }
        }
        for (int j = 0; j < board.length; j++) {
            if (checkUpLeftDiag(mark, board.length - 1, j) || checkDownLeftDiag(mark, board.length - 1, j)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkDownRightDiag(ConnectFourPiece piece, int i, int j) {
        int count = 0;
        int maxCount = 0;
        while(isInBounds(i, j)) {
            if (board[i][j] != piece) {
                maxCount = Math.max(maxCount, count);
                count = 0;
            } else {
                count++;
            }
            i++;
            j++;
        }
        maxCount = Math.max(maxCount, count);
        return maxCount >= toWin;
    }

    private boolean checkDownLeftDiag(ConnectFourPiece piece, int i, int j) {
        int count = 0;
        int maxCount = 0;
        while(isInBounds(i, j)) {
            if (board[i][j] != piece) {
                maxCount = Math.max(maxCount, count);
                count = 0;
            } else {
                count++;
            }
            i--;
            j++;
        }
        maxCount = Math.max(maxCount, count);
        return maxCount >= toWin;
    }

    private boolean checkUpRightDiag(ConnectFourPiece piece, int i, int j) {
        int count = 0;
        int maxCount = 0;
        while(isInBounds(i, j)) {
            if (board[i][j] != piece) {
                maxCount = Math.max(maxCount, count);
                count = 0;
            } else {
                count++;
            }
            i++;
            j--;
        }
        maxCount = Math.max(maxCount, count);
        return maxCount >= toWin;
    }

    private boolean checkUpLeftDiag(ConnectFourPiece piece, int i, int j) {
        int count = 0;
        int maxCount = 0;
        while(isInBounds(i, j)) {
            if (board[i][j] != piece) {
                maxCount = Math.max(maxCount, count);
                count = 0;
            } else {
                count++;
            }
            i--;
            j--;
        }
        maxCount = Math.max(maxCount, count);
        return maxCount >= toWin;
    }

    private boolean isInBounds(int i, int j) {
        return i >= 0 && i < board.length && j >=0 && j < board[0].length;
    }

    @Override
    public boolean isBotsTurn() {
        return isBotsTurn;
    }
}
