import java.util.*;
import java.awt.*;
import java.awt.event.KeyEvent;

class MagnetArray {

	private int dx = 0, dy = 0;
	private Magnet[] mArray; 
	
	SolutionArray sArray;
	InitialArray iArray;
	
	private Magnet[] duplication (Magnet[] array) {
		Magnet[] newArray;
		newArray = new Magnet[array.length];
		for (int i = 0; i < array.length; ++i) {
			// Using "shift(0, 0)" is a little bit silly, but it is the easiest way I found which can
			// truely duplicate two Magnet[] arrays, rather than just link the relavent items.
			newArray[i] = array[i].shift(0, 0); 
		}
		return newArray;
	}
	
	MagnetArray () {
		mArray = new Magnet[Screen.MAGNETNUM];
		sArray = new SolutionArray(Screen.MAGNETNUM);
		iArray = new InitialArray(sArray.getArray());
		mArray = duplication(iArray.getArray());
		mArray[0].conjoin();
	}
	
	void draw (Graphics2D g2) {
		for (Magnet m: mArray) { m.draw(g2); }
	}

	Magnet[] getArray () { return mArray; }

	//------------------------------------------------------------------
	
	void move () {
		for (Magnet m: mArray) {
			m.move(false, dx, dy);
		}

		// Find the magnets which can be pushed.
		ArrayList<Cell> occupiedList = new ArrayList<>();
		for (Magnet m: mArray) {
			if (m.getConjoined()) {
				occupiedList.addAll(Arrays.asList(m.getOccupied()));
			}
		}
		
		boolean checkPush = true;
		while (checkPush) {
			checkPush = false;
			for (Magnet m: mArray) {
				if (m.getConjoined() == false) {
					Cell[] contained = m.getOccupied();
					for (Cell cell: contained)  {
						if (occupiedList.contains(cell) && (m.getChecked() == false)) {
							m.move(true, dx, dy);
							occupiedList.addAll(Arrays.asList(m.getOccupied()));
							// "m.getOccupied()" is just renewed, so we cannot use "contained".
							m.check();
							checkPush = true;
						}
					}
				}
			}
		}
		
		for (Magnet m: mArray) { m.resetChecked(); }
		
		// Find the new conjoined magnets.
		ArrayList<Cell> northList = new ArrayList<>();
		ArrayList<Cell> southList = new ArrayList<>();
		
		for (Magnet m: mArray) {
			if (m.getConjoined()) {
				northList.add(m.getNorth());
				southList.add(m.getSouth());
			}
		}
		
		for (Magnet m: mArray) {
			if (m.getConjoined() == false) {
				if ((northList.contains(m.getSouth().shift(1,0)))
					|| (northList.contains(m.getSouth().shift(-1,0)))
					|| (northList.contains(m.getSouth().shift(0,1)))
					|| (northList.contains(m.getSouth().shift(0,-1)))
					|| (southList.contains(m.getNorth().shift(1,0)))
					|| (southList.contains(m.getNorth().shift(-1,0)))
					|| (southList.contains(m.getNorth().shift(0,1)))
					|| (southList.contains(m.getNorth().shift(0,-1)))) {
					m.conjoin();
				}
			}
		}
		
		// Reset "keyPressed" effects.
		dx = 0;
		dy = 0;
	}
	
	public void keyPressed (KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT) { dx = -1; }
		if (key == KeyEvent.VK_RIGHT) { dx = 1; }
		if (key == KeyEvent.VK_UP) { dy = -1; }
		if (key == KeyEvent.VK_DOWN) { dy = 1; }
		
		// Ctrl-N for New Game
		if ((key == KeyEvent.VK_N) && e.isControlDown()) {
			sArray = new SolutionArray(Screen.MAGNETNUM);
			iArray = new InitialArray(sArray.getArray());
			mArray = duplication(iArray.getArray());
			mArray[0].conjoin();
		}
		
		// Ctrl-B for Restart
		if ((key == KeyEvent.VK_B) && e.isControlDown()) {			
			mArray = duplication(iArray.getArray());
			mArray[0].conjoin();
		}
		
		// Ctrl-W for Close the Window
		if ((key == KeyEvent.VK_W) && e.isControlDown()) System.exit(0);
    }
    
/*	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_LEFT) { dx = 0; }
		if (key == KeyEvent.VK_RIGHT) { dx = 0; }
		if (key == KeyEvent.VK_UP) { dy = 0; }
		if (key == KeyEvent.VK_DOWN) { dy = 0; }
	}*/
}
