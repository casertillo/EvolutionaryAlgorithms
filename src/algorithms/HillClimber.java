/******************************************************************************
 * 
 * HillCLimber class.
 * 
 * This class receive a path from a solution and works improving that path
 * It decide either use 2-swap or insert, then creates a new path and compares
 * with the best solution, if the new solution is better then removes the posterior
 * 
 * 
 *****************************************************************************/
package algorithms;

import crvp.Common;
import crvp.Depot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class HillClimber {
	
	//Use the unformRate to select either 2-swap or insertion
	static double uniformRate = 0.5;
	static double crossOverRate = 500;
	static int sameIter = 0;
	
	public static ArrayList<Double> weights = new ArrayList<Double>();

	public static List<Integer> run(int iterations, List<Integer> path, Depot depot, boolean crossover)
	{
		//Our initial fittest path
		List<Integer> fittest = path;
		//Our proposal path
		List<Integer> prop = new ArrayList<Integer>(fittest);
		
		//fittest cost
		double fitcost = 0.0;

		//proposal cost
		double propcost = 0.0;
		

	
		for(int i = 0; i<iterations; i++)
		{
			//TYPES OF MUTATION SWAP OR INSERT FROM PARENT
			//---------------------------------
			
			if(Math.random() <= uniformRate)
			{
				prop = swap(fittest);
			}
			else
			{
				prop = insert(fittest);
			}

			//Calculate fitcost
			fitcost = Common.calculateCost(fittest, depot);
			//Our proposal
			
			//Adding our best results for the graphic
			weights.add(fitcost);
			
			//Comparing when using crossover
			if(crossover && sameIter > crossOverRate)
			{
				//Generates a random path
				RandomPath rp = new RandomPath(depot.size());
				//restart the counter for same results
				sameIter = 0;
				//resulting cost of the ERC
				propcost = Common.calculateCost(erc(fittest, rp.GetPath()), depot);
			}
			else
			{
				//if no crossover just get proposal cost
				propcost = Common.calculateCost(prop, depot);
			}
			
			//In case the proposal is better than fittest change
			if(fitcost > propcost)
			{
				fittest = prop;
			}
			else
			{
				sameIter++;
			}
		}
		
		return fittest;	
	}
	
	private static List<Integer> swap(List<Integer> fittest)
	{
		List<Integer> prop = new ArrayList<Integer>(fittest);
		
		//Select two random customers 
		int gene1 = (int) (Math.random()*((249 - 1) +1))+1;
		int gene2 = (int) (Math.random()*((249 - 1) +1))+1;
		
		//Change our values from gene 1 to gene two 
		int swap = prop.get(gene1);
		prop.set(gene1, prop.get(gene2));
		prop.set(gene2, swap);
		
		return prop;
	}

	private static List<Integer> insert(List<Integer> fittest)
	{
		List<Integer> prop = new ArrayList<Integer>(fittest);
		
		//select the gene that will be insert
		int geneToInsert = (int) (Math.random()*((249 - 1) +1))+1;
		
		//where is going to be place the gene
		int placeToInsert = (int) (Math.random()*((249 - 1) +1))+1;
		int tmp = 0;
		
		if(geneToInsert > placeToInsert)
		{
			//backward
			tmp = prop.get(geneToInsert);
			for(int i = geneToInsert; i > placeToInsert; i--)
			{
				prop.set(i, prop.get(i-1));	
			}
			prop.set(placeToInsert, tmp);
		}
		else
		{
			//Forward
			tmp = prop.get(geneToInsert);
			for(int i = geneToInsert; i < placeToInsert; i++)
			{
				prop.set(i, prop.get(i+1));
			}
			prop.set(placeToInsert, tmp);
		}
		
		return prop;
	}

	private static List<Integer> erc(List<Integer> fittest, List<Integer> prop)
	{
		//Every customer with id 0 to 249 will have n number of neighbors
		List<List<Integer>> neighborsList = new ArrayList<List<Integer>>();
	
		//temporary list to stores the total neighbors of each customer
		List<Integer> neighbors = new ArrayList<Integer>();
		
		//Final new solution
		List<Integer> newsol = new ArrayList<Integer>();
		
		//Parents
		List<Integer> parent1 = new ArrayList<Integer>(fittest);
		List<Integer> parent2 = new ArrayList<Integer>(prop);
		
		//Add neighbors for depot tha never change 0
		neighbors.add(parent1.get(1));
		neighbors.add(parent2.get(1));
		neighbors.add(parent1.get(parent1.size()-1));
		neighbors.add(parent2.get(parent2.size()-1));
		
		//Using HashSet we remove repeated values
		List cleanlist = new ArrayList(new HashSet(neighbors)); 
		
		//Initialize neighborsList
		for(int i= 0; i<fittest.size(); i++)
		{
			neighborsList.add(cleanlist);
		}
		neighbors.clear();
		
		//This for fills the neighborsList 
		for(int i = 1; i<parent1.size(); i++)
		{
			if((parent1.indexOf(i)+1) == parent1.size())
			{
				neighbors.add(parent1.get(parent1.indexOf(0)));
			}
			else
			{
				neighbors.add(parent1.get(parent1.indexOf(i)+1));
			}
			if((parent2.indexOf(i)+1) == parent2.size())
			{
				neighbors.add(parent2.get(parent2.indexOf(0)));
			}
			else
			{
				neighbors.add(parent2.get(parent2.indexOf(i)+1));
			}

			neighbors.add(parent1.get(parent1.indexOf(i)-1));
			neighbors.add(parent2.get(parent2.indexOf(i)-1));
			
			cleanlist = new ArrayList(new HashSet(neighbors)); 
			neighborsList.set(i, cleanlist);
			neighbors.clear();
		}
		int next = 0;
		//Now start creating the new solution using the neihgborsList
		for(int i = 0; i < parent1.size(); i++)
		{
			newsol.add(next);
			
	    	for(List<Integer> lists : neighborsList)
	    	{
	    		if(lists != null && lists.contains(next))
	    		{
	    			lists.remove(lists.indexOf(next));
	    		}
	    	}
	    	neighborsList.set(next, null);
	    	
	    	//Now count the number of elements by neighbors 	
	    	int localindex = 0;
	    	int min = 20;
	    	List<Integer> minList = new ArrayList<Integer>();
	    	for(List<Integer> lists : neighborsList)
	    	{
	    		if(lists != null)
	    		{
	    			if(lists.size() < min)
	    			{
	    				minList.clear();
	    				min = lists.size();
	    				minList.add(localindex);
	    			}
	    			else if(lists.size() == min)
	    			{
	    				minList.add(localindex);
	    			}
	    		}
	    		localindex++;
	    	}
	    	if(min == 0)
	    	{
	    		return newsol;
	    	}
	    	if(minList.size() > 1)
	    	{
	    		int random = (int)Math.random()*(minList.size()+1);
	    		next = minList.get(random);
	    	}
	    	else
	    	{
	    		next = minList.get(0);
	    	}
	    	minList.clear();
		}
		return newsol;
	}
	
	//Used for testing but did not work as expected leave it as proof.
	/*
	private static List<Integer> inversion(List<Integer> fittest)
	{
		List<Integer> prop = new ArrayList<Integer>(fittest);
		
		List<Integer> temp = new ArrayList<Integer>();
		
		//Right is 1, left is 2
		int rl = (int) (Math.random()*((2 - 1) +1))+1;
		
		int p1 = (int) (Math.random()*((239 - 11) +1))+11;
		int p2 = (int) (Math.random()*((10 - 1) +1))+1;
		int count = 0;
		if(rl == 1)
		{
			for(int i = p1; i<= (p1+p2); i++)
			{
				temp.add(fittest.get(i));
			}
			
			for(int i=(p1+p2); i >=p1; i--)
			{
				prop.set(i, temp.get(count));
				count++;
			}
		}
		else
		{
			for(int i =(p1-p2); i<= p1; i++)
			{
				temp.add(fittest.get(i));
			}
			
			for(int i=p1; i >=(p1-p2); i--)
			{
				prop.set(i, temp.get(count));
				count++;
			}
		}
		
		return prop;
	}
	*/
}
	