import java.util.*;

class InitialArray {
	private Magnet[] mArray; 
	
	InitialArray (Magnet[] sArray) {
		mArray = new Magnet[sArray.length];
		
		Random rand = new Random();
		ArrayList<Cell> occupiedList = new ArrayList<>();
		for (int i = 0; i < sArray.length; ++i) {
			// Randomly move the position of the magnet.
			Magnet m = sArray[i];
			if (m.getNorth().x == m.getSouth().x) {
				m = m.shift(1 + rand.nextInt(Screen.XCELLMAX-1) - m.getNorth().x,
							1 + rand.nextInt(Screen.YCELLMAX-1-Math.abs(m.getNorth().y-m.getSouth().y)) - Math.min(m.getNorth().y, m.getSouth().y));
			}
			else {
				m = m.shift(1 + rand.nextInt(Screen.XCELLMAX-1-Math.abs(m.getNorth().x-m.getSouth().x)) - Math.min(m.getNorth().x, m.getSouth().x), 
							1 + rand.nextInt(Screen.YCELLMAX-1) - m.getNorth().y);
			}
			
			// Check whether the new position overlap with the other magnets or not.
			boolean successfulMagnet = true;
			Cell[] contained = m.getOccupied();
			for (Cell cell: contained) {
				if (occupiedList.contains(cell)) {
					successfulMagnet = false;
					--i;
					break;
				}
			}
			if (successfulMagnet) {
				mArray[i] = m;
				occupiedList.addAll(Arrays.asList(m.getOccupied()));					
			}
		}
	}
	
	Magnet[] getArray () { return mArray; }
}
