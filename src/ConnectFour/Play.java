package ConnectFour;

import TurnSolver.Game;

public class Play {
    public static void main(String[] args) {
        int n = 7;
        int toWin = 4;
        if (args.length >= 1) {
            n = Integer.parseInt(args[0]);
            if (n <= 0) {
                throw new IllegalArgumentException("n must be larger than 0");
            }
        }

        if (args.length == 2) {
            toWin = Integer.parseInt(args[1]);
            if (toWin <= 1) {
                throw new IllegalArgumentException("to win must be larger than 1");
            }
        }
        ConnectFourGraphics g = new ConnectFourGraphics(n, toWin, true);
        Game.run(g, 4);
    }
}
