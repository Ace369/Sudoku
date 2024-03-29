import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *	SudokuSolver - Solves an incomplete Sudoku puzzle using recursion and backtracking
 *
 *	@author	Aditi Chamarthy
 *	@since	January 25, 2024
 *
 */
public class SudokuSolver {

	private int[][] puzzle;		// the Sudoku puzzle
	
	private String PUZZLE_FILE = "puzzle2.txt";	// default puzzle file
	
	/* Constructor */
	public SudokuSolver() {
		puzzle = new int[9][9];
		// fill puzzle with zeros
		for (int row = 0; row < puzzle.length; row++)
			for (int col = 0; col < puzzle[0].length; col++)
				puzzle[row][col] = 0;
	}
	
	public static void main(String[] args) {
		SudokuSolver sm = new SudokuSolver();
		sm.run(args);
	}
	
	public void run(String[] args) {
		// get the name of the puzzle file
		String puzzleFile = PUZZLE_FILE;
		if (args.length > 0) puzzleFile = args[0];
		
		System.out.println("\nSudoku Puzzle Solver");
		// load the puzzle
		System.out.println("Loading puzzle file " + puzzleFile);
		loadPuzzle(puzzleFile);
		printPuzzle();
		// solve the puzzle starting in (0,0) spot (upper left)
		solvePuzzle(0, 0);
		printPuzzle();
	}
	
	/**	Load the puzzle from a file
	 *	@param filename		name of puzzle file
	 */
	public void loadPuzzle(String filename) {
		Scanner infile = FileUtils.openToRead(filename);
		for (int row = 0; row < 9; row++)
			for (int col = 0; col < 9; col++)
				puzzle[row][col] = infile.nextInt();
		infile.close();
	}
	
	/**	Solve the Sudoku puzzle using brute-force method. */
	public boolean solvePuzzle(int row, int col) {
		
		boolean set = false;
		if(puzzle[row][col] != 0){
			if(col!=8)
				return solvePuzzle(row,col+1);
			else
				return solvePuzzle(row+1,0);
		}
		int count = 0;
		boolean cont = true;
		List<Integer> allNums = new ArrayList<Integer>();
		for(int i = 1; i < 10; i++){
			if (!doubled(row, col, i))
				allNums.add(i);
		}

		while (cont) {
			if (allNums.size() ==0) {
				return false;
			}
				
			int guess = allNums.get((int)(Math.random() * allNums.size()));
			allNums.remove(Integer.valueOf(guess));
				
			puzzle[row][col] = guess;
			int nextRow = row;
			int nextCol = col + 1;
				
			if(nextCol >=9){
				nextRow++;
				nextCol = 0;
			}
			if(nextRow < 9 && nextCol < 9) {
				cont = !solvePuzzle(nextRow, nextCol);
				if (cont && !set)
					puzzle[row][col] = 0;
			}
			else
				return true;
		}
		
		return true;
		
	}
	
	/**
	 * This method returns true if the guess is the same as another num in
	 * the same row, column, or box. Returns false if not.
	 * @param row - int for the row the number is in
	 * @param column - int for the column the number is in
	 * @param guess - the current random guess
	 * @return ifDoubled - a boolean, true if the guess matches a num
	 */
	public boolean doubled(int row, int column, int guess){


		for(int i = 0; i < 9; i++){
			if (puzzle[row][i] == guess){
				return true;
			}
			if(puzzle[i][column] == guess){
				return true;
			}
		}
		
		int rowBox = row - (row%3);
		int colBox = column - (column%3);
		
		for(int i = rowBox;  i < rowBox+ 3;i++){	
			for(int j = colBox; j < colBox +3; j++){
				
				if (puzzle[i][j] == guess)
					return true;
			}
		}
		return false;
	}	
	
	/**
	 *	printPuzzle - prints the Sudoku puzzle with borders
	 *	If the value is 0, then print an empty space; otherwise, print the number.
	 */
	public void printPuzzle() {
		System.out.print("  +-----------+-----------+-----------+\n");
		String value = "";
		for (int row = 0; row < puzzle.length; row++) {
			for (int col = 0; col < puzzle[0].length; col++) {
				// if number is 0, print a blank
				if (puzzle[row][col] == 0) value = " ";
				else value = "" + puzzle[row][col];
				if (col % 3 == 0)
					System.out.print("  |  " + value);
				else
					System.out.print("  " + value);
			}
			if ((row + 1) % 3 == 0)
				System.out.print("  |\n  +-----------+-----------+-----------+\n");
			else
				System.out.print("  |\n");
		}
	}
}
