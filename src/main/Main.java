/******************************************************************************
 * 
 * Main class.
 * 
 * Author - Sergio Castillo
 * 
 * VRP-genetic tries to find the best solution for the CVPR problem
 *****************************************************************************/
package main;

import algorithms.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import crvp.*;
import graphics.RouteVisualiser;
import graphics.TimeGraph;

public class Main {

	public static void main(String[] args) {
        
		/*
		 * Source code to print all messages to a log
		 * http://www.codeproject.com/Tips/315892/A-quick-and-easy-way-to-direct-Java-System-out-to
		 */
		try
		{
			FileOutputStream fout= new FileOutputStream("stdout.log");
			FileOutputStream ferr= new FileOutputStream("stderr.log");
			
			MultiOutputStream multiOut= new MultiOutputStream(System.out, fout);
			MultiOutputStream multiErr= new MultiOutputStream(System.err, ferr);
			
			PrintStream stdout= new PrintStream(multiOut);
			PrintStream stderr= new PrintStream(multiErr);
			
			System.setOut(stdout);
			System.setErr(stderr);
		}
		catch (FileNotFoundException ex)
		{
			//Could not create/open the file
		}
		/*
		 * Finish initialization to star writing in a file*/
		
		//Number of iterations to test
		//-----------------------------
		int niters = 1600000;
		//-----------------------------
		
		//Store solutions
		List<List<Integer>> Solutions = new ArrayList<List<Integer>>();
		List<Float> SolutionsWeights = new ArrayList<Float>();
		
		//Initialize the program
		System.out.println("------------------------------------------------------------");
		System.out.println("-------------------START CRVP SOLUTION----------------------");
		System.out.println("------------------------------------------------------------");
		//Create the depot that will contain most of the information
		Depot depot = new Depot(-33, 33, 500);
		
		//Specify Depot coordinates
		System.out.println("Creating our Depot on x: "+depot.getPositionX()+" y: "+depot.getPositionY()+" truck capacity: "+ depot.getTruckCapacity());
		
		//We add our data from the Data Class, a simple two arrays to a class
		System.out.println("Adding customers...");
		depot.Chromosome(Data.customerCoordenates.length);
		System.out.println("Done with customers...");
		
		//--------------------------------------------------------------------
		//USING AN IMPROVED SOLUTION SINCE BEGINING WITH Nearest Neighbor ALgorithm
		//--------------------------------------------------------------------
		// Initial Best Solution with NN algorithm.
		NearestNeighbor ng = new NearestNeighbor(depot);
		List<Integer> Nearestpath = ng.FindPath();
		System.out.println("Calculating initial cost using nearest neighbor...");
		 Common.calculateCost(Nearestpath, depot);
		 System.out.println("Printing initial nearest neighbor results(this never change)...");
		 Common.printRoute(Nearestpath, depot);
		
		 System.out.println("Starting hillclimber no crossover...");
		 Nearestpath = HillClimber.run(niters, Nearestpath, depot, false);
		 //Print the route on the map
		 Common.printRoute(Nearestpath, depot);
		 
		 //Adding first path
		 Solutions.add(Nearestpath);
		 SolutionsWeights.add(Common.calculateCost(Nearestpath, depot));
		 
		 //Save our results in a graph
		 double[] weights = Common.convertDoubles(HillClimber.weights);
		 TimeGraph tg = new TimeGraph(true);
		 tg.addResults(weights, "NN & HillC.");
		 
		 //create the map
		 RouteVisualiser rv = new RouteVisualiser(true);
		 rv.getNodeMap(depot);
		 rv.drawPaths(Common.getRouteMap(Nearestpath, depot), depot);
		 rv.drawKey(Common.getRouteMap(Nearestpath, depot), depot);
		 rv.saveImage("NN-HillClimber-false");
		 System.out.println("Done hillclimber with nearest neighbor, no crossover...");
		//--------------------------------------------------------------------
		//Start using a random solution
		//--------------------------------------------------------------------	
		 HillClimber.weights.clear();
		 RandomPath rp = new RandomPath(depot.size());
		 List<Integer> RandomPath = rp.GetPath();
		System.out.println("Calculating initial cost using a random path...");
		Common.calculateCost(RandomPath, depot);
		System.out.println("Printing  Random path results...");
		Common.printRoute(RandomPath, depot);
		 
		 System.out.println("Starting hillclimber with random path no crossover...");
		 RandomPath = HillClimber.run(niters, RandomPath, depot, false);
		 //Print the route on the map
		 Common.printRoute(RandomPath, depot);
		 
		 //Adding second path
		 Solutions.add(RandomPath);
		 SolutionsWeights.add(Common.calculateCost(RandomPath, depot));
		 
		 //Save our results in a graph
		 weights = Common.convertDoubles(HillClimber.weights);
		 tg.addResults(weights, "Rnd & HillC.");
		 
		 //create the map
		 rv = new RouteVisualiser(true);
		 rv.getNodeMap(depot);
		 rv.drawPaths(Common.getRouteMap(RandomPath, depot), depot);
		 rv.drawKey(Common.getRouteMap(RandomPath, depot), depot);
		 rv.saveImage("Rnd-HillClimber-false");
		 System.out.println("Done hillclimber with Random Path no crossover...");

		 /*NOW USING CROSSOVER
		  * *******************************************************
		  * *******************************************************
		  * */
		 //--------------------------------------------------------------------
		 //USING AN IMPROVED SOLUTION SINCE BEGINING WITH Nearest Neighbor ALgorithm
		 //--------------------------------------------------------------------
		 HillClimber.weights.clear();
		 // Initial Best Solution with NN algorithm.
		 
		 System.out.println("Starting hillclimber with nearest neighbor and crossover...");
		 List<Integer> Nearestpath2 = ng.FindPath();
		 Nearestpath = HillClimber.run(niters, Nearestpath2, depot, true);
		 //Print the route on the map
		 Common.printRoute(Nearestpath, depot);
		 
		 //Adding third path
		 Solutions.add(Nearestpath);
		 SolutionsWeights.add(Common.calculateCost(Nearestpath, depot));
		 
		 //Save our results in a graph
		 weights = Common.convertDoubles(HillClimber.weights);
		 tg.addResults(weights, "NN&HillC. cross");
		 
		 //create the map
		 rv = new RouteVisualiser(true);
		 rv.getNodeMap(depot);
		 rv.drawPaths(Common.getRouteMap(Nearestpath, depot), depot);
		 rv.drawKey(Common.getRouteMap(Nearestpath, depot), depot);
		 rv.saveImage("NN-HillClimber-true");
		 System.out.println("Done hillclimber with nearest neighbor and crossover...");
		//--------------------------------------------------------------------
		//Start using a random solution
		//--------------------------------------------------------------------	
		 HillClimber.weights.clear();
		 rp = new RandomPath(depot.size());
		 RandomPath = rp.GetPath();
		 System.out.println("Calculating initial cost using a random path...");
		 Common.calculateCost(RandomPath, depot);
		 System.out.println("Printing  Random path results...");
		 Common.printRoute(RandomPath, depot);
		 
		 System.out.println("Starting hillclimber with random path...");
		 RandomPath = HillClimber.run(niters, RandomPath, depot, true);
		 //Print the route on the map
		 Common.printRoute(RandomPath, depot);
		 
		 
		 //Adding third path
		 Solutions.add(RandomPath);
		 SolutionsWeights.add(Common.calculateCost(RandomPath, depot));
		 
		 //Save our results in a graph
		 weights = Common.convertDoubles(HillClimber.weights);
		 tg.addResults(weights, "Rnd&HillC. cross");
			 
		 //create the map
		 rv = new RouteVisualiser(true);
		 rv.getNodeMap(depot);
		 rv.drawPaths(Common.getRouteMap(RandomPath, depot), depot);
		 rv.drawKey(Common.getRouteMap(RandomPath, depot), depot);
		 rv.saveImage("Rnd-HillClimber-true");
		 System.out.println("Done hillclimber with Random Path and crossover...");
		 tg.render();	
		 tg.save("RandomvsNearest");
		
		 //SEND MESSAGES TO FILES CREATED
		 System.out.println("Chart created RandomvsNearest.png...");
		 System.out.println("NN-HillClimber-false-route.png created...");
		 System.out.println("NN-HillClimber-true-route.png created...");
		 System.out.println("Rnd-HillClimber-false-route.png created...");
		 System.out.println("Rnd-HillClimber-true-route.png created");
		 System.out.println("stdout.log created contains path format and program log...");
		 System.out.println("------------PRINTING BEST SOLUTION AS FORMAT----------------");
		 System.out.println("------------------------------------------------------------");
		 System.out.println("------------------------------------------------------------");
		 System.out.println("login jc15275 26479");
		 System.out.println("name Sergio Castillo");
		 System.out.println("algorithm first parent nearest neighbor and RMHC using 2-swap and instert mutation with ERCO crossover");
		 System.out.println("cost "+Collections.min(SolutionsWeights));
		 Common.printRoute(Solutions.get(SolutionsWeights.indexOf(Collections.min(SolutionsWeights))), depot);
		 System.out.println("------------------------------------------------------------");
		 System.out.println("---------------------------DONE-----------------------------");
	}
}
