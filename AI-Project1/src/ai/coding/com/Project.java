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
		System.out.println("Hi!! This is the 8-puzzle. Please enter the number to select the heuristic.");
        System.out.println("Press 1: Uniform Cost Search");
        System.out.println("Press 2: A* with Misplaced tile heuristic");
        System.out.println("Press 3: A* with Manhattan distance heuristic");
        Scanner scanner = new Scanner(System.in);
        int input= scanner.nextInt();
		
		int[][] a = {{0, 1, 3},
                {4, 2, 5},
                {7, 8, 6}};
		
		int[][] goal = {{1, 2, 3},
                {4, 5, 6},
                {7, 8, 0}};
		Project.goal = goal;
		long startTime=System.currentTimeMillis();
		StateNode state = new StateNode(a,0, input);
		generalSearch(state,input);
		for( StateNode node1 :ans) {
            //resultPrint(node1);
            //System.out.println("f(n) :" + node1.function_value);
            //System.out.println("h(n) :" + (node1.function_value - node1.gn));
            //System.out.println("g(n) :" + (node1.gn));
        }
        System.out.println("Answer size "+ ans.size());
        long endTime=System.currentTimeMillis();
		System.out.println("Time Taken in milli seconds: "+(endTime-startTime));
	}

}
