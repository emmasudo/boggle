//Emma Sudo

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.ArrayList;
import java.util.Arrays;

public class Boggle {

    Trie dictionary = new Trie();
    BoggleGrid grid = new BoggleGrid();
    int score = 0;
    
    public Boggle() {
    	try {
    		dictionary.txtToTrie("bogwords.txt");
            grid.printGrid();
		} catch (FileNotFoundException e) {
			System.out.println("No dictionary file was found. bogwords.txt was created, please enter words in this file to play.");
			File file = new File("bogwords.txt");
			try {
				file.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
    }

    public void playBoggle() {
    	//Trie that will contain the words that can be played in this specific boggle, but have not yet been played
    	Trie notPlayed = getAllWords();
    	
    	//ArrayList of words that have already been played
    	ArrayList<String> played = new ArrayList<String>();
    	
    	Scanner s = new Scanner(System.in);
    	
    	System.out.println("Play with timer? [Y]es or [N]o ");
    	String answer = s.next();
    	
    	if (answer.compareTo("Y") == 0) {
    		
    		System.out.println("For how many seconds: ");
        	int seconds = s.nextInt();
        	Timer timer = new Timer();
        	
        	timer.scheduleAtFixedRate(new TimerTask() {
        		int i = 10;
        		public void run() {
        			i--;
                    if (i < 0) {
                        
                        System.out.println("times up :(");
                        System.exit(i);
                        timer.cancel();
                    }
        		}
        	}, 0, 1000);
        	
        	//starts letting the player play the game
        	displayOptions(notPlayed, played);
        	
    	}
    	
    	else if (answer.compareTo("N") == 0) {
    		//starts game
    		displayOptions(notPlayed, played);
    	}
    	
    	else {
    		System.out.println("Try again!");
    		playBoggle();
    	}
    }
    
    private void displayOptions(Trie notPlayed, ArrayList<String> played) {
    	Scanner s = new Scanner(System.in);
    	System.out.println("[P]lay another word, [H]int, [W]hich words have I played?, [S]core, [R]eveal all words, [A]dd word to dictionary, [B]oggle Board, [E]xit");
    	String answer = s.next();
    	
    	switch(answer) {
    	case "P": 
    		play(notPlayed, played);
    		displayOptions(notPlayed, played);
    	
    	case "H":
    		hint(notPlayed);
    		displayOptions(notPlayed, played);
    	
    	case "W":
    		System.out.println(played);
    		displayOptions(notPlayed, played);
    		
    	case "S":
    		System.out.println("This is your score: " + score);
    		displayOptions(notPlayed, played);
    		
    	case "R":
    		System.out.println(getAllWords().getAllKeys());
    		displayOptions(notPlayed, played);
    	
    	case "A":
    		addToDictionary(notPlayed);
    		System.out.println("Done!");
    		displayOptions(notPlayed, played);
    	
    	case "B":
    		grid.printGrid();
    		displayOptions(notPlayed, played);
    		
    	case "E":
    		System.out.println("Thank you for playing!");
    		System.exit(0);
    
    	default:
    		System.out.println("Please try again...");
    		displayOptions(notPlayed, played);
    	}
    	
    	
    }
    
    //asks player for a word in the boggle and then adds score if word is legal
    private void play(Trie notPlayed, ArrayList<String> played) {
    	
    	Scanner s = new Scanner(System.in);
    	System.out.println("Enter word: ");
		String word = s.next();
		
		if(validWord(word, notPlayed)) {
			
			score+=getAllWords().getValue(word);
			
			//marks word from notPlayed to played 
        	notPlayed.remove(word);
        	played.add(word);
        	
        	System.out.println("That's a word!");
        	System.out.println("Your score: " + score);
        }

		//word is in the dictionary and boggle but has already been played
        else if(played.contains(word)){
        	System.out.println("You already played that word!");
        }
		
		//word is either not in the dictionary, boggle, or both
        else {
        	System.out.println("Try again...");
        }
    }
    
    //prints a random word in the boggle that has not been played
    private void hint(Trie notPlayed) {
    	ArrayList<String> list = notPlayed.getAllKeys();
    	String hint = list.get((int) (Math.random()*(notPlayed.getAllKeys().size())));
    	System.out.println("Try this word: " + hint);
    }
    
    //allows players to add their own words to the boggle dictionary
    private void addToDictionary(Trie notPlayed) {
    	
    	Scanner s = new Scanner(System.in);
    	System.out.println("Enter word: ");
		String word = s.next();
		
		dictionary.insert(word, 0);
		notPlayed.insert(word, points(word));
    }
    
    //returns a Trie with all of the words in the boggle
    private Trie getAllWords(){
    	Trie words = new Trie();
        
        boolean[][] visited = new boolean[4][4];
        
        for (int r = 0; r<4; r++) {
        	for(int c = 0; c<4; c++) {
        		search(r,c,"",visited,words);
        	}
        }
        
        return words;
    }
    
    //helper method for getAllWords
    //performs depth first search
    private void search(int row, int col, String restOfWord, boolean[][] visited, Trie words){
    	//set the current square to visited
    	visited[row][col] = true;
    	
    	//find the word formed by the current path
    	String currentWord = restOfWord.concat(grid.getSquare(row, col));
    	
    	//if the word is in the dictionary then insert into trie
        if(dictionary.contains(currentWord)){
           words.insert(currentWord, points(currentWord));
           //words.add(currentWord);
        }
        
        //if the word has no more possible paths to create more words then backtrack
        if(!dictionary.hasPrefix(currentWord)) {
        	visited[row][col] = false;
        	return;
        }
        
        //list of neighbors on the grid
        ArrayList<Coordinate> neighbors = grid.getLegalNeighbors(row, col);
        
        if(!neighbors.isEmpty()){
            for(int i = 0; i < neighbors.size(); i++){
            	Coordinate k = (Coordinate) neighbors.get(i);
                int r = k.getRow();
                int c = k.getCol();
                
                //if neighbor is unvisited then perform depth first search on it
                if(visited[r][c]==false) {
                	search(r, c, currentWord, visited, words);
                }
            }
        }
        
        //set current square to unvisited again
        visited[row][col] = false;
    }
    
    //returns true if the word is legal or exists in both the dictionary and boggle and has not yet been played
    private boolean validWord(String word, Trie notPlayed) {
    	return (notPlayed.contains(word));
    }
    
    //calculates points of a word
    private int points(String key) {
    	int points = 0;
    	
    	for(int i = 0; i < key.length(); i++) {
    		char c = key.charAt(i);
    		
    		//one
    		if (Arrays.asList('a','e','i','o','u','l','n','s','t','r').contains(c)) {
    			points++;
    		}
    		
    		//two
    		if (Arrays.asList('d','g').contains(c)) {
    			points+=2;
    		}
    		
    		//three
    		if (Arrays.asList('b','c','m','p').contains(c)) {
    			points+=3;
    		}
    		
    		//four
    		if (Arrays.asList('f','h','v','w','y').contains(c)) {
    			points+=4;
    		}
    		
    		//five
    		if (Arrays.asList('k').contains(c)) {
    			points+=5;
    		}
    		
    		//eight
    		if (Arrays.asList('j','x').contains(c)) {
    			points+=8;
    		}
    		
    		//ten
    		if (Arrays.asList('q','z').contains(c)) {
    			points+=10;
    		}
    	}
    	
    	int length = key.length();
    	
    	//multiplier bonus
    	if(length>=5) {
    		points = points*(length-3);
    	}
    	
    	return points;
    }
    
    public static void main(String[] args) {
		Boggle boggle = new Boggle();
		boggle.playBoggle();
    }
}