package ConnectFour;

import TurnSolver.Turn;

import java.util.Objects;

public class ConnectFourTurn implements Turn {
    ConnectFourPiece piece;
    int x, y;

    public ConnectFourTurn(ConnectFourPiece piece, int x, int y) {
        this.piece = piece;
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConnectFourTurn that = (ConnectFourTurn) o;
        return x == that.x &&
                y == that.y &&
                piece == that.piece;
    }

    @Override
    public int hashCode() {
        return Objects.hash(piece, x, y);
    }
}
