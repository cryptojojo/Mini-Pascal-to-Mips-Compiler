package parser;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.StringReader;

import scanner.*;

public class TestParser {

	public static void main(String[] args) throws IOException{
		
		
		String pasc = "{ This is the simplest pascal program } program foo; begin end .";
		StringReader sr = new StringReader(pasc);

		Scanner scanner = new Scanner(sr);
		Token aToken = null;

		// TESTING
		aToken = scanner.nextToken();
		System.out.println(aToken);
		
		aToken = scanner.nextToken();
		System.out.println(aToken);
		
		aToken = scanner.nextToken();
		System.out.println(aToken);

	}

}
