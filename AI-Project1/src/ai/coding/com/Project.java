package ai.coding.com;

import java.util.*;

// Runner class where main function is present
// This class holds generalSearch method
public class Project {
	
	// Priority queue is used to compare and choose next state
	public static PriorityQueue<StateNode> queue = new PriorityQueue<StateNode>();
	// Set is used to handle duplication
	public static Set<int[][]> chosenNodes = new HashSet<int[][]>();
	// Path to the solution
	public static ArrayList<StateNode> ans = new ArrayList<StateNode>();
	
	// Goal State
	public static int[][] goal;
	
	// Rows and columns value, to change from 8-puzzle to 15-puzzle edit following values
	public static int rows = 3, columns = 3;
	
	// General Search Algorithm as discussed in class
	public static void generalSearch(StateNode node,int input){
        
		// Initial State added in queue
		queue.add(node);
        
        while(!queue.isEmpty()) {
            
        	// Choose optimal path next state
        	StateNode current=queue.poll();
        	chosenNodes.add(current.positions);
            ans.add(current);
            
            // Check if chosen state is a goal state or not
        	if (Arrays.deepEquals(current.positions, goal)) {
                System.out.println("Goal reached");
                break;
            }
            
        	// Add children states into queue
        	ArrayList<StateNode> list = current.getChildrens();
        	for(StateNode child: list)
        	{
        		if(!chosenNodes.contains(child.positions))
        		{
        			queue.add(child);
        		}
        	}
        }
        // Function values print
        for( StateNode node1 :ans) {
            resultPrint(node1);
            System.out.println("f(n) :" + node1.function_value);
            System.out.println("h(n) :" + (node1.function_value - node1.gn));
            System.out.println("g(n) :" + (node1.gn));
        }
        //System.out.println("Answer size "+ ans.size());
    }
	
	// Funtion to print satate values
	public static void resultPrint( StateNode currentNode){
        
        for (int l = 0; l < rows; l++) {
            for (int m = 0; m < columns; m++) {
                System.out.print(currentNode.positions[l][m] + "\t");
            }
            System.out.println();
        }
    }
	
	// Main function
	public static void main(String args[])
	{
		// Decide which heuristic
		int[][] initialState = new int[rows][columns];
		System.out.println("Welcome..!! This is The 8-puzzle.\n Please enter the number to select the heuristic.");
        System.out.println("Type 1: Uniform Cost Search");
        System.out.println("Type 2: A* with Misplaced tile heuristic");
        System.out.println("Type 3: A* with Manhattan distance heuristic");
        Scanner scanner = new Scanner(System.in);
        
        int input= scanner.nextInt();
        
        // Decide to use default input or custom input
        System.out.println("Type 1: If you want to solve puzzle with default input.");
        System.out.println("Type 2: If you want to solve puzzle with your input.");
        
        int isDefault= scanner.nextInt();
        
        if(isDefault == 1)
        {
        	int[][] a = {{0, 1, 3},
                    {4, 2, 5},
                    {7, 8, 6}};
        	initialState = a;
        }
        if(isDefault == 2)
        {
        	System.out.println("Enter initial state. **Valid input contains numbers 0 to 8. **0 treated as space.");
            
            for(int i = 0; i< rows; i++)
            {
            	for(int j =0; j< columns; j++)
            	{
            		int val = scanner.nextInt();
            		initialState[i][j] = val;
            	}
            }
        }
		
		int[][] goal = {{1, 2, 3},
                {4, 5, 6},
                {7, 8, 0}};
		Project.goal = goal;
		long startTime=System.currentTimeMillis();
		
		// Create initial state with level 0
		StateNode state = new StateNode(initialState,0, input);
		
		// Check if puzzle is solvable or not
		boolean flag = isSolvable(initialState);
		
		if(flag)
		{
			// general search
			generalSearch(state,input);
			//for( StateNode node1 :ans) {
	            //resultPrint(node1);
	            //System.out.println("f(n) :" + node1.function_value);
	            //System.out.println("h(n) :" + (node1.function_value - node1.gn));
	            //System.out.println("g(n) :" + (node1.gn));
	        //}
	        System.out.println("Answer size "+ ans.size());
	        System.out.println("Total Nodes generated:"+(ans.size()+queue.size()));
		}
		else
		{
			System.out.println("Given input is not Solvable.");
		}
        long endTime=System.currentTimeMillis();
		System.out.println("Time Taken in milli seconds: "+(endTime-startTime));
	}
	
	// Function to check if puzzle is solvable or not
	private static boolean isSolvable(int[][] array)
	{
		int[] seq = new int[rows*columns];
		int index = 0;
		for (int i = 0; i < rows; i++) 
		{
            for (int j = 0; j < columns; j++) 
            {
            	if(array[i][j] != 0)
            	{	
	            	seq[index] = array[i][j]; 
	            	index++;
            	}
            }
        }
		return (getInversions(seq) % 2) == 0;
	}
	
	// Get number of inversions from 1-d array
	private static int getInversions(int[] arr)
	{
	    int count = 0;
	    for (int i = 0; i < rows*columns - 1; i++)
	    {
	    	for (int j = i+1; j < rows*columns; j++)
	    	{
	    		if(arr[j]>arr[i]){
	    			count++;
                }
	    	}
	    }
	       
	    return count;
	}
}
