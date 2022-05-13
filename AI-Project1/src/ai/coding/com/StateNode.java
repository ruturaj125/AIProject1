package ai.coding.com;

import java.util.*;

public class StateNode implements Comparable<StateNode> {

	public int function_value;
	public int[][] positions;
	public int gn = 0;
	public static int input_type; 
	
	public StateNode(int[][] a, int level, int input)
	{
		StateNode.input_type = input;
		this.positions = new int[3][3];
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				this.positions[i][j] = a[i][j];
			}
		}
		this.gn = level;
		this.function_value = calculateFunctionValue();
	}
	
	public int calculateFunctionValue()
	{
		return calculateHNValue() + this.gn;
	}
	
	public int calculateHNValue()
	{
		int hn = 0;
		if(StateNode.input_type == 1)
		{
			hn = 0;
		}
		if(StateNode.input_type == 2)
		{
			hn = getMisplacedTileHeuristic();
		}
		if(StateNode.input_type == 3)
		{
			hn = getManhattanDistance();
		}
		
		return hn;
	}
	
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
	
	private int getManhattanDistance()
	{
		int ans=0;
		int[] index= new int[2];
		
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 3; j++)
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
	
	private int[] findPositionInGoal(int value)
	{
		int[] ans = new int[2];
		
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 3; j++)
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
	
	public ArrayList<StateNode> getChildrens()
	{
		ArrayList<StateNode> childrens = new ArrayList<StateNode>();
		
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				if(this.positions[i][j] == 0)
				{
					if(i-1>=0)
					{
						int[][] child = new int[3][3];
						for (int x = 0; x < 3; x++)
						{
							for(int y = 0; y < 3; y++)
							{
								child[x][y] = this.positions[x][y];
							}
						}
						 child = swap(child,i,j,i-1,j);
						 StateNode b = new StateNode(child,this.gn+1, StateNode.input_type);
						 childrens.add(b);
					}
					if(j-1>=0)
					{
						int[][] child = new int[3][3];
						for (int x = 0; x < 3; x++)
						{
							for(int y = 0; y < 3; y++)
							{
								child[x][y] = this.positions[x][y];
							}
						}
						 child = swap(child,i,j,i,j-1);
						 StateNode b = new StateNode(child,this.gn+1, StateNode.input_type);
						 childrens.add(b);
					}
					if(i+1<3)
					{
						int[][] child = new int[3][3];
						for (int x = 0; x < 3; x++)
						{
							for(int y = 0; y < 3; y++)
							{
								child[x][y] = this.positions[x][y];
							}
						}
						 child = swap(child,i,j,i+1,j);
						 StateNode b = new StateNode(child,this.gn+1, StateNode.input_type);
						 childrens.add(b);
					}
					if(j+1<3)
					{
						int[][] child = new int[3][3];
						for (int x = 0; x < 3; x++)
						{
							for(int y = 0; y < 3; y++)
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
	
	private int[][] swap(int[][] a,int row1, int col1, int row2, int col2)
	{
	     int[][] copy = a;
	     int tmp = copy[row1][col1];
	     copy[row1][col1] = copy[row2][col2];
	     copy[row2][col2] = tmp;
	
	     return copy;
	}

	@Override
	public int compareTo(StateNode o) {
		if(this.function_value == o.function_value)
		{
			return ((this.calculateHNValue() - o.calculateHNValue()));
		}
		return this.function_value - o.function_value;
	}
}
