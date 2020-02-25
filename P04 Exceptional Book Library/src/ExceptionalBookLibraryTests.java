//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:           (descriptive title of the program making use of this file)
// Files:           (a list of all source files used by that program)
// Course:          (course number, term, and year)
//
// Author:          Lenea Guse
// Email:           laguse@wisc.edu
// Lecturer's Name: Gary Dahl
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name:    (name of your pair programming partner)
// Partner Email:   (email address of your programming partner)
// Partner Lecturer's Name: (name of your partner's lecturer)
// 
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
//   ___ Write-up states that pair programming is allowed for this assignment.
//   ___ We have both read and understand the course Pair Programming Policy.
//   ___ We have registered our team prior to the team registration deadline.
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully 
// acknowledge and credit those sources of help here.  Instructors and TAs do 
// not need to be credited here, but tutors, friends, relatives, room mates, 
// strangers, and others do.  If you received no outside help from either type
//  of source, then please explicitly indicate NONE.
//
// Persons:         N/A
// Online Sources:  N/A
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////
import java.text.ParseException;

public class ExceptionalBookLibraryTests {
	public static void main(String[] args) {
		System.out.println(testLibraryParseCardBarCode());
		System.out.println(testLibraryParseRunLibrarianCheckoutBookCommand());
		System.out.println(testLibraryParseRunSubscriberReturnBookCommand());
		System.out.println(testLibrarianCreateSubscriber());
		System.out.println(testLibraryParsePinCode());
	}

	public static boolean testLibraryParseCardBarCode() {
		//initialize parameters
		boolean barCodeTest = true;
		boolean barCodeTest1 = false;
		boolean barCodeTest2 = false;
		boolean barCodeTest3 = true;
		boolean barCodeTest4 = true;
		ExceptionalLibrary newLib = new ExceptionalLibrary("Madison, WI", "april", "abc");
		//tests letters being passed in as a bar code
		try {
			newLib.parseCardBarCode("abc", 2);
		} catch (ParseException e) {
			System.out.println("Caught parse exception 1");
			barCodeTest1 = true;
		}
		//tests letters being passed in as a bar code
		try {
			newLib.parseCardBarCode("hello", 2);
		} catch (ParseException e) {
			System.out.println("Caught parse exception 2");
			barCodeTest2 = true;
		}
		//tests a good bar code
		try {
			newLib.parseCardBarCode("2019000001", 2);
		} catch (ParseException e) {
			System.out.println("Caught parse exception incorrectly 1");
			barCodeTest3 = false;
		}
		//tests a good bar code
		try {
			newLib.parseCardBarCode("2019000536", 2);
		} catch (ParseException e) {
			System.out.println("Caught parse exception incorrectly 2");
			barCodeTest4 = false;
		}
		//checks if any of the tests failed wrongly
		if (barCodeTest1 == false || barCodeTest2 == false || barCodeTest3 == false ||
				barCodeTest4 == false) {
			barCodeTest = false;
		}

		return barCodeTest;
	}

	public static boolean testLibraryParseRunLibrarianCheckoutBookCommand() {
		// initializes parameters for test
		boolean libCheckOutTest = true;
		boolean libCheckOutTest1 = false;
		boolean libCheckOutTest2 = false;
		boolean libCheckOutTest3 = false;
		boolean libCheckOutTest4 = false;
		boolean libCheckOutTest5 = true;

		ExceptionalLibrary newLib = new ExceptionalLibrary("Madison, WI", "april", "abc");
		//commands[0]: check out ; commands[1]: bar code ; commands[2]: book id
		String[] commands1 = { "3", "abc", "1" };
		String[] commands2 = { "3", "2019000001", "&&&" };
		String[] commands3 = { "3", "2019000001", "2" };
		String[] commands4 = { "3", "5", "&&&" };
		String[] commands5 = { "3", "20190000001", "&&&" };
		//tests checkout with a bad bar code
		try {
			newLib.parseRunLibrarianCheckoutBookCommand(commands1);
		} catch (ParseException e) {
			libCheckOutTest1 = true;
		}
		//tests checkout for a bad book id
		try {
			newLib.parseRunLibrarianCheckoutBookCommand(commands2);
		} catch (ParseException e) {
			libCheckOutTest2 = true;
		}
		//tests checkout with appropriate inputs
		try {
			newLib.parseRunLibrarianCheckoutBookCommand(commands3);
		}
		catch (ParseException e) {
		libCheckOutTest5 = false;
		}
		//tests checkout with bad bar code and pin
		try {
			newLib.parseRunLibrarianCheckoutBookCommand(commands4);
		} catch (ParseException e) {
			libCheckOutTest3 = true;
		}
		//tests checkout with a bar code thats too large
		try {
			newLib.parseRunLibrarianCheckoutBookCommand(commands5);
		} catch (ParseException e) {
			// System.out.println("Error: commands5 failed correctly");
			libCheckOutTest4 = true;
		}
		//checks in any of the tests failed incorrectly
		if (libCheckOutTest1 == false || libCheckOutTest2 == false || libCheckOutTest3 == false
				|| libCheckOutTest4 == false || libCheckOutTest5 == false) {
			libCheckOutTest = false;
		}
		return libCheckOutTest;
	}

