/******************************************************************************
 * 
 * Depot class.
 * 
 * One of the main classes, this function contains a group of customers,
 * the depot location, and its trucks capacity
 *****************************************************************************/
package crvp;

public class Depot {
	
	//declare the x and y position of our unique depot
	private int positionX;
	private int positionY;
	
	//In this example there is an equal capacity for all trucks
	private int truckcapacity;
	
	//The depot contains their entire population of customers
	Customer[] customers;
	
	private int customerSize;
	
	public Depot(int positionx, int positiony, int truckcapacity)
	{
		this.positionX = positionx;
		this.positionY = positiony;
		this.truckcapacity = truckcapacity;
	}

	public int getPositionX() {
		return positionX;
	}

	public int getPositionY() {
		return positionY;
	}
	
	public int getTruckCapacity() {
		return truckcapacity;
	}
	
	//Create Chromosome
	public void Chromosome(int populationSize){
		
		//Add one to our population to set the zero "customer" which is our depot
		int size = populationSize + 1;
		setCustomerSize(size);
		customers = new Customer[size()];
		
		Customer newcustomer = new Customer(this.getPositionX(),this.getPositionY(), 0);
		saveCustomer(0, newcustomer);
		
		for(int i=0; i<size()-1; i++){
			newcustomer = new Customer(Data.customerCoordenates[i][0],Data.customerCoordenates[i][1], Data.customerDemand[i]);
			saveCustomer(i+1, newcustomer);
		}
	}
	
	//Save Customer
	public void saveCustomer(int index, Customer  cust){
		customers[index] = cust;
	}
	
	//Getters
	public Customer getCustomer(int index){
		return customers[index];
	}

	public int size() {
		return customerSize;
	}

	public void setCustomerSize(int customerSize) {
		this.customerSize = customerSize;
	}
	
}
