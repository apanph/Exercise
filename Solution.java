package panx;

import java.util.HashMap;
import java.util.Map;

public class Solution {

	static int firstNonRepeatingPos(String s) {
		Map<Character, int[]> m = new HashMap<>(); // array of dimension 2 contains index and number of repetition of the character in string
		for (int i = 0; i < s.length(); i++)
			if (m.containsKey(s.charAt(i)))
				m.get(s.charAt(i))[1]++; // increment count
			else
				m.put(s.charAt(i), new int[]{i,1}); // add new entry with position 1 and count 1

		int r = Integer.MAX_VALUE;
		// string can be long and map usually short, so instead of traversing over it and getting number of repetitions by looking up char in Map<Character, Integer>, it's better to traverse over the collection of characters that also holds the position of char
		for (Map.Entry<Character, int[]> e : m.entrySet()) {
			int i = e.getValue()[0];
			if (e.getValue()[1] == 1 && i < r)
				r = i; // take minimal index
		}
		return r < Integer.MAX_VALUE ? r : -1;
	}

	static char firstNonRepeatingChar(String s) {
		int i = firstNonRepeatingPos(s);
		return i > -1 ? s.charAt(i) : (char)0;
	}

	private static void test(String s) {
		System.out.printf("%d %s%n", firstNonRepeatingPos(s), firstNonRepeatingChar(s));
	}

	public static void main(String[] args) {
		test("abracadabra");
		test("");
		test("bee");
		test("eel");
		test("bee");
	}
}
