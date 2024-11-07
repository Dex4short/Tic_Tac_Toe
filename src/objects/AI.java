package objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import drawables.TicTacToeBoard;
import drawables.TicTacToeBox;
import enums.Symbol;
import extras.Timing;
import interfaces.Action;
import scenes.PlayScene;

public abstract class AI extends Player implements Action {

    
    public AI() {
        super("Computer");
        
    }

    @Override
    public void onAction() {
        System.out.println("AI is thinking...");
        
        new Thread(() -> {
            new Timing().sleep(1000); // Wait for the AI to make a move
            while (PlayScene.paused) { // Wait if the game is paused
                new Timing().sleep(1000);
                if (PlayScene.suspended) return;
            }
            makeMove();
        }).start();
    }

    private boolean isEmpty(TicTacToeBoard board){
        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col < board.getColumns(); col++) {
                // Check if any box has a non-null symbol
                if (board.getBox(row, col).getSymbol() != null) {
                    return false;
                }
            }
        }
        // If no boxes have symbols, the board is empty
        return true;

    }

    private int[] getRandomMove(TicTacToeBoard board) {
        Random random = new Random();
        int row, col;

        do {
            row = random.nextInt(board.getRows());
            col = random.nextInt(board.getColumns());
        } while (board.getBox(row, col).getSymbol() != null); // Ensure it's an empty cell

        return new int[]{row, col};
    }


    private void makeMove() {
        TicTacToeBoard board = getTacToeBoard();
        int[] move;
        //if AI is first move, the move should be randomize first
        if (isEmpty(board)) { 
            System.out.println("Board is empty AI is the first move");
            move = getRandomMove(board);
            
        } else { // Use minimax algorithm for the following moves
            System.out.println("using minmax");
            move = findBestMove(board);
        }

        int row = move[0];
        int col = move[1];

        TicTacToeBox nextBox = board.getBox(row, col);
        Symbol symbol = board.getNextSymbol();
        nextBox.setSymbol(symbol);
        new Timing().sleep(1000);

        System.out.println("AI marked " + symbol + " at row: " + row + ", col: " + col + ".");
        board.next();
    }

    // Minimax algorithm to find the best move
    private int[] findBestMove(TicTacToeBoard board) {
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = null;

        // Iterate over each cell in the board
        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col < board.getColumns(); col++) {
                TicTacToeBox box = board.getBox(row, col);

                if (box.getSymbol() == null) { // Check if the cell is empty
                    box.setSymbol(board.getNextSymbol()); // Try move
                    int score = minimax(board, 0, false);
                    box.setSymbol(null); // Undo move

                    if (score > bestScore) {
                        bestScore = score;
                        bestMove = new int[]{row, col};
                    }
                }
            }
        }
        return bestMove;
    }

    // Minimax function for recursive evaluation
    private int minimax(TicTacToeBoard board, int depth, boolean isMaximizing) {
        Symbol winner = checkWinner(board);
        Symbol assigned_Symbol = board.getNextSymbol();
        if (winner != null ) {
            //O when ai is second to move 
            //TODO
            // return winner == Symbol.O ? 10 - depth : depth - 10;
            //X when AI is first to move
            return winner == assigned_Symbol ? 10 - depth : depth - 10;
        }
        if (isDraw(board)) {
            return 0;
        }

        if (isMaximizing) {
            int maxEval = Integer.MIN_VALUE;
            for (int row = 0; row < board.getRows(); row++) {
                for (int col = 0; col < board.getColumns(); col++) {
                    TicTacToeBox box = board.getBox(row, col);
                    if (box.getSymbol() == null) {
                        box.setSymbol(Symbol.X);
                        int eval = minimax(board, depth + 1, false);
                        box.setSymbol(null);
                        maxEval = Math.max(maxEval, eval);
                    }
                }
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (int row = 0; row < board.getRows(); row++) {
                for (int col = 0; col < board.getColumns(); col++) {
                    TicTacToeBox box = board.getBox(row, col);
                    if (box.getSymbol() == null) {
                        box.setSymbol(Symbol.O);
                        int eval = minimax(board, depth + 1, true);
                        box.setSymbol(null);
                        minEval = Math.min(minEval, eval);
                    }
                }
            }
            return minEval;
        }
    }
    //to be edited and clean
    // Helper to check for a winner based on the board size
    private Symbol checkWinner(TicTacToeBoard board) {
        int size = board.getRows(); // Assuming square grid
        List<Symbol> symbols;

        // Check rows
        for (int row = 0; row < size; row++) {
            symbols = new ArrayList<>();
            for (int col = 0; col < size; col++) {
                symbols.add(board.getBox(row, col).getSymbol());
            }
            if (isWinningLine(symbols)) return symbols.get(0);
        }

        // Check columns
        for (int col = 0; col < size; col++) {
            symbols = new ArrayList<>();
            for (int row = 0; row < size; row++) {
                symbols.add(board.getBox(row, col).getSymbol());
            }
            if (isWinningLine(symbols)) return symbols.get(0);
        }

        // Check diagonals
        symbols = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            symbols.add(board.getBox(i, i).getSymbol());
        }
        if (isWinningLine(symbols)) return symbols.get(0);

        symbols = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            symbols.add(board.getBox(i, size - i - 1).getSymbol());
        }
        if (isWinningLine(symbols)) return symbols.get(0);

        return null; // No winner
    }

    private boolean isWinningLine(List<Symbol> symbols) {
        if (symbols.isEmpty() || symbols.get(0) == null) return false;
        for (Symbol symbol : symbols) {
            if (symbol != symbols.get(0)) return false;
        }
        return true;
    }

    // Check for a draw
    private boolean isDraw(TicTacToeBoard board) {
        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col < board.getColumns(); col++) {
                if (board.getBox(row, col).getSymbol() == null) {
                    return false;
                }
            }
        }
        return true;
    }
}
