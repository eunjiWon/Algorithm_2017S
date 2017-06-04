import java.util.*;

public class Graph {
	private int V; 
	private int tot; 
	private LinkedList<Integer> graphG[];
	private LinkedList<Integer> reverseG[];
	String[] alphabet = { " a ", " b ", " c ", " d ", " e ", " f ", " g ", " h ", " i ", " j " , " k ", " l ", " m "};

	private boolean used[]; 
	
	@SuppressWarnings("unchecked")
	public Graph(int vertex) {
		this.V = vertex;
		this.used = new boolean[vertex];
		this.graphG = new LinkedList[vertex];
		this.reverseG = new LinkedList[vertex];
		for(int i = 0; i < vertex; ++i) {
			graphG[i] = new LinkedList<Integer>();
			reverseG[i] = new LinkedList<Integer>();
		}
	}
	
	public void addEdge(int from, int to) {
		this.graphG[from].add(to);
		this.reverseG[to].add(from);
	}
	
	public void calSCC() {
		Stack<Integer> stack = new Stack<Integer>();
		
		for(int i = 0; i < V; i++)
			this.used[i] = false; 
		
		// DFS 
		System.out.println("Vertexes in the order of increasing finishing time: ");
		for(int i = 0; i < V; i++) 
			if(!this.used[i])
				DFS(i, stack); 
		System.out.println();
		
		
		for(int i = 0; i < V; i++)
			this.used[i] = false; 
		
		System.out.println("Strongly Connected Components are: ");
		while(!stack.empty()) { 
			if(!this.used[stack.peek()]) {
				RverseDFS(stack.peek(), 0);
			}	
			stack.pop(); 
		}
		System.out.println("There are " + tot + " strongly connected components in the given Graph.");
	}
	
	public void DFS(int i, Stack<Integer> stack) {
		this.used[i] = true;
		Iterator<Integer> a = this.graphG[i].iterator(); 
		while(a.hasNext()) {
			int next = a.next(); 
			if(!used[next]) 
				DFS(next, stack);
		}
		System.out.print(alphabet[i] + " " );
		stack.push(i); 
	}
	
	public void RverseDFS (int visit, int count) {
		this.used[visit] = true;
		if(count == 0) 
			System.out.print("Component: ");
		
		System.out.print(alphabet[visit] + " ");
		Iterator<Integer> i = reverseG[visit].listIterator(); 
		while(i.hasNext()) {
			int next = i.next(); 
			if(!used[next]) 
				RverseDFS(next, count + 1);
		}
		if(count == 0) {
			System.out.println();
			tot++;
		}
	}
}
