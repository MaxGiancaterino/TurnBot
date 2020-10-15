package TicTacToe;
import TurnSolver.*;

import java.util.Objects;

public class TicTacToeTurn implements Turn {
    TicTacToeMark mark;
    int x, y;

    public TicTacToeTurn(TicTacToeMark mark, int x, int y) {
        this.mark = mark;
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicTacToeTurn that = (TicTacToeTurn) o;
        return x == that.x &&
                y == that.y &&
                mark == that.mark;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mark, x, y);
    }
}
