/******************************************************************************
 * 
 * Route Visualizer class. Outputs the arrays provided as a graph.
 * 
 * Shows the path for each truck and total length for each path
 * 
 *****************************************************************************/

package graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import crvp.Common;
import crvp.Depot;

public class RouteVisualiser {
	
	private BufferedImage b;
	private Graphics2D g;
	
	int xscale = 99;
	int yscale = -99;
	double xmulscale;
	double ymulscale;
	
	public RouteVisualiser(boolean withKey){
		b = new BufferedImage(withKey ? 1920 : 770 ,1080,BufferedImage.TYPE_INT_RGB);
		g = b.createGraphics();
		
		xmulscale = (b.getWidth()-130)/200;
		ymulscale = b.getHeight()/200;
	}
	
	public void getNodeMap(Depot depot){

		char[] s = String.valueOf(depot.getCustomer(0).getDemand()).toCharArray();
		g.setColor(Color.green);
		g.drawOval((int)((depot.getCustomer(0).getPositionX()+xscale)*xmulscale),(int)((depot.getCustomer(0).getPositionY()+yscale)*-ymulscale), 2, 2);
		g.setColor(Color.green);
		g.drawChars( s, 0, s.length, (int)((depot.getCustomer(0).getPositionX()+xscale)*xmulscale), (int)((depot.getCustomer(0).getPositionY()+yscale)*-ymulscale));
		for(int i = 1; i < depot.size(); i++){
			s = String.valueOf(depot.getCustomer(i).getDemand()).toCharArray();
			g.setColor(Color.red);
			g.drawOval((int)((depot.getCustomer(i).getPositionX()+xscale)*xmulscale), (int)((depot.getCustomer(i).getPositionY()+yscale)*-ymulscale), 2, 2);
			g.setColor(Color.white);
			g.drawChars( s, 0, s.length,(int)((depot.getCustomer(i).getPositionX()+xscale)*xmulscale),(int)((depot.getCustomer(i).getPositionY()+yscale)*-ymulscale));
		}
	}
	
	public void drawPaths(int[][] paths, Depot depot){
		for (int i = 0; i < paths.length && paths[i] != null; i++ ){
			g.setColor(Color.getHSBColor((float)((0.19 * i + 1.0) % 1.0), 0.8f, 1.0f));
			for(int j = 0; j < paths[i].length-1; j++){
				g.drawLine((int)((depot.getCustomer(paths[i][j]).getPositionX()+xscale)*xmulscale),(int)((depot.getCustomer(paths[i][j]).getPositionY()+yscale)*-ymulscale),
						(int)((depot.getCustomer(paths[i][j+1]).getPositionX()+xscale)*xmulscale) , (int)((depot.getCustomer(paths[i][j+1]).getPositionY()+yscale)*-ymulscale) );
			}
		}
	}

	public double getPathDistance(int[] paths, Depot depot){
		double distance = 0;
		for (int i = 0; i < paths.length -1 && paths != null; i++ ){
			distance += Common.getDistance(depot.getCustomer(paths[i]).getPositionX() , depot.getCustomer(paths[i]).getPositionY(), depot.getCustomer(paths[i+1]).getPositionX(), depot.getCustomer(paths[i+1]).getPositionY());
		}
		return distance;
	}
	public void drawKey(int[][] paths, Depot depot){
		
		int numPaths = 0;
		int i = 0;
		while(paths[i++] != null){
			numPaths++;
		}
		
		int boxtop = ( b.getHeight() - (numPaths * 30 + 50)) / 2;
		
		// Draw a box for the key
		g.setColor(Color.white);
		g.drawRect(1730, boxtop, 180, numPaths * 30 + 50);
		// Add the title
		String title = new String("Path Total Weights");
		g.drawChars(title.toCharArray(), 0, title.length(), 1750, boxtop + 30);
		
		for(i = 0; i < numPaths; i++){
			g.setColor(Color.getHSBColor((float)((0.19 * i + 1.0) % 1.0), 0.8f, 1.0f));
			g.drawLine(1750, boxtop + 50 + i*30, 1800, boxtop + 50 + i*30);
			String weight = String.format("%1$,.2f", 
					getPathDistance(paths[i], depot));
			g.setColor(Color.white);
			g.drawChars(weight.toCharArray(), 0, weight.length(), 1810, boxtop + 55 + i*30);
		}
	}
	public void saveImage(String filename){
		try{ImageIO.write(b,"png",new File(filename + "-route.png"));}catch (Exception e) {}
	}

}
