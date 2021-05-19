package panx;

import java.util.HashMap;
import java.util.Iterator;
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

	/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 
	
	Problem: Given an array of name of students and their marks. Find maximum average mark of the students who have such a max mark.
	
	Example:
	
	Name	Mark
	"James"	70
	"Mark"	80
	"Bash"	75
	"Mark"	60
	
	Here average marks for James, Mark and Bash are 70, 70, 75 respectively. So the highest average mark is 75.
	*/

	static class Pair<T1, T2> {
		T1 v1;
		T2 v2;
	}

	static class StudentMark extends Pair<String, Integer> {

		public StudentMark(String name, int mark) {
			v1 = name;
			v2 = mark;
		}

		String getName() {
			return v1;
		}

		Integer getMark() {
			return v2;
		}
	}

	static int findMaxAvg(StudentMark[] studentMarks) {

		class MarksSumAndCount extends Pair<Integer, Integer> {

			MarksSumAndCount(int firstMark) {
				v1 = firstMark;
				v2 = 1;
			}

			void add(int mark) {
				v1 += mark;
				v2++;
			}

			Integer getAvg() {
				return v1 / v2;
			}
		}

		Map<String, MarksSumAndCount> m = new HashMap<>(); // Name -> marks sum, marks count
		for (StudentMark sm : studentMarks) {
			String name = sm.getName();
			Integer mark = sm.getMark();
			MarksSumAndCount msc = m.get(name);
			if (msc == null)
				m.put(name, new MarksSumAndCount(mark));
			else
				msc.add(mark);
		}
		int r = 0;
		for (MarksSumAndCount msc : m.values()) {
			int avg = msc.getAvg();
			if (r < avg)
				r = avg;
		}
		return r;
	}

	static void testFindMaxAvg() {
		StudentMark[] a = new StudentMark[]{
			new StudentMark("James", 70),
			new StudentMark("Mark", 80),
			new StudentMark("Bash", 75),
			new StudentMark("Mark", 60)};
		System.out.println(findMaxAvg(a));
	}

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
		System.out.printf("%n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~%n");
		testFindMaxAvg();
	}
}
