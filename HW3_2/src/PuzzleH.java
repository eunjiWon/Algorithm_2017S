import java.util.*;
import java.io.*;

public class PuzzleH { 

	// n x n puzzle
	public static int n = 3;
	public static int totalMoves = 0;

	private static int[][] goalState = { { 1, 2, 3 }, { 8, 0, 4 }, { 7, 6, 5 } };
	
	private int[][] currentState;
	private int walks;
	private int estimatedH;
	private PuzzleH prev;

	public PuzzleH(int[][] given, int step, int h, PuzzleH prev) {
		this.currentState = given;
		this.walks = step;
		this.estimatedH = h;
		this.prev = prev;
	}

	public static void main(String[] args) throws FileNotFoundException {

		// create initial board from file
		File givenData = new File(args[0]);
		Scanner input = new Scanner(givenData);

		// store given state in the 2D array
		int[][] givenState = new int[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				givenState[i][j] = input.nextInt();

		input.close();
		
		// printing the Given state
		System.out.println("Goal State");
		printState(goalState);

		// printing the Given state
		System.out.println("Given State");
		printState(givenState);

		long startTime = System.currentTimeMillis();

		if (!isGoal(givenState)) {
			aStar(givenState);
			System.out.println("Reached goal with " + (totalMoves - 1 ) + " steps");
		} else
			System.out.println("Input is already in its goal state");

		long endTime = System.currentTimeMillis();
		long totTime = endTime - startTime;

		System.out.println("Total time took: " + totTime * 0.001 + "s took");
	}

	// use a* algorithm to find the path
	public static void aStar(int[][] givenState) {
		HashSet<String> closedSet = new HashSet<String>();

		PriorityQueue<PuzzleH> pq = new PriorityQueue<PuzzleH>(10, new Comparator<PuzzleH>(){
			@Override
			public int compare(PuzzleH a, PuzzleH b) {
				int p1 = a.walks + a.estimatedH;
				int p2 = b.walks + b.estimatedH;
				if (p1 < p2) {
					return -1;
				} else if (p1 > p2) {
					return 1;
				} else {
					return 0;
				}
			}	
		});

		ArrayList<int[][]> route = new ArrayList<int[][]>();

		pq.add(new PuzzleH(givenState, 0, 0, null));
		PuzzleH current = null;

		while (!pq.isEmpty()) {
			current = pq.remove();
			closedSet.add(toString(current.currentState));

			if (isGoal(current.currentState) ){
				System.out.println("Found Solution! ");
				pq.clear();
			} else {
				ArrayList<int[][]> availableMoves = move(current.currentState);

				for (int[][] neighbour : availableMoves) {
				
					if (!closedSet.contains(toString(neighbour))) {
						int heuristicCost;
						//heuristicCost = manhattan(neighbour) + hamming(neighbour);
						//heuristicCost = manhattan(neighbour);
						heuristicCost = hamming(neighbour);
						pq.add(new PuzzleH(neighbour, current.walks + 1, heuristicCost, current));
					} 
				}
			}
		}

		
		// solution found and printing the solution steps
		while (current != null) {
			route.add(current.currentState);
			current = current.prev;
		}
		Collections.reverse(route);
		System.out.println("Route is ...");
		for (int[][] state : route) {
			System.out.println("---------step " + totalMoves + "------------");
			printState(state);
			totalMoves++;
		}
	}

	// possible neighbors
	private static ArrayList<int[][]> move(int[][] stateCHK) {
		ArrayList<int[][]> availables = new ArrayList<int[][]>();

		int[] xy = findZero(stateCHK);
		int y = xy[0];
		int x = xy[1];

		// left available
		if (x - 1 > -1) {
			int[][] l = deepCopy(stateCHK);
			int[][] chkL = swap(l, y, x, y, x - 1);
			availables.add(chkL);
		}

		// up available
		if (y - 1 > -1) {
			int[][] U = deepCopy(stateCHK);
			int[][] chkU = swap(U, y, x, y - 1, x);
			availables.add(chkU);
		}
		// down available
		if (y + 1 < 3) {
			int[][] D = deepCopy(stateCHK);
			int[][] chkD = swap(D, y, x, y + 1, x);
			availables.add(chkD);
		}

		// right available
		if (x + 1 < 3) {
			int[][] R = deepCopy(stateCHK);
			int[][] chkR = swap(R, y, x, y, x + 1);
			availables.add(chkR);
		}
		return availables;
	}

	private static void printState(int[][] a) {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++)
				System.out.print(a[i][j] + " ");
			System.out.println();
		}
		System.out.println();
	}


	private static int[][] swap(int[][] swa, int x1, int y1, int x2, int y2) {
		int[][] swapped = swa;
		int tmp = swa[x1][y1];

		swapped[x1][y1] = swa[x2][y2];
		swapped[x2][y2] = tmp;

		return swapped;
	}

	private static int[][] deepCopy(int[][] target) {
		int[][] copied = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				copied[i][j] = target[i][j];
			}
		}
		return copied;
	}

	// find the position of '0' to move
	private static int[] findZero(int[][] state) {
		int[] position = { -1, -1 };
		outerloop: for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				if (state[i][j] == 0) {
					position[0] = i;
					position[1] = j;
					break outerloop;
				}
		return position;
	}

	// calculate # of blokcs in wrongPosition
	// Hamming: #ofblocks outof position + #ofmoves made so far
	private static int hamming(int[][] current) {
		int outOfPlace = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (current[i][j] != goalState[i][j]) {
					outOfPlace++;
				}
			}
		}
		return outOfPlace;
	}

	// sum of manhattan distances
	// which is veritcal and horizontal distance from the blocks to their goal
	private static int manhattan(int[][] current) {
		int stepsToGoal = 0;
		int i, j;

		for (i = 0; i < n; i++) {
			for (j = 0; j < n; j++) {
				stepsToGoal += distanceFromGoal(i, j, current[i][j]);
			}
		}
		return stepsToGoal;
	}

	private static int distanceFromGoal(int x, int y, int target) {
		int dis = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (target == goalState[i][j]) {
					dis = (Math.abs(x - i) + Math.abs(y - j));
					break;
				}
			}
		}
		return dis;
	}

	// checks whether the state is same as the given state
	private static boolean isGoal(int[][] current) {
		boolean isGoal = true;
		int i = 0, j = 0;

		outerloop: for (i = 0; i < n; i++)
			for (j = 0; j < n; j++)
				if (goalState[i][j] != current[i][j]) {
					isGoal = false;
					break outerloop;
				}
		return isGoal;
	}
	public static String toString(int[][] intToString) {
		String str = "";
		for(int i = 0; i < n; i ++) {
			for (int j = 0; j < n; j ++) {
				str += Integer.toString(intToString[i][j]);
			}
				
		}
		return str; 
	}
}
