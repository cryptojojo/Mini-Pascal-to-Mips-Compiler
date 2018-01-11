package scanner;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @author Joseph Miller <miller12 @ augsburg.edu>
 * @version JDK/JRE 1.8.0_141
 */

public class TestMyScanner {

	/**
	 * Main function, runs program
	 *
	 *
	 * @param args
	 *            Takes in the file name of the pascal code
	 * 
	 * 
	 * @return void
	 */

	@SuppressWarnings("unlikely-arg-type")
	public static void main(String[] args) {
		String filename = args[0];
		FileInputStream fis = null;
		List<Token> tokenList = new ArrayList<Token>();
		try {
			fis = new FileInputStream(filename);
		} catch (Exception e) {
			e.printStackTrace();
		}
		InputStreamReader isr = new InputStreamReader(fis);
		MyScanner scanner = new MyScanner(isr);
		Token aToken = null;
		do {
			try {
				aToken = scanner.nextToken();
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (aToken != null && !aToken.equals(""))

			System.out.println(aToken);
			tokenList.add(aToken);

		} while (aToken != null);
		System.out.println("\nThe List of Token Arrays: ");
		System.out.println(tokenList);
	}

}