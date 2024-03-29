import java.util.ArrayList;
import java.util.List;
/**
 *	SudokuMaker - Creates a Sudoku puzzle using recursion and backtracking
 *
 *	@author Aditi Chamarthy
 *	@since January 18, 2023
 *
 */
public class SudokuMaker {

	private int[][] puzzle;
	
	public SudokuMaker(){
		puzzle = new int[9][9];
	}
	
	public static void main(String [] args){
		SudokuMaker sm = new SudokuMaker();
		sm.run();
	}
	
	public void run(){
		genNum(0,0);
		printPuzzle();
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
	
	/**
	 * This method recursively calls itself. It generates a random guess
	 * from 1-9 and calls the doubled method to see if the guess can be placed there.
	 * If it can, then it moves on to the next box, if not, then it goes back.
	 * @param row - current row to generate the num for
	 * @param col - current column to generate the num for
	 * @return boolean - if a number can't be placed, return false, if not, then true
	 */
	public boolean genNum(int row, int col){
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
			
			/*while(doubled(row, col, guess)){
				allNums.remove(Integer.valueOf(guess));
				System.out.println(allNums);
				if (allNums.size() ==0) {
					return false;
				}
				guess = allNums.get((int)(Math.random() * allNums.size()));
				System.out.println(guess + ", " + row + ", " + col);
				printPuzzle();
			}*/
			puzzle[row][col] = guess;
			int nextRow = row;
			int nextCol = col + 1;
			
			if(nextCol >=9){
				nextRow++;
				nextCol = 0;
			}
			if(nextRow < 9 && nextCol < 9) {
				cont = !genNum(nextRow, nextCol);
				if (cont)
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
}
