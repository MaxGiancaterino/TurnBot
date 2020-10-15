# TurnBot

The main part of this project is located in the TurnSolver package. 
This set of source files is designed as an interface for creating 2-player turn-based games with a turn solver that uses minimax to make moves for one player. 
Someone wanting to use this package would need to implement two interfaces in particular: GameGraphics and GameState

This project uses a tree algorithm with the implementation of the minimax algorithm with alpha-beta pruning in order to cut down on evaluation time. This minimax algorithm
also fulfills the game theory topic as it is fundamentally a game theory algorithm that relies on the evaluation function implemented in GameState.

## Usage
I have two concrete examples of using this turn solver in the other two packages. The games are Connect Four and tic-tac-toe. Consult the [manual](User\ Manual.pdf) for how
to run them.
