//Emma Sudo

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

public class Trie {
	
    private class Node{
    	//represents the points in boggle
        private Integer value = null;
        private Node[] alphabet = new Node[26];
    }

    private Node root = new Node();
    
    //sets the value of the node to the number of points the key earns
    public void insert(String key, Integer value){
        findEnd(key).value = value;
    }
    
    //removes the value of the node that ends the key
    public void remove(String key){
        findEnd(key).value = null;
    }
    
    //finds the last node of a word/key
    private Node findEnd(String key){
        Node end = root;

        for (int i = 0; i < key.length(); i++){
            if (end.alphabet[key.charAt(i)-'a'] == null){
                end.alphabet[key.charAt(i)-'a'] = new Node();
            }
            end = end.alphabet[key.charAt(i)-'a'];
        }

        return end;
    }
    
    //returns the value corresponding to the key
    public int getValue(String key) {
    	return findEnd(key).value;
    }
    
    //returns ArrayList of all the keys in a trie
    public ArrayList<String> getAllKeys(){
    	ArrayList<String> keys = new ArrayList<String>();
   
        return getAllKeys(root, "", keys);
    }
    
    //helper method for getAllKeys
    private ArrayList<String> getAllKeys(Node node, String word, ArrayList<String> keys){
    	
    	//if the node exists and has a value then it is the end of a key
        if( node != null && node.value != null){
        	keys.add(word);
        }
        
        //check all other letters of the alphabet
        for( int i = 0; i < 26; i++){
            if(node != null && node.alphabet[i] != null) {
                getAllKeys(node.alphabet[i], word + (char) ('a' + i), keys);
            }
        }
        
        return keys;
    }
    
    //returns ArrayList of all the values in a trie
    public ArrayList<Integer> getAllValues(){
    	ArrayList<Integer> values = new ArrayList<Integer>();
    	
        return getAllValues(root, values);
    }
    
    //helper method for getAllValues
    private ArrayList<Integer> getAllValues(Node n, ArrayList<Integer> values){
    	
    	//If the node exists and has a value then add it to the list
    	if(n != null && n.value != null){
            values.add(n.value);
        }
    	
    	//check all other letters
        for( int i = 0; i < 26; i++){
            if(n != null && n.alphabet[i] != null) {
                getAllValues(n.alphabet[i], values);
            }
        }
        return values;
    }
    
    //returns list of keys that start with the prefix
    public ArrayList<String> prefix(String prefix) {
    	ArrayList<String> keysWithPrefix = new ArrayList<String>();
    	
    	//getAllKeys can be used because it takes in a node which can be the end of the prefix
    	return getAllKeys(findEnd(prefix), prefix, keysWithPrefix); 
    }
    
    //returns true if there are keys with the prefix and false if not
    public boolean hasPrefix(String prefix) {
    	if(prefix(prefix).size() == 0) {
    		return false;
    	}
    	else {
    		return true;
    	}
    }
    
    //returns true if the trie contains a key that matches and false if not
    public boolean contains(String key){
        Node cur = root;
        for (int i = 0; i < key.length(); i++){
        	
        	//to make sure that the correct alphabet is used and that the node exists
            if ((key.charAt(i)-'a') <0 || (key.charAt(i)-'a') >= 26||cur.alphabet[key.charAt(i)-'a'] == null) {
                return false;
            }
            cur = cur.alphabet[key.charAt(i)-'a'];
        }
        
        if (cur.value != null){
            return true;
        }

        return false;
    }
    
    //converts a txt file with words into a trie dictionary
    public void txtToTrie(String filename) throws FileNotFoundException {
        File file = new File(filename);
        Scanner scan = new Scanner(file);

        while(scan.hasNextLine()){
        	String temp = scan.nextLine();
            insert(temp, 0);
        }
    }
    
    
}
