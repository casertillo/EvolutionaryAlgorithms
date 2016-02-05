/******************************************************************************
 * 
 * Common class.
 * 
 * This class is used to calculate distance, print the route, calculate the route
 * 
 * 
 *****************************************************************************/
package crvp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Common {

    /** Return the Euclidean distance between the two given nodes */
    public static float getDistance(int x1, int y1, int x2, int y2) {
    	
    	float dist = (float)Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
    	
    	return dist;
	}	

    public static int[][] getRouteMap(List<Integer> path, Depot depot)
    {
        int[][] routePaths = new int[28][];
    	float totalCost = 0;
    	int truckCapacity = depot.getTruckCapacity();
    	
    	int truckLocation = 0;
    	int truckItemsLeft = truckCapacity;
    	int trucksUsed = 1;
    	int i;
    	ArrayList<Integer> routpath = new ArrayList<Integer>();
    	
    	for(i = 0; i < path.size()-1; i++ )
    	{
    		//If the truck can supply next customer do the trip
    		if(truckItemsLeft >= depot.getCustomer(path.get(i+1)).getDemand() )
    		{
    			//WHERE TRUCK STARTS
    			routpath.add(truckLocation);
    			
    			//now sum the cost from truckcurrentLocation to nextLocation
    			totalCost = totalCost + 
    					getDistance(depot.getCustomer(truckLocation).getPositionX(), depot.getCustomer(truckLocation).getPositionY(),  
    								depot.getCustomer(path.get(i+1)).getPositionX(), depot.getCustomer(path.get(i+1)).getPositionY()
    							);
    			
    			//REST THE DELIVER
    			truckItemsLeft = truckItemsLeft - depot.getCustomer(path.get(i+1)).getDemand();
    			
    			
    			//make the travel to next location
    			truckLocation = path.get(i+1);
    		}
    		//else go back to depot, sum the trip to depot and reset location on depot.
    		else
    		{
    			routpath.add(truckLocation);
    			routpath.add(0);
    			
    			routePaths[trucksUsed-1] =  convertIntegers(routpath);
    			routpath.clear();
    			//GOing Back to the depot
    			totalCost = totalCost + 
    					getDistance(depot.getCustomer(truckLocation).getPositionX(), depot.getCustomer(truckLocation).getPositionY(),  
								depot.getPositionX(), depot.getPositionY()
							);
    			truckLocation = 0;
    			truckItemsLeft = 500;
    			trucksUsed++;
    			
    			//Make the first trip to the depot that could't been supplied earlier
    			routpath.add(truckLocation);
    			
    			//now sum the cost from truckcurrentLocation to nextLocation
    			totalCost = totalCost + 
    					getDistance(depot.getCustomer(truckLocation).getPositionX(), depot.getCustomer(truckLocation).getPositionY(),  
    								depot.getCustomer(path.get(i+1)).getPositionX(), depot.getCustomer(path.get(i+1)).getPositionY()
    							);
    			//Remove items
    			truckItemsLeft = truckItemsLeft - depot.getCustomer(path.get(i+1)).getDemand();
    			
    			//make the travel to next location
    			truckLocation = path.get(i+1);
    		}
    	}
    	routpath.add(truckLocation);
    	routpath.add(0);
    	routePaths[trucksUsed-1] =  convertIntegers(routpath);
    	
    	//Add cost from last location to depot
		//now sum the cost from truckcurrentLocation to nextLocation
		totalCost = totalCost + 
				getDistance(depot.getCustomer(truckLocation).getPositionX(), depot.getCustomer(truckLocation).getPositionY(),  
							depot.getPositionX(), depot.getPositionY()
						);	
		return routePaths;
    }
	public static void printRoute(List<Integer> path, Depot depot)
    {
    	float totalCost = 0;
    	int truckCapacity = depot.getTruckCapacity();
    	
    	int truckLocation = 0;
    	int truckItemsLeft = truckCapacity;
    	int trucksUsed = 1;
    	int i;
    	
    	for(i = 0; i < path.size()-1; i++ )
    	{
    		//If the truck can supply next customer do the trip
    		if(truckItemsLeft >= depot.getCustomer(path.get(i+1)).getDemand() )
    		{
    			//WHERE TRUCK STARTS
    			System.out.print((truckLocation+1)+"->");
    			
    			//now sum the cost from truckcurrentLocation to nextLocation
    			totalCost = totalCost + 
    					getDistance(depot.getCustomer(truckLocation).getPositionX(), depot.getCustomer(truckLocation).getPositionY(),  
    								depot.getCustomer(path.get(i+1)).getPositionX(), depot.getCustomer(path.get(i+1)).getPositionY()
    							);
    			
    			//REST THE DELIVER
    			truckItemsLeft = truckItemsLeft - depot.getCustomer(path.get(i+1)).getDemand();
    			
    			
    			//make the travel to next location
    			truckLocation = path.get(i+1);
    		}
    		//else go back to depot, sum the trip to depot and reset location on depot.
    		else
    		{	
    			System.out.print((truckLocation+1)+"->1");
    			
    			System.out.println();
    			
    			//GOing Back to the depot
    			totalCost = totalCost + 
    					getDistance(depot.getCustomer(truckLocation).getPositionX(), depot.getCustomer(truckLocation).getPositionY(),  
								depot.getPositionX(), depot.getPositionY()
							);
    			truckLocation = 0;
    			truckItemsLeft = 500;
    			trucksUsed++;
    			
    			//Make the first trip to the depot that could't been supplied earlier
    			System.out.print((truckLocation+1)+"->");
    			
    			//now sum the cost from truckcurrentLocation to nextLocation
    			totalCost = totalCost + 
    					getDistance(depot.getCustomer(truckLocation).getPositionX(), depot.getCustomer(truckLocation).getPositionY(),  
    								depot.getCustomer(path.get(i+1)).getPositionX(), depot.getCustomer(path.get(i+1)).getPositionY()
    							);
    			//Remove items
    			truckItemsLeft = truckItemsLeft - depot.getCustomer(path.get(i+1)).getDemand();
    			
    			//make the travel to next location
    			truckLocation = path.get(i+1);
    		}
    	}
    	
    	
    	//Add cost from last location to depot
		//now sum the cost from truckcurrentLocation to nextLocation
		totalCost = totalCost + 
				getDistance(depot.getCustomer(truckLocation).getPositionX(), depot.getCustomer(truckLocation).getPositionY(),  
							depot.getPositionX(), depot.getPositionY()
						);
		//Print when is back
		System.out.print((truckLocation+1)+"->1");
		System.out.println();
		System.out.println("------------------------------------------------------------");
    	System.out.println("Used trucks: "+ trucksUsed);
    	System.out.println("Total Cost: " + totalCost);	
    	System.out.println("------------------------------------------------------------");
    }
    public static float calculateCost(List<Integer> path, Depot depot)
    {
    	float totalCost = 0;
    	int truckCapacity = depot.getTruckCapacity();
    	
    	int truckLocation = 0;
    	int truckItemsLeft = truckCapacity;
    	
    	for(int i = 0; i < path.size()-1; i++ )
    	{
    		//If the truck can supply next customer do the trip
    		if(truckItemsLeft >= depot.getCustomer(path.get(i+1)).getDemand() )
    		{
    			//now sum the cost from truckcurrentLocation to nextLocation
    			totalCost = totalCost + 
    					getDistance(depot.getCustomer(truckLocation).getPositionX(), depot.getCustomer(truckLocation).getPositionY(),  
    								depot.getCustomer(path.get(i+1)).getPositionX(), depot.getCustomer(path.get(i+1)).getPositionY()
    							);
    			
    			//REST THE THAT DELIVER
    			truckItemsLeft = truckItemsLeft - depot.getCustomer(path.get(i+1)).getDemand();
    			
    			//make the travel to next location
    			truckLocation = path.get(i+1);
    		}
    		//else go back to depot, sum the trip to depot and reset location on depot.
    		else
    		{
    			
    			//GOing Back to the depot
    			totalCost = totalCost + 
    					getDistance(depot.getCustomer(truckLocation).getPositionX(), depot.getCustomer(truckLocation).getPositionY(),  
								depot.getPositionX(), depot.getPositionY()
							);
    			truckLocation = 0;
    			truckItemsLeft = 500;
    			
    			//now sum the cost from truckcurrentLocation to nextLocation
    			totalCost = totalCost + 
    					getDistance(depot.getCustomer(truckLocation).getPositionX(), depot.getCustomer(truckLocation).getPositionY(),  
    								depot.getCustomer(path.get(i+1)).getPositionX(), depot.getCustomer(path.get(i+1)).getPositionY()
    							);
    			//Remove items
    			truckItemsLeft = truckItemsLeft - depot.getCustomer(path.get(i+1)).getDemand();
    			
    			//make the travel to next location
    			truckLocation = path.get(i+1);
    			
    		}
    	}
    	
    	//Add cost from last location to depot
		//now sum the cost from truckcurrentLocation to nextLocation
		totalCost = totalCost + 
				getDistance(depot.getCustomer(truckLocation).getPositionX(), depot.getCustomer(truckLocation).getPositionY(),  
							depot.getPositionX(), depot.getPositionY()
						);
    	return totalCost;
    }
  
	public static double[] convertDoubles(List<Double> doubles)	{
	    double[] ret = new double[doubles.size()];
	    Iterator<Double> iterator = doubles.iterator();
	    for (int i = 0; i < ret.length; i++)
	    {
	        ret[i] = iterator.next().doubleValue();
	    }
	    return ret;
	}
	
	// Private method for converting arraylist<Integer> to int[]
	public static int[] convertIntegers(List<Integer> integers)	{
	    int[] ret = new int[integers.size()];
	    Iterator<Integer> iterator = integers.iterator();
	    for (int i = 0; i < ret.length; i++)
	    {
	        ret[i] = iterator.next().intValue();
	    }
	    return ret;
	}
 
}
