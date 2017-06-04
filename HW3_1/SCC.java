import java.util.*;
import java.io.*;

public class SCC {

	public static int n = 8;

	public static void main(String[] args) throws FileNotFoundException {
		String[] alphabet = { " a ", " b ", " c ", " d ", " e ", " f ", " g ", " h ", " i ", " j " , " k ", " l ", " m "};

		Graph gra = new Graph(n); 
		
		// create initial graph from file
		File givenNodes = new File(args[0]);
		Scanner input = new Scanner(givenNodes);

		// graph draw with the given info
		while (input.hasNextLine()) {
			if (input.hasNext()) {
				int from = Arrays.asList(alphabet).indexOf(" " + input.next() + " ");
				int to = Arrays.asList(alphabet).indexOf(" " + input.next() + " ");
				gra.addEdge(from, to);
			}
			else 
				break;
		}
		input.close();
		gra.calSCC();
	}
	
}
