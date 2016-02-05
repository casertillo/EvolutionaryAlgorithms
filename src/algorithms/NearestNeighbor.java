/******************************************************************************
 * 
 * NearestNeighbor class.
 * 
 * This class perform the simple nearest neighbor algorithm.
 * From a group of coordenates find the closes point to each one and creates a route
 * based on that solution
 * 
 * This algorithm is used as first step avoiding a random solution to start
 *****************************************************************************/
package algorithms;

import crvp.Common;
import crvp.Customer;
import crvp.Depot;

import java.util.List;
import java.util.ArrayList;

public class NearestNeighbor {

    private List<Integer> depotPath = new ArrayList<>();
    
    private Depot depotToOptimize;
	public NearestNeighbor(Depot depot)
	{
		
		depotPath.add(0); //which is our depot location
		depotToOptimize = depot;
		
	}
	
	public List<Integer> FindPath()
	{
	    Customer currentCity;
	    Customer nextCity;
	    
		currentCity = depotToOptimize.getCustomer(0);
		nextCity = depotToOptimize.getCustomer(1);
		float distance = 0;
		float minDistance = Integer.MAX_VALUE ;
		int i=1;
		int NextCityIndex = 1;
		
		//First Distance between depot and the first customer
		
		while(depotPath.size() < depotToOptimize.size())
		{
			
			minDistance = Integer.MAX_VALUE;
			i = 0;
			while(i < depotToOptimize.size() )
			{
				if(!depotPath.contains(i))
				{
					nextCity = depotToOptimize.getCustomer(i);
					distance = Common.getDistance(currentCity.getPositionX(), currentCity.getPositionY(), nextCity.getPositionX(), nextCity.getPositionY());
					if(distance < minDistance)
					{
						NextCityIndex = i;
						minDistance = distance;
					}
				}
				i++;
			}
			depotPath.add(NextCityIndex);
			currentCity = depotToOptimize.getCustomer(NextCityIndex);
		}
		
		return depotPath;
	}

}
