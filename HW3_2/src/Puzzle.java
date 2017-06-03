import java.util.*;
import java.io.*;

public class Puzzle {

	// n x n puzzle
	public static int n = 3;

	public static void main(String[] args) throws FileNotFoundException {

		// create initial board from file
		File givenData = new File(args[0]);
		Scanner input = new Scanner(givenData);

		// n x n puzzle
		int[][] goalState = { { 1, 2, 3 }, { 8, 0, 4 }, { 7, 6, 5 } };

		// store given state in the 2D array
		int[][] givenState = new int[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				givenState[i][j] = input.nextInt();

		// printing the Given state
		System.out.println("Given State");
		printState(givenState);

		// printing the Given state
		System.out.println("Goal State");
		printState(goalState);

		long startTime = System.currentTimeMillis();

		isGoal(goalState, givenState);

		long endTime = System.currentTimeMillis();
		long totTime = endTime - startTime;
		System.out.println("Total time took: " + totTime * 0.001 + "s took");
	}

	public static void printState(int[][] a) {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++)
				System.out.print(a[i][j] + " ");
			System.out.println();
		}
		System.out.println();
	}

	public static boolean equlChk(int a[][], int b[][]) {
		boolean equal = true;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++)
				if (a[i][j] != b[i][j]) {
					equal = false;
					break;
				}
		}
		return equal;
	}

	private int[][] swap(int[][] copy, int row1, int col1, int row2, int col2) {
		int tmp = copy[row1][col1];
		copy[row1][col1] = copy[row2][col2];
		copy[row2][col2] = tmp;

		return copy;
	}

	public static void aStar() {

	}

	public static int calDistance() {
		int distance = 0;

		return distance;
	}

	public static boolean isGoal(int[][] goal, int[][] current) {
		boolean isGoal = true;
		int i = 0, j = 0;

		outerloop: for (i = 0; i < n; i++) {
			for (j = 0; j < n; j++) {
				if (goal[i][j] != current[i][j]) {
					isGoal = false;
					break outerloop;
				}
			}
		}

		return isGoal;
	}

}
