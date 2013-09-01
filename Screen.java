class Screen {
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;

	public static final int CELLSIZE = 50;

	public static final int XCELLMAX = WIDTH/CELLSIZE;
	public static final int YCELLMAX = HEIGHT/CELLSIZE - 2;
	
	public static final int POLEFONTSIZE = Screen.CELLSIZE*2/3;
	public static final int YOUWINFONTSIZE = Screen.CELLSIZE;
	public static final int HELPFONTSIZE = 15;
	
	public static final int FIXBOXXMIN = WIDTH/CELLSIZE/4 + 1;
	public static final int FIXBOXXMAX = 3*WIDTH/CELLSIZE/4;
	public static final int FIXBOXYMIN = HEIGHT/CELLSIZE/4 + 1;
	public static final int FIXBOXYMAX = 3*HEIGHT/CELLSIZE/4 - 1;
	
	public static final int YOUWINX = WIDTH/2 - 2*Screen.CELLSIZE;
	public static final int YOUWINY = 4*HEIGHT/5;
	
	public static final int MAGNETNUM = 6;
}
