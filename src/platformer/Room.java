package platformer;

public class Room {
	public static final int	UPPER				= 1237;
	public static final int	MIDDLE_TOP	= 3250;
	public static final int	MIDDLE_BTM	= 3270;
	public static final int	BOTTOM			= 3466;

	public static final int	LEFT				= 1231;
	public static final int	CENTER			= 3254;
	public static final int	RIGHT				= 3463;

	public char[][]					room;
	public int							type;
	int											level;
	int											alignment;
	boolean									entrance		= false;
	boolean									exit				= false;
	int											x						= 0;
	int											y 					= 0;

	public Room(int level, int alignment, boolean entrance, boolean exit, int x, int y) {
		this.level = level;
		this.x = x * 50*10;
		this.y = y * 50*8;

		if(level == UPPER)
			generateTop(entrance, alignment);
		if(level == MIDDLE_TOP)
			generateMiddle(alignment);
		if(level == MIDDLE_BTM)
			generateMiddle(alignment);
		if(level == BOTTOM)
			generateBottom(exit, alignment);
	}

	public void generateTop(boolean entrance, int alignment) {
		char e = (entrance) ? '@' : ' ';

		if(alignment == LEFT) {
			room = new char[][] {
				{ '#', '#', '#', '#', '#', '#', '#', '#', '#', '#' },
				{ '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ '#', ' ', ' ', ' ',  e , ' ', ' ', ' ', ' ', ' ' },
				{ '#', ' ', ' ', '#', '#', '#', '#', ' ', ' ', ' ' },
				{ '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ '#', ' ', '#', '#', '#', '#', '#', '#', '#', '#' }
			};
		}
		if(alignment == CENTER) {
			room = new char[][] {
				{ '#', '#', '#', '#', '#', '#', '#', '#', '#', '#' },
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', ' ',  e , ' ', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', '#', '#', '#', '#', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ '#', '#', '#', '#', '#', '#', '#', '#', '#', '#' }
			};
		}
		if(alignment == RIGHT) {
			room = new char[][] {
				{ '#', '#', '#', '#', '#', '#', '#', '#', '#', '#' },
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#' },
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#' },
				{ ' ', ' ', ' ', ' ',  e , ' ', ' ', ' ', ' ', '#' },
				{ ' ', ' ', ' ', '#', '#', '#', '#', ' ', ' ', '#' },
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#' },
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#' },
				{ '#', '#', '#', '#', '#', '#', '#', '#', ' ', '#' }
			};
		}
	}

	public void generateMiddle(int alignment) {
		if(alignment == LEFT) {
			room = new char[][] {
				{ '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ '#', ' ', ' ', '#', '#', '#', '#', ' ', ' ', ' ' },
				{ '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ '#', ' ', '#', '#', '#', '#', '#', '#', '#', '#' }
			};
		}
		if(alignment == CENTER) {
			room = new char[][] {
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', '#', '#', '#', '#', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ '#', '#', '#', '#', '#', '#', '#', '#', '#', '#' }
			};
		}
		if(alignment == RIGHT) {
			room = new char[][] {
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#' },
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#' },
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#' },
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#' },
				{ ' ', ' ', ' ', '#', '#', '#', '#', ' ', ' ', '#' },
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#' },
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#' },
				{ '#', '#', '#', '#', '#', '#', '#', '#', ' ', '#' }
			};
		}
	}

	public void generateBottom(boolean exit, int alignment) {
		char e = (exit) ? '%' : ' ';

		if(alignment == LEFT) {
			room = new char[][] { 
				{ '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ '#', ' ', ' ', ' ',  e , ' ', ' ', ' ', ' ', ' ' },
				{ '#', ' ', ' ', '#', '#', '#', '#', ' ', ' ', ' ' },
				{ '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ '#', '#', '#', '#', '#', '#', '#', '#', '#', '#' }
			};
		}
		if(alignment == CENTER) {
			room = new char[][] {
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', ' ',  e , ' ', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', '#', '#', '#', '#', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ '#', '#', '#', '#', '#', '#', '#', '#', '#', '#' }
			};
		}
		if(alignment == RIGHT) {
			room = new char[][] {
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#' },
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#' },
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#' },
				{ ' ', ' ', ' ', ' ',  e , ' ', ' ', ' ', ' ', '#' },
				{ ' ', ' ', ' ', '#', '#', '#', '#', ' ', ' ', '#' },
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#' },
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#' },
				{ '#', '#', '#', '#', '#', '#', '#', '#', '#', '#' }
			};
		}
	}
}
