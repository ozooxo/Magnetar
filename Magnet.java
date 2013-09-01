import java.util.*;
import java.awt.*;
import java.awt.Point;
import java.awt.geom.*;

class Cell extends Point {
	// In here, "Cell" is always for the large pixel position, and "Point" is for the screen position.
	Cell (int x, int y) { super(x, y); }
	/* I use "extends Point" here, because if I only do
	 * int x, y;
	 * Cell (int x, int y) { this.x = x; this.y = y; }
	 * then "ArrayList<Cell> i = new ArrayList<>();" && "i.contains(~~~)" is not working.
	 * I don't fully understand why. */

	Cell shift (int dx, int dy) { return new Cell (x+dx, y+dy); }
	
	Point cellCenterScreen () { return new Point ((int)((x-0.5)*Screen.CELLSIZE), (int)((y-0.5)*Screen.CELLSIZE)); }
	Point[] cellCornersScreen () {
		Point[] position;
		position = new Point[4];
		position[0] = new Point ((x-1)*Screen.CELLSIZE, (y-1)*Screen.CELLSIZE);
		position[1] = new Point (x*Screen.CELLSIZE, (y-1)*Screen.CELLSIZE);
		position[2] = new Point (x*Screen.CELLSIZE, y*Screen.CELLSIZE);
		position[3] = new Point ((x-1)*Screen.CELLSIZE, y*Screen.CELLSIZE);
		return position;
	}
	
	boolean checkInBox (int xmin, int xmax, int ymin, int ymax) {
		if ((x < xmin) || (x > xmax) || (y < ymin) || (y > ymax))
		return false;
		else return true;
	}
	
	Cell randConjoined () {
		Random rand = new Random();
		int direction = rand.nextInt(4);
		if (direction == 0) return new Cell (x+1, y);
		else if (direction == 1) return new Cell (x-1, y);
		else if (direction == 2) return new Cell (x, y+1);
		else return new Cell (x, y-1);
	}
}

class Magnet {
	private Cell north, south;
	private boolean conjoined = false;
	private boolean checked = false;
	
	Magnet (Cell north, Cell south) {
		if ((north.x != south.x) && (north.y != south.y)) {
			System.out.println("The magnet is neigher horizontal or vertical: invalid.");
		} 
		else {
			this.north = north;
			this.south = south;
		}
	}
	
	Cell getNorth () { return north; }
	Cell getSouth () { return south; }
	
	boolean getConjoined () { return conjoined; }
	void conjoin () {
		conjoined = true;
		checked = true;
	}
	boolean getChecked () { return checked; }
	void check () { checked = true; }
	void resetChecked () { 
		if (conjoined == false) { checked = false; }
	}
	
	Cell[] getOccupied () {
		if (north.x == south.x) {
			Cell[] cells;
			cells = new Cell[Math.abs(north.y-south.y)+1];
			for (int i = 0; i < cells.length; ++i) {
				cells[i] = new Cell (north.x, Math.min(north.y, south.y)+i);
			}
			return cells;
		}
		else {
			Cell[] cells;
			cells = new Cell[Math.abs(north.x-south.x)+1];
			for (int i = 0; i < cells.length; ++i) {
				cells[i] = new Cell (Math.min(north.x, south.x)+i, north.y);
			}
			return cells;
		}
	}
	
	//------------------------------------------------------------------

	private Point midpoint (Point p, Point q) { return new Point ((int)(0.5*(p.x+q.x)), (int)(0.5*(p.y+q.y))); }

	// The cell in the left-top corner is (1,1).
	private Point northScreen () { return north.cellCenterScreen(); }
	private Point southScreen () { return south.cellCenterScreen(); }

	private int width () { return (Math.abs(north.x-south.x)+1)*Screen.CELLSIZE; }
	private int height () { return (Math.abs(north.y-south.y)+1)*Screen.CELLSIZE; }
	private Point centerScreen () { return new Point ((north.x+south.x-1)*Screen.CELLSIZE/2, (north.y+south.y-1)*Screen.CELLSIZE/2); }
	private Point lefttopScreen () { return new Point ((int)(centerScreen().x-0.5*width()), (int)(centerScreen().y-0.5*height())); }

	// Order: [northLeft, northRight, southRight, SouthLeft]
	private Point[] vertices () {
		Point[] position;
		position = new Point[4];
		if ((north.x == south.x) && (north.y < south.y)) {
			position[0] = north.cellCornersScreen()[0];
			position[1] = north.cellCornersScreen()[1];
			position[2] = south.cellCornersScreen()[2];
			position[3] = south.cellCornersScreen()[3];
			return position;
		}
		else if ((north.x == south.x) && (north.y >= south.y)) {
			position[0] = north.cellCornersScreen()[2];
			position[1] = north.cellCornersScreen()[3];
			position[2] = south.cellCornersScreen()[0];
			position[3] = south.cellCornersScreen()[1];
			return position;
		}
		else if ((north.y == south.y) && (north.x < south.x)) {
			position[0] = north.cellCornersScreen()[3];
			position[1] = north.cellCornersScreen()[0];
			position[2] = south.cellCornersScreen()[1];
			position[3] = south.cellCornersScreen()[2];
			return position;
		}
		else {
			position[0] = north.cellCornersScreen()[1];
			position[1] = north.cellCornersScreen()[2];
			position[2] = south.cellCornersScreen()[3];
			position[3] = south.cellCornersScreen()[0];
			return position;
		}
	}

	void draw (Graphics2D g2) {
		g2.setColor(Color.BLUE);
		g2.fillRect(lefttopScreen().x, lefttopScreen().y, width(), height());

		g2.setColor(Color.RED);
		if ((north.x == south.x) && (north.y < south.y)) {
			g2.fillRect(lefttopScreen().x, lefttopScreen().y, width(), height()/2);
		}
		else if ((north.x == south.x) && (north.y >= south.y)) {
			Point tmp = midpoint(vertices()[1], vertices()[2]); 
			g2.fillRect(tmp.x, tmp.y, width(), height()/2);
		}
		else if ((north.y == south.y) && (north.x < south.x)) {
			g2.fillRect(lefttopScreen().x, lefttopScreen().y, width()/2, height());
		}
		else {
			Point tmp = midpoint(vertices()[0], vertices()[3]); 
			g2.fillRect(tmp.x, tmp.y, width()/2, height());
		}
		
		g2.setColor(Color.WHITE);
		g2.setFont(new Font("Arial", Font.PLAIN, Screen.POLEFONTSIZE));
		g2.drawString("N", northScreen().x-(int)(0.42*Screen.POLEFONTSIZE), northScreen().y+(int)(0.42*Screen.POLEFONTSIZE));
		g2.drawString("S", southScreen().x-(int)(0.42*Screen.POLEFONTSIZE), southScreen().y+(int)(0.42*Screen.POLEFONTSIZE));
	}
	
	//------------------------------------------------------------------
	
	public void move (boolean force, int dx, int dy) {
		if ((force == true) || (conjoined == true)) {
			north = new Cell (north.x+dx, north.y+dy);
			south = new Cell (south.x+dx, south.y+dy);
		}
	}
	
	Magnet shift (int dx, int dy) {
		Cell n = new Cell (north.x+dx, north.y+dy);
		Cell s = new Cell (south.x+dx, south.y+dy);
		return new Magnet (n, s);
	}
}
