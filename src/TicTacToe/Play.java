package TicTacToe;
import TurnSolver.*;

public class Play {
    public static void main(String[] args) {
        int n = 3;
        if (args.length == 1) {
            n = Integer.parseInt(args[0]);
            if (n <= 0) {
                throw new IllegalArgumentException("n must be larger than 0");
            }
        }
        TicTacToeGraphics g = new TicTacToeGraphics(n, true);
        Game.run(g, 8);
    }
}
