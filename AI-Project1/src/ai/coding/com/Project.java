package ai.coding.com;

import java.util.*;

public class Project {
	
	public static PriorityQueue<StateNode> queue = new PriorityQueue<StateNode>(); 
	public static Set<int[][]> chosenNodes = new HashSet<int[][]>();
	public static ArrayList<StateNode> ans = new ArrayList<StateNode>();
	public static int[][] goal;
	
	public static void generalSearch(StateNode node,int input){
        
		queue.add(node);
        
        while(!queue.isEmpty()) {
            
        	StateNode current=queue.poll();
        	chosenNodes.add(current.positions);
            ans.add(current);
        	if (Arrays.deepEquals(current.positions, goal)) {
                System.out.println("Goal reached");
                break;
            }
            
        	ArrayList<StateNode> list = current.getChildrens();
        	for(StateNode child: list)
        	{
        		if(!chosenNodes.contains(child.positions))
        		{
        			queue.add(child);
        		}
        	}
        }
        for( StateNode node1 :ans) {
            resultPrint(node1);
            System.out.println("f(n) :" + node1.function_value);
            System.out.println("h(n) :" + (node1.function_value - node1.gn));
            System.out.println("g(n) :" + (node1.gn));
        }
        System.out.println("Answer size "+ ans.size());
    }
	
	public static void resultPrint( StateNode currentNode){
        
        for (int l = 0; l < 3; l++) {
            for (int m = 0; m < 3; m++) {
                System.out.print(currentNode.positions[l][m] + "\t");
            }
            System.out.println();
        }
    }
	
	public static void main(String args[])
	{
		int[][] initialState = new int[3][3];
		System.out.println("Hi!! This is the 8-puzzle. Please enter the number to select the heuristic.");
        System.out.println("Press 1: Uniform Cost Search");
        System.out.println("Press 2: A* with Misplaced tile heuristic");
        System.out.println("Press 3: A* with Manhattan distance heuristic");
        Scanner scanner = new Scanner(System.in);
        
        int input= scanner.nextInt();
        
        System.out.println("Press 1: If you want to solve puzzle with default input.");
        System.out.println("Press 2: If you want to solve puzzle with your input.");
        
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
            
            for(int i = 0; i< 3; i++)
            {
            	for(int j =0; j< 3; j++)
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
		StateNode state = new StateNode(initialState,0, input);
		boolean flag = isSolvable(initialState);
		
		if(flag)
		{
			generalSearch(state,input);
			for( StateNode node1 :ans) {
	            //resultPrint(node1);
	            //System.out.println("f(n) :" + node1.function_value);
	            //System.out.println("h(n) :" + (node1.function_value - node1.gn));
	            //System.out.println("g(n) :" + (node1.gn));
	        }
	        System.out.println("Answer size "+ ans.size());
		}
		else
		{
			System.out.println("Given input is not Solvable.");
		}
        long endTime=System.currentTimeMillis();
		System.out.println("Time Taken in milli seconds: "+(endTime-startTime));
	}
	
	private static boolean isSolvable(int[][] array)
	{
		int[] seq = new int[9];
		int index = 0;
		for (int i = 0; i < 3; i++) 
		{
            for (int j = 0; j < 3; j++) 
            {
                seq[index++] = array[i][j]; 
            }
        }
		return (getInversions(seq) % 2) == 0;
	}
	
	private static int getInversions(int[] arr)
	{
	    int count = 0;
	    for (int i = 0; i < 8; i++)
	    {
	    	for (int j = i+1; j < 9; j++)
	    	{
	    		if(arr[j]>arr[i]){
	    			count++;
                }
	    	}
	    }
	       
	    return count;
	}
}
