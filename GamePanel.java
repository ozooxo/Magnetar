import java.awt.*;
import java.awt.Point;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.JPanel;
import javax.swing.Timer;

class FixBox {
	FixBox () {}
	
	boolean checkWin (Magnet[] mArray) {
		for (Magnet m: mArray) {
			Cell[] contained = m.getOccupied();
			for (Cell cell: contained)  {
				if (!(cell.checkInBox(Screen.FIXBOXXMIN, Screen.FIXBOXXMAX, Screen.FIXBOXYMIN, Screen.FIXBOXYMAX)))
					return false;
			}
		}
		return true;
	}
	
	void draw (Magnet[] mArray, Graphics2D g2) {
		Point lefttop = new Cell(Screen.FIXBOXXMIN, Screen.FIXBOXYMIN).cellCornersScreen()[0];
		int width = (Screen.FIXBOXXMAX - Screen.FIXBOXXMIN + 1)*Screen.CELLSIZE;
		int height = (Screen.FIXBOXYMAX - Screen.FIXBOXYMIN + 1)*Screen.CELLSIZE;
		
		if (checkWin(mArray)) {
			g2.setColor(Color.GREEN); 
			g2.setFont(new Font("Arial", Font.PLAIN, Screen.YOUWINFONTSIZE));
			g2.drawString("You Win!", Screen.YOUWINX, Screen.YOUWINY);
		}
		else g2.setColor(Color.GRAY);
		
		g2.fillRect(lefttop.x, lefttop.y, width, height);
	}
}

public class GamePanel extends JPanel implements ActionListener {

	private Timer timer;
	MagnetArray mArray = new MagnetArray();
	
	public GamePanel() {
		addKeyListener(new TAdapter());
		setFocusable(true);
		setBackground(Color.WHITE);
		setDoubleBuffered(true);

		timer = new Timer(5, this);
		timer.start();
    }
    
	public void actionPerformed (ActionEvent e) {
		mArray.move();
		repaint();  
	}

    private class TAdapter extends KeyAdapter {
		//public void keyReleased(KeyEvent e) { mArray.keyReleased(e); }
		public void keyPressed(KeyEvent e) {
			// More than one "keyPressed" functions from different classes, can be list in here.
			mArray.keyPressed(e);
		}
	}

	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2 = (Graphics2D) g;
		g2.setBackground(Color.WHITE);
		g2.setColor(Color.BLACK);

		g2.setColor(Color.BLACK);
		g2.setFont(new Font("Arial", Font.PLAIN, Screen.HELPFONTSIZE));

		g2.drawString("Use cursor keys to move the magnets, until everybody is in the gray region", 3, Screen.HEIGHT-55);	
		g2.drawString("Use Ctrl-N to start a new game, Ctrl-B to restart the game, and Ctrl-W to quit the game", 3, Screen.HEIGHT-35);		
		new FixBox().draw(mArray.getArray(), g2);

		mArray.draw(g2);
		
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
	}
}
