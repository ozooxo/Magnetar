import java.util.*;

class SolutionArray {
	private Magnet[] mArray; 
	
	Cell anotherEnd (Cell end1) {
		int end1x = end1.x;
		int end1y = end1.y;
		int end2x, end2y;
		
		Random rand = new Random();
		int hv = rand.nextInt(2);
		if (hv == 0) {
			end2x = end1x;
			if ((Screen.FIXBOXYMAX-end1y) >= (end1y-Screen.FIXBOXYMIN))
				end2y = end1y + 1 + rand.nextInt(Screen.FIXBOXYMAX-end1y);
			else end2y = end1y - 1 - rand.nextInt(end1y-Screen.FIXBOXYMIN);
		}
		else {
			end2y = end1y;
			if ((Screen.FIXBOXXMAX-end1x) >= (end1x-Screen.FIXBOXXMIN))
				end2x = end1x + 1 + rand.nextInt(Screen.FIXBOXXMAX-end1x);
			else end2x = end1x - 1 - rand.nextInt(end1x-Screen.FIXBOXXMIN);				
		}
		Cell end2 = new Cell(end2x, end2y);
		return end2;
	}
	
	SolutionArray (int n) {
		mArray = new Magnet[n];
		
		Random rand = new Random();
		ArrayList<Cell> northList = new ArrayList<>();
		ArrayList<Cell> southList = new ArrayList<>();
		ArrayList<Cell> occupiedList = new ArrayList<>();
		for (int i = 0; i < n; ++i) {
			int end1x, end1y, end2x, end2y;
			Cell end1, end2;
			
			// To generate the first random magnet.
			if (i == 0) {
				end1x = Screen.FIXBOXXMIN + rand.nextInt(Screen.FIXBOXXMAX-Screen.FIXBOXXMIN+1);
				end1y = Screen.FIXBOXYMIN + rand.nextInt(Screen.FIXBOXYMAX-Screen.FIXBOXYMIN+1);
				end1 = new Cell(end1x, end1y);
				
				end2 = anotherEnd(end1);
				
				int sn = rand.nextInt(2);
				if (sn == 0) {
					northList.add(end1);
					southList.add(end2);
					mArray[i] = new Magnet(end1, end2);
				}
				else {
					northList.add(end2);
					southList.add(end1);
					mArray[i] = new Magnet(end2, end1);
				}
				occupiedList.addAll(Arrays.asList(mArray[i].getOccupied()));
			}
			
			// To generate all other random magnets.
			else {
				// Either the north pole or the south pole need to be conjoined with some other magnet.
				Magnet magnet;
				int sn = rand.nextInt(2);
				int snnum = rand.nextInt(northList.size());
				if (sn == 0) {
					end1 = northList.get(snnum).randConjoined();
					end2 = anotherEnd(end1);
					magnet = new Magnet(end2, end1);
				}
				else {
					end1 = southList.get(snnum).randConjoined();
					end2 = anotherEnd(end1);
					magnet = new Magnet(end1, end2);
				}
				
				// Check whether the new position overlap with the other magnets or not.
				boolean successfulMagnet = true;
				Cell[] contained = magnet.getOccupied();
				for (Cell cell: contained) {
					if (!(cell.checkInBox(Screen.FIXBOXXMIN, Screen.FIXBOXXMAX, Screen.FIXBOXYMIN, Screen.FIXBOXYMAX)) ||
						occupiedList.contains(cell)) {
						successfulMagnet = false;
						--i;
						break;
					}
				}
				if (successfulMagnet) {
					mArray[i] = magnet;
					northList.add(magnet.getNorth());
					southList.add(magnet.getSouth());
					occupiedList.addAll(Arrays.asList(magnet.getOccupied()));					
				}
			}
		}
		
		//System.out.println(rand.nextInt(2)); // 0 and 1
		/*
		mArray = new Magnet[4];
		
		Cell p = new Cell (1, 1);
		Cell q = new Cell (1, 5);		
		mArray[0] = new Magnet (p, q);

		Cell r = new Cell (6, 5);
		Cell s = new Cell (10, 5);		
		mArray[1] = new Magnet (r, s);

		Cell u = new Cell (12, 7);
		Cell v = new Cell (12, 4);		
		mArray[2] = new Magnet (u, v);
			
		Cell m = new Cell (9, 2);
		Cell n = new Cell (6, 2);		
		mArray[3] = new Magnet (m, n);
		*/
	}
	
	Magnet[] getArray () { return mArray; }
}
