//Emma Sudo

import java.util.ArrayList;

public class BoggleGrid {
	
	private final char[][] DICE = 
					{   {'R','I','F','O','B','X'},
						{'I','F','E','H','E','Y'},
						{'D','E','N','O','W','S'},
						{'U','T','O','K','N','D'},
						{'H','M','S','R','A','O'},
						{'L','U','P','E','T','S'},
						{'A','C','I','T','O','A'},
						{'Y','L','G','K','U','E'},
						{'Q','B','M','J','O','A'}, //Q represents Qu
						{'E','H','I','S','P','N'},
						{'V','E','T','I','G','N'},
						{'B','A','L','I','Y','T'},
						{'E','Z','A','V','N','D'},
						{'R','A','L','E','S','C'},
						{'U','W','I','L','R','G'},
						{'P','A','C','E','M','D'},
					};
	
	
	char[][] board;
	
	public BoggleGrid() {
		board = new char[4][4];
		randomize();
	}
	
	
	public void randomize() {
		ArrayList<Character> c = new ArrayList<Character>(16);
		//picks a random face of a die and adds it to a random spot in the list
		for(int i = 0; i < DICE.length; i++) {
			c.add((int) (Math.random()*c.size()), DICE[i][(int)(Math.random()*6)]);
		}
		
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				board[i][j] = c.remove(0);
			}
		}
	}

	public String getSquare(int row, int col) {
		//convert to String
		String c =  String.valueOf(board[row][col]);
		
		//convert Q to qu
		if (c.compareTo("Q") == 0) {
			return ("qu");
		}
		else {
			return c.toLowerCase();
		}
	}
	
	public void printGrid() {
		for(char[] space : board) {
			System.out.print("[ ");
			for(char face : space) {
				System.out.print(face + " ");
				
			}
			System.out.println("]");
		}
	}
	
	//returns list of coordinates that represent the neighbors of a square
	//coordinates data structure in a public class
	public ArrayList<Coordinate> getLegalNeighbors(int row, int col){
		ArrayList<Coordinate> neighbors = new ArrayList<Coordinate>();
		
		//checks if the square is on an edge, corner, or in the middle to determine the coordinates of the neighbors
		for(int x = Math.max(0, row-1); x <= Math.min(row+1, 3); x++){
			for(int y = Math.max(0, col-1); y <= Math.min(col+1, 3); y++){
				if(x != row || y != col){
					neighbors.add(new Coordinate(x,y));
				}
			}
		}
		
		return neighbors;
	}
	
	public static void main( String[] args) {
		BoggleGrid b = new BoggleGrid();
		b.printGrid();
	}
	
}