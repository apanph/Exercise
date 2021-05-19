package panx;

import java.util.HashMap;
import java.util.Map;

public class Solution {

	/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	Problem: Find first non-repeating character in a string.
	Example: in: "abracadabra" out: 'c'
	*/
	static char firstNonRepeatingChar(String s) {
		int i = firstNonRepeatingPosition(s);
		return i > -1 ? s.charAt(i) : (char)0;
	}

	static int firstNonRepeatingPosition(String s) {
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

	static void testFirstNonRepeating(String s) {
		System.out.printf("%d %s\t%s%n", firstNonRepeatingPosition(s), firstNonRepeatingChar(s), s);
	}

	/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 
	Problem: Transfer a string to a string made of characters in the initial string followed by numbers of how many times they consequently repeat.
	Examle: in: "qwweeerrrr" out: "q1w2e3r4"
	Examle: in: "qq1" out: "q211"
	 */

	public static String enumerateConsequentRepeatedChars(String s) {
		if (s.length() == 0)
			return s;
		int count = 1;
		char prev = s.charAt(0);
		StringBuilder b = new StringBuilder();
		for (int i = 1; i < s.length(); i++) {
			char curr = s.charAt(i);
			if (prev == curr)
				count++;
			else {
				b.append(prev).append(count);
				prev = curr;
				count = 1;
			}
		}
		return b.append(prev).append(count).toString();
		
	}

	static void testEnumerateConsequentRepeatedChars(String s) {
		System.out.printf("%s\t%s%n", enumerateConsequentRepeatedChars(s), s);
	}

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	given array of students and their marks in different subjects. Find

 

maximum average of the student in the following format below

 

{name, avg}

 

e.g.

{“James”, “70”}

{"Mark", "80"}

{"Bash", "75"}

{"Mark", "60"}

So, here average marks for James, Mark and Bash are 70, 70, 75 respectively. So highest avg is 75 so it should be returned.

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	public static void main(String[] args) {
		testFirstNonRepeating("abracadabra");
		testFirstNonRepeating("");
		testFirstNonRepeating("bee");
		testFirstNonRepeating("eel");
		testFirstNonRepeating("bee");
		System.out.printf("%n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~%n");
		testEnumerateConsequentRepeatedChars("qwweeerrrr");
		testEnumerateConsequentRepeatedChars("");
		testEnumerateConsequentRepeatedChars("qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq");
		testEnumerateConsequentRepeatedChars("qq1");
	}
}
