/******************************************************************************
 * 
 * Customer class.
 * 
 * This class store the position and the demand for each customer

 *****************************************************************************/
package crvp;

public class Customer {
	
	private int positionX;
	private int positionY;
	private int demand;
	
	public Customer(int x, int y, int demand)
	{
		this.positionX = x;
		this.positionY = y;
		this.demand = demand;
	}

	public int getPositionX() {
		return positionX;
	}

	public int getPositionY() {
		return positionY;
	}

	public int getDemand() {
		return demand;
	}

}
