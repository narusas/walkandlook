public class SpiralArray {

	int remains;
	int now = 1;
	int x = 0;
	int y = 0;

	int bearing = 1;// 1=right, 2=right, 3=up, 4=down

	int width;
	int height;
	private int[][] map;
	private int position;

	public static void main(String[] args) {
		new SpiralArray().execute(new String[] { "1", "0", "1" });
	}

	// //////////////////////////////////////////////////
	// depth 0
	// //////////////////////////////////////////////////

	public void execute(String[] args) {
		init(args);
		walk();
		// print();
	}

// //////////////////////////////////////////////////
	// Initialization
	// //////////////////////////////////////////////////

	private void init(String[] args) {
		int[] values = input(args);
		width = values[0];
		height = values[1];
		position = values[2];

		initPosition();

		remains = width * height;
		map = initMap();
	}

	/**
	 * depth 3 이지만 응집성을 위해 남겨둠
	 */
	private void initPosition() {
		switch (position) {
		case 1: // 0,0
			x = 0;
			y = 0;
			bearing = 1;
			break;
		case 2: // width-1,0
			x = width - 1;
			y = 0;
			bearing = 4;
			break;
		case 3: // width-1, height-1
			x = width - 1;
			y = height - 1;
			bearing = 2;
			break;
		case 4: // 0, height-1
			x = 0;
			y = height - 1;
			bearing = 3;
			break;
		}
	}

	private int[][] initMap() {
		int[][] map = new int[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				map[i][j] = 0;
			}
		}
		return map;
	}

	// //////////////////////////////////////////////////
	// User Input
	// //////////////////////////////////////////////////

	private int[] input(String[] args) {
		return new int[] { Integer.parseInt(args[0]),
				Integer.parseInt(args[1]), Integer.parseInt(args[2]) };
	}

	public String readLine() {
		return "";
	}

	// //////////////////////////////////////////////////
	// Processing walk
	// //////////////////////////////////////////////////

	private void walk() {
		while (remains > 0) {
			readLine();
			walkStep();
			new MapView(map, width, height).print();
		}
	}

	private void walkStep() {
		mark();
		if (canGo() == false) {
			turn();
		}

		gotoNext();
		timeFlow();
	}

	// //////////////////////////////////////////////////
	// Procession: Action & Condition
	// //////////////////////////////////////////////////

	private boolean canGo() {

		switch (bearing) {
		case 1:// right
			if (x + 1 >= width || map[x + 1][y] != 0) {
				return false;
			}
			break;
		case 2:// left
			if (x - 1 < 0 || map[x - 1][y] != 0) {
				return false;
			}
			break;
		case 3:// up
			if (y - 1 < 0 || map[x][y - 1] != 0) {
				return false;
			}
			break;
		case 4:// down
			if (y + 1 >= height || map[x][y + 1] != 0) {
				return false;
			}
		}
		return true;
	}

	private void turn() {
		switch (bearing) {
		case 1:
			bearing = 4;
			break;
		case 2:
			bearing = 3;
			break;
		case 3:
			bearing = 1;
			break;
		case 4:
			bearing = 2;
			break;
		}
	}

	private void mark() {
		map[x][y] = now;
	}

	private void gotoNext() {
		switch (bearing) {
		case 1:// right
			x++;
			break;
		case 2:// left
			x--;
			break;
		case 3:// up
			y--;
			break;
		case 4:// down
			y++;
			break;
		}
	}

	private void timeFlow() {
		now++;
		remains--;
	}
	// util

}

class MapView {
	private final int[][] map;
	private int maxCellWidth;
	private int width;
	private int height;

	public MapView(int[][] map, int width, int height) {
		super();
		this.map = map;
		this.maxCellWidth = maxCellWidth;
		this.width = width;
		this.height = height;
	}

	void print() {
		maxCellWidth = String.valueOf(width * height).length();
		for (int y = 0; y < height; y++) {
			printLine();
			printValues(y);
		}
		printLine();

	}

	private void printValues(int row) {
		for (int x = 0; x < width; x++) {
			printPartition();
			printValue(row, x);
		}
		printPartition();
		System.out.println();
	}

	private void printValue(int row, int col) {
		if (map[col][row] == 0)
			printBlank();
		else
			printNumber(row, col);
	}

	private void printNumber(int row, int col) {
		System.out.printf("%" + maxCellWidth + "d", map[col][row]);
	}

	private void printBlank() {
		for (int i = 0; i < maxCellWidth; i++)
			System.out.print(" ");
	}

	private void printPartition() {
		System.out.print("|");
	}

	private void printLine() {
		// TODO Auto-generated method stub
		for (int x = 0; x < width; x++) {
			printCross();
			printBar();
		}
		printCross();

		System.out.println();
	}

	private void printBar() {
		for (int i = 0; i < maxCellWidth; i++)
			System.out.print("-");
	}

	private void printCross() {
		System.out.print("+");
	}

}