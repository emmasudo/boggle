////Emma Sudo

import static org.junit.jupiter.api.Assertions.*;

import java.io.Console;
import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;

class TrieTest {
	Trie test = new Trie();
	Trie dictionary = new Trie();
	Console cons = System.console();
	
	@Test
	void insertTest() {
		assertEquals(test.contains("hello"), false);
		test.insert("hello", 1);
		assertEquals(test.contains("hello"), true);
		test.insert("hi", null);
		assertEquals(test.contains("hi"), false);
		
		String word = "";
		for (int i = 0; i < 500; i++) {
			word = word + (char)('a'+ (int)(26*Math.random()));
			test.insert(word, 0);
			assertEquals(test.contains(word), true);
		}
		
	}
	
	@Test
	void removeTest() {
		assertEquals(test.contains("hello"), false);
		test.insert("hello", 1);
		assertEquals(test.contains("hello"), true);
		test.remove("hello");
		assertEquals(test.contains("hello"), false);
		
		test.insert("hello", 1);
		test.remove("hey");
		assertEquals(test.contains("hello"), true);
		assertEquals(test.contains("hey"), false);
		
		String word = "";
		for (int i = 0; i < 500; i++) {
			word = word + (char)('a'+ (int)(26*Math.random()));
			test.insert(word, 0);
			assertEquals(test.contains(word), true);
			test.remove(word);
			assertEquals(test.contains(word), false);
		}
	}
	
	@Test
	void getValueTest() {
		test.insert("hi", 2);
		assertEquals(test.getValue("hi"), 2);
		
		test.insert("hi", 3);
		assertEquals(test.getValue("hi"), 3);
		
		test.insert("hello", 0);
		assertEquals(test.getValue("hello"), 0);
		
		test.insert("a", -1);
		assertEquals(test.getValue("a"), -1);
		
		for (int i = -500; i < 500; i++) {
			test.insert("hi", i);
			assertEquals(test.getValue("hi"), i);
		}
		
	}
	
	@Test
	void getAllKeysTest() {
		String word = "";
		ArrayList<String> words = new ArrayList<>();
		for (int i = 0; i < 500; i++) {
			word = word + (char)('a'+ (int)(26*Math.random()));
			test.insert(word, 0);
			words.add(word);
		}
		int original = words.size();
		words.retainAll(test.getAllKeys());
		assertEquals(original, words.size());
	}
	
	@Test
	void getAllValuesTest() {
		String word = "";
		ArrayList<Integer> values = new ArrayList<>();
		for (int i = 0; i < 500; i++) {
			word = word + (char)('a'+ (int)(26*Math.random()));
			test.insert(word, i);
			values.add(i);
		}
		int original = values.size();
		values.retainAll(test.getAllValues());
		assertEquals(original, values.size());
		
	}
	
	@Test
	void prefixTest() {
		String word = "abc";
		ArrayList<String> words = new ArrayList<>();
		for (int i = 0; i < 500; i++) {
			word = word + (char)('a'+ (int)(26*Math.random()));
			test.insert(word, 0);
			words.add(word);
		}
		int original = words.size();
		words.retainAll(test.prefix("abc"));
		assertEquals(original, words.size());
	}
	
	@Test
	void hasPrefixTest() {
		assertEquals(test.hasPrefix("hi"), false);
		test.insert("hi", 0);
		assertEquals(test.hasPrefix("h"), true);
		assertEquals(test.hasPrefix("hi"), true);
		assertEquals(test.hasPrefix("hii"), false);
		assertEquals(test.hasPrefix(""), true);
	}
	
	@Test
	void containsTest() {
		assertEquals(test.contains("hello"), false);
		test.insert("hello", 1);
		assertEquals(test.contains("hello"), true);
		
		assertEquals(test.contains("1"), false);
		assertEquals(test.contains("Hi"), false);
		
		//tested in insertTest
	}
	
	@Test
	void txtToTrieTest() throws FileNotFoundException {
		assertThrows(FileNotFoundException.class, () ->{
			test.txtToTrie("hello");
		});
		
		test.txtToTrie("bogwords.txt");
		assertEquals(test.contains("aback"), true);
		assertEquals(test.contains("abacus"), true);
		assertEquals(test.contains("electorate"), true);
		assertEquals(test.contains("encore"), true);
		assertEquals(test.contains("ratiocinate"), true);
		assertEquals(test.contains("rattlesnake"), true);
		assertEquals(test.contains("zirconium"), true);
		assertEquals(test.contains("zygote"), true);
		assertEquals(test.contains("abcdefg"), false);
		
	}
}
