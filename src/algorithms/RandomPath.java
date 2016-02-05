package algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RandomPath {
	
	private List<Integer> depotPath = new ArrayList<>();
	
    private int depotSize;
	public RandomPath(int depotSize)
	{
		this.depotSize = depotSize;	
	}
	public List<Integer> GetPath()
	{	
		for(int i = 0; i < depotSize; i++)
		{
			depotPath.add(i);
		}
		Collections.shuffle(depotPath);
		
		depotPath.set(depotPath.indexOf(0), depotPath.get(0));
		depotPath.set(0, 0);
		
		return depotPath;
	}

}
