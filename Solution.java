package panx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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


/*
leetcode.com/problems/longest-palindromic-substring
5. Longest Palindromic Substring
Also My solution: All Longest Palindromic Substrings
Brute forse solutions.
Time complexity: O(n^3).
Space complexity: O(1).
*/
	public static List<String> longestPalindromes(String s) {
		List<String> r = new ArrayList<>();
		findLongestPalindromes(s, r, 0);
		return r;
	}

	private static void findLongestPalindromes(String s, List<String> r, int d) { // Recursive.
		//System.out.println(d);
		boolean f = false; // found
		for (int i = 0; i <= d; i++) {
			String p = s.substring(i, s.length() - d + i); // portion
			//System.out.println(i + " " + p + " " + isP(p));
			if (isPalindrome(p)) {
				r.add(p);
				// Don't break the loop here. Just raise the flag and check all substrings in s that are shorter than s by decrement d.
				f = true;
			}
		}
		if (!f) {
			findLongestPalindromes(s, r, d + 1);
		} else
			return; // All other substrings will be shorter; no need to further check. Breke recursion.
	}

/*
Success
Details 
Runtime: 2067 ms, faster than 5.01% of Java online submissions for Longest Palindromic Substring.
Memory Usage: 541.7 MB, less than 5.00% of Java online submissions for Longest Palindromic Substring.	
*/
	public static String longestPalindrome(String s) {
		return findLongestPalindrome(s, 0);
	}

	private static String findLongestPalindrome(String s, int d) { // Recursive.
		for (int i = 0; i <= d; i++) {
			String p = s.substring(i, s.length() - d + i); // portion
			if (isPalindrome(p))
				return p;
		}
		return findLongestPalindrome(s, d + 1);
	}

	private static boolean isPalindrome(String s) {
		int n = s.length();
		for (int i = 0; i < n / 2; i++)
			if (s.charAt(i) != s.charAt(n - i - 1))
				return false;
		return true;
	}

/*
Expand Around Center approach. For disguising I offer spreadWhilePalindrome instead expandAroundCenter aux method name.
Success
Details 
Runtime: 23 ms, faster than 90.63% of Java online submissions for Longest Palindromic Substring.
Memory Usage: 38.8 MB, less than 90.44% of Java online submissions for Longest Palindromic Substring. 
*/
	public static String longestPalindromeEac(String s) {
		int b = 0; // bebeginning of longest palindrome
		int e = -1; // end ~~. -1 for empty string
		for (int i = 0; i < s.length(); i++) {
			int q = Math.max(spreadWhilePalindrome(s, i - 1, i + 1), spreadWhilePalindrome(s, i, i + 1));
			if (q > e - b + 1) {
				b = i - (q - 1) / 2;
				e = i + q / 2;
			}
		}
		return s.substring(b, e + 1);
	}

/*
5. Longest Palindromic Substring
leetcode.com/problems/longest-palindromic-substring

This is my improvement. This method starts serching for palindroms from the middle of the string, and not from the beginning; it is more effective.

Success
Details 
Runtime: 24 ms, faster than 80.08% of Java online submissions for Longest Palindromic Substring.
Memory Usage: 39.1 MB, less than 76.85% of Java online submissions for Longest Palindromic Substring.
*/
	public static String longestPalindromeSwp(String s) {
		int b = 0; // bebeginning of longest palindrome
		int e = 0; // end ..
		int n = s.length();
		int l = n / 2; // left
		int r = l + 1; // right
		int i;
		boolean t = false;
		while (l >= 0 && r <= n) {
			i = (t = !t) ? l-- : r++;
			if (i >= n)
				i = 0;
			else if (i < 0)
				i = n - 1;
			int q = Math.max(spreadWhilePalindrome(s, i - 1, i + 1), spreadWhilePalindrome(s, i, i + 1));
			if (q > e - b + 1) {
				b = i - (q - 1) / 2;
				e = i + q / 2;
			}
		}
		return n > 1 ? s.substring(b, e + 1) : s;
	}

	public static List<String> longestPalindromesSwp(String s) {
		int b = 0; // bebeginning of longest palindrome
		int e = 0; // end ..
		int n = s.length();
		int l = n / 2; // left
		int r = l + 1; // right
		int i;
		boolean t = false;
		List<String> a = new ArrayList<>();
		while (l >= 0 && r <= n) {
			i = (t = !t) ? l-- : r++;
			if (i >= n)
				i = 0;
			else if (i < 0)
				i = n - 1;
			int q = Math.max(spreadWhilePalindrome(s, i - 1, i + 1), spreadWhilePalindrome(s, i, i + 1));
			int d = e - b + 1;
			if (q == d) // a candidate. will constitute the result if a longer one won't be found 
				a.add(s.substring(i - (q - 1) / 2, i + q / 2 + 1));
			else if (q > d) { // a longer one found
				a.clear();
				b = i - (q - 1) / 2;
				e = i + q / 2;
				a.add(s.substring(b, e + 1));
			}
		}
		return a;
	}

	private static int spreadWhilePalindrome(String s, int l, int r) {
		while (l >= 0 && r < s.length() && s.charAt(l) == s.charAt(r)) {
			l--;
			r++;
		}
		return r - l - 1;
	}


	static void testlongestPalindromes() {
		String[] a = new String[] {
			"abracadabra",
			"banana",
			"bananas",
			"abba",
			"aabb",
			"",
			"aaaaaaaaaaaaaaaaaaaaaa",
			"hehe",
			"he-he",
			"a",
			"zz",
			"eabcb",
			"121232343"
		};
		for (String v : a)
			System.out.println(longestPalindrome(v));
		System.out.println("------------------");
		for (String v : a)
			System.out.println(longestPalindromeSwp(v));
		for (String v : a)
			System.out.println(longestPalindromes(v));
		System.out.println("------------------");
		for (String v : a)
			System.out.println(longestPalindromeEac(v));
		System.out.println("------------------");
		for (String v : a)
			System.out.println(longestPalindromesSwp(v));
		System.out.println("------------------");
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
		System.out.printf("%n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~%n");
		testlongestPalindromes();
	}
}
