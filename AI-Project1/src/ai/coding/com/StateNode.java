package ai.coding.com;

import java.util.*;

// This class holds information of state along with its functional value
public class StateNode implements Comparable<StateNode> {

	public int function_value; // Function value to compare between states
	public int[][] positions; // Current state of tiles
	public int gn = 0; // g(n)
	public static int input_type; 
	public static int rows = Project.rows, columns = Project.columns;
	
	// Constructor will take tile positions, level and input heuristic
	public StateNode(int[][] a, int level, int input)
	{
		StateNode.input_type = input;
		this.positions = new int[rows][columns];
		for(int i = 0; i < rows; i++)
		{
			for(int j = 0; j < columns; j++)
			{
				this.positions[i][j] = a[i][j];
			}
		}
		this.gn = level;
		this.function_value = calculateFunctionValue();
	}
	
	// Function to calculate f(n) = h(n) + g(n)
	public int calculateFunctionValue()
	{
		return calculateHNValue() + this.gn;
	}
	
	// Function gives h(n) value for selected heuristic
	public int calculateHNValue()
	{
		int hn = 0;
		if(StateNode.input_type == 1) // Uniform cost search
		{
			hn = 0;
		}
		if(StateNode.input_type == 2) // Misplaced tile
		{
			hn = getMisplacedTileHeuristic();
		}
		if(StateNode.input_type == 3)// Manhattan Distance
		{
			hn = getManhattanDistance();
		}
		
		return hn;
	}
	
	// H(n) value for Misplaced tile heuristic
	private int getMisplacedTileHeuristic()
	{
		int sum=0;
		int N = Project.goal.length;
		for (int i = 0;i<N;i++)
		{
			for (int j = 0; j<N; j++)
			{
				if(this.positions[i][j] == 0)
				{
					continue;
				}
				if(this.positions[i][j] != Project.goal[i][j])
					sum+=1;
			}
		}
		return sum;
	}
	
	// h(n) value for manhattan distance heuristic
	private int getManhattanDistance()
	{
		int ans=0;
		int[] index= new int[2];
		
		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < columns; j++)
			{
				if(this.positions[i][j] == 0)
				{
					continue;
				}
				index = findPositionInGoal(this.positions[i][j]);
				ans = ans + (Math.abs(i-index[0])+Math.abs(j-index[1]));
			}
		}
		return ans;
	}
	
	// Function to get current number's position in goal state
	private int[] findPositionInGoal(int value)
	{
		int[] ans = new int[2];
		
		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < columns; j++)
			{
				if(Project.goal[i][j] == 0)
				{
					continue;
				}
				if (Project.goal[i][j] == value)
				{
					ans[0]=i;
					ans[1]=j;
					return ans;
				}
			}
		}
		
		return ans;
	}
	
	// This function gives all states possible after one move
	public ArrayList<StateNode> getChildrens()
	{
		ArrayList<StateNode> childrens = new ArrayList<StateNode>();
		
		for(int i = 0; i < rows; i++)
		{
			for(int j = 0; j < columns; j++)
			{
				if(this.positions[i][j] == 0)
				{
					//Top Move
					if(i-1>=0)
					{
						int[][] child = new int[rows][columns];
						for (int x = 0; x < rows; x++)
						{
							for(int y = 0; y < columns; y++)
							{
								child[x][y] = this.positions[x][y];
							}
						}
						 child = swap(child,i,j,i-1,j);
						 StateNode b = new StateNode(child,this.gn+1, StateNode.input_type);
						 childrens.add(b);
					}
					
					//Left Move
					if(j-1>=0)
					{
						int[][] child = new int[rows][columns];
						for (int x = 0; x < rows; x++)
						{
							for(int y = 0; y < columns; y++)
							{
								child[x][y] = this.positions[x][y];
							}
						}
						 child = swap(child,i,j,i,j-1);
						 StateNode b = new StateNode(child,this.gn+1, StateNode.input_type);
						 childrens.add(b);
					}
					
					//Bottom move
					if(i+1<rows)
					{
						int[][] child = new int[rows][columns];
						for (int x = 0; x < rows; x++)
						{
							for(int y = 0; y < columns; y++)
							{
								child[x][y] = this.positions[x][y];
							}
						}
						 child = swap(child,i,j,i+1,j);
						 StateNode b = new StateNode(child,this.gn+1, StateNode.input_type);
						 childrens.add(b);
					}
					
					//Right move
					if(j+1<columns)
					{
						int[][] child = new int[rows][columns];
						for (int x = 0; x < rows; x++)
						{
							for(int y = 0; y < columns; y++)
							{
								child[x][y] = this.positions[x][y];
							}
						}
						 child = swap(child,i,j,i,j+1);
						 StateNode b = new StateNode(child,this.gn+1, StateNode.input_type);
						 childrens.add(b);
					}
				}
			}
		}
		
		return childrens;
	}
	
	// Swap function to facilitate move
	private int[][] swap(int[][] a,int row1, int col1, int row2, int col2)
	{
	     int[][] copy = a;
	     int tmp = copy[row1][col1];
	     copy[row1][col1] = copy[row2][col2];
	     copy[row2][col2] = tmp;
	
	     return copy;
	}

	// This comparator is very important
	// We want to compare StateNode on function value of that state
	@Override
	public int compareTo(StateNode o) {
		if(this.function_value == o.function_value)
		{
			return ((this.calculateHNValue() - o.calculateHNValue()));
		}
		return this.function_value - o.function_value;
	}
}