	public static boolean testLibraryParseRunSubscriberReturnBookCommand() {
		//initialize the parameters
		boolean subReturn = true;
		boolean subReturn1 = false;
		boolean subReturn2 = false;
		boolean subReturn3 = true;

		Subscriber sub = null;
		ExceptionalLibrary newLib = new ExceptionalLibrary("Madison, WI", "april", "abc");
		
		String[] commands1 = { "3", "abc" };
		String[] commands2 = { "3", "201900001", "&&&" };
		String[] commands3 = { "3", "1" };
		//creates a new subscriber
		try {
			sub = new Subscriber("jane", 1111, "Madison", "1651684313");
		} catch (InstantiationException e) {
			System.out.println("Error");
		}
		//tests if return book fails with bad bar code
		try {
			newLib.parseRunSubscriberReturnBookCommand(commands1, sub);
		} catch (ParseException e) {
			System.out.println("Error: commands1 failed correctly");
			subReturn1 = true;
		}
		//test if return book fails with too many command arguments
		try {
			newLib.parseRunSubscriberReturnBookCommand(commands2, sub);
		} catch (ParseException e) {
			System.out.println("Error: commands2 failed correctly");
			subReturn2 = true;
		}
		//tests if return book fails with valid arguments
		try {
			newLib.parseRunSubscriberReturnBookCommand(commands3, sub);
		} catch (ParseException e) {
			System.out.println("Error: commands3 failed incorrectly");
			subReturn3 = false;
		}
		//checks if any of the tests failed incorrectly
		if (subReturn1 == false || subReturn2 == false || subReturn3 == false) {
			subReturn = false;
		}

		return subReturn;
	}

	public static boolean testLibrarianCreateSubscriber() {
		//initializes parameters
		boolean testAddSub = true;
		boolean testAddSub1 = false;
		boolean testAddSub2 = false;
		boolean testAddSub3 = true;
		boolean testAddSub4 = false;
		
		Subscriber sub = null;
		ExceptionalLibrary newLib = new ExceptionalLibrary("Madison, WI", "april", "abc");
		
		String[] commands1 = { "2", "Mouna", "123", "Madison", "135438451" };
		String[] commands2 = { "2", "Jerry", "&&&&", "Madison", "135135454" };
		String[] commands3 = { "2", "Ron", "5555", "Madison", "513518464" };
		String[] commands4 = { "2", "Sam", "5555", "Madison", "265135154", "im tired" };
		//creates a new subscriber
		try {
			sub = new Subscriber("jane", 1111, "Madison", "1651684313");
		} catch (InstantiationException e) {
			System.out.println("Error");
		}
		//tests if add sub fails with a bad pin
		try {
			newLib.parseRunLibrarianAddSubscriberCommand(commands1);
		} catch (InstantiationException e) {
			System.out.println("Error: subscriber cannot be created");
		} catch (ParseException e) {
			System.out.println("Error: bad pin caught");
			testAddSub1 = true;
		}
		//tests if add sub fails with a bad pin
		try {
			newLib.parseRunLibrarianAddSubscriberCommand(commands2);
		} catch (InstantiationException e) {
			System.out.println("Error: subscriber cannot be created");
		} catch (ParseException e) {
			System.out.println("Error: bad pin caught");
			testAddSub2 = true;
		}
		//tests if add sub fails with valid parameters
		try {
			newLib.parseRunLibrarianAddSubscriberCommand(commands3);
		} catch (InstantiationException e) {
			System.out.println("Error: subscriber cannot be created");
		} catch (ParseException e) {
			System.out.println("Error: bad pin caught incorrectly");
			testAddSub3 = false;
		}
		//tests if add sub fails with too many command arguments
		try {
			newLib.parseRunLibrarianAddSubscriberCommand(commands4);
		} catch (InstantiationException e) {
			System.out.println("Error: subscriber cannot be created");
		} catch (ParseException e) {
			System.out.println("Error: too many arguments caught");
			testAddSub4 = true;
		}
		//checks if any tests failed incorrectly 
		if (testAddSub1 == false || testAddSub2 == false || testAddSub3 == false ||
				testAddSub4 == false) {
			testAddSub = false;
		}
		return testAddSub;
	}
	public static boolean testLibraryParsePinCode() {
		//initializes parameters
		boolean testParsePin = true;
		boolean testParsePin1 = false;
		boolean testParsePin2 = true;
		boolean testParsePin3 = false;
		
		ExceptionalLibrary newLib = new ExceptionalLibrary("Madison, WI", "april", "abc");
		
		String[] commands1 = { "3", "abc" };
		String[] commands2 = { "3", "1111", "&&&" };
		String[] commands3 = { "3", "5" };
		//test parse pin with letter pin
		try {
			newLib.parsePinCode(commands1[1], 1);
		}
		catch(ParseException e) {
			System.out.println("Error: incorrect pin1");
			testParsePin1 = true;
		}
		//test parsePinCode with too many arguments
		try {
			newLib.parsePinCode(commands2[1], 1);
		}
		catch(ParseException e) {
			System.out.println("Error: incorrect pin2");
			testParsePin2 = false;
		}
		//tests parse with too small of a pin code
		try {
			newLib.parsePinCode(commands3[1], 1);
		}
		catch(ParseException e) {
			System.out.println("Error: incorrect pin3");
			testParsePin3 = true;
		}
		
		if (testParsePin1 == false || testParsePin2 == false || testParsePin3 == false) {
			testParsePin = false;
		}
		return testParsePin;
	}
}
