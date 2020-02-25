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
// File header comes here
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;

/**
 * This class models a simple book library. The main method of this class
 * implements the management system for this library.
 *
 */
public class ExceptionalLibrary {
	// instance fields
	private String address; // Street address of this library
	private Librarian librarian; // this library's librarian. This library must have only ONE
									// librarian
	private ArrayList<Book> books; // list of the books in this library
	private ArrayList<Subscriber> subscribers; // list of this library's subscribers

	/**
	 * Creates a new Library and initializes all its instance fields. Initially both
	 * books and subscribers lists are empty.
	 * 
	 * @param address           Address of this Library
	 * @param librarianUsername username of the librarian of this book library
	 * @param librarianPassword password of the librarian of this book library
	 */
	public ExceptionalLibrary(String address, String librarianUsername, String librarianPassword) {
		this.address = address;
		this.librarian = new Librarian(librarianUsername, librarianPassword); // creates the librarian
																				// of this library
		books = new ArrayList<Book>(); // creates an empty arraylist of books
		subscribers = new ArrayList<Subscriber>(); // creates an empty arraylist of subscribers
	}

	/**
	 * Returns the librarian of this library
	 * 
	 * @return the librarian
	 */
	public Librarian getLibrarian() {
		return librarian;
	}

	/**
	 * Returns the address of this library
	 * 
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Returns the list of books having a given title in this library. The
	 * comparison used by this method is case insensitive
	 * 
	 * @param title title of the book(s) to find
	 * @return ArrayList of the books having a given title in this library (0 or
	 *         more books can be found)
	 */
	public ArrayList<Book> findBookByTitle(String title) {
		ArrayList<Book> foundBooks = new ArrayList<>(); // create an empty ArrayList to store found
														// books
		// traverse the ArrayList books looking for books matching with the provided
		// title
		for (int i = 0; i < books.size(); i++)
			if (books.get(i).getTitle().equalsIgnoreCase(title))
				foundBooks.add(books.get(i));
		return foundBooks; // return found books ArrayList. It may be empty
	}

	/**
	 * Returns a Book given a book identifier if found, and null otherwise. If the
	 * book is not found, this method displays the following message: "Error: this
	 * book identifier didn't match any of our books identifiers."
	 * 
	 * @param bookId identifier of the book to find
	 * @return reference to the Book if found and null otherwise
	 */
	public Book findBook(int bookId) {
		// traverse the list of books and look for a match with bookId
		for (int i = 0; i < books.size(); i++)
			if (books.get(i).getID() == bookId) // match found
				return books.get(i);
		// book not found: display an error message and return null
		System.out.println("Error: this book identifier didn't match any of our books identifiers.");
		return null;
	}

	/**
	 * Returns the list of books having a given author. The comparison used by this
	 * method is case insensitive
	 * 
	 * @param author author of the book(s) to find
	 * @return ArrayList of the books having a given author (0 or more books can be
	 *         found)
	 */
	public ArrayList<Book> findBookByAuthor(String author) {
		ArrayList<Book> foundBooks = new ArrayList<>();// create an empty ArrayList to store found books
		// traverse the ArrayList books looking for books matching with the provided
		// author
		for (int i = 0; i < books.size(); i++)
			if (books.get(i).getAuthor().equalsIgnoreCase(author))
				foundBooks.add(books.get(i));
		return foundBooks; // return found books ArrayList. It may be empty
	}

	/**
	 * Adds a new book to the library (to the books list). This method displays the
	 * following message: "Book with Title " + title + " is successfully added to
	 * the library."
	 * 
	 * @param title  title of the new book
	 * @param author author of the new book
	 */
	public void addBook(String title, String author) {
		books.add(new Book(title, author));
		System.out.println("Book with Title " + title + " is successfully added to the library.");
	}

	/**
	 * Removes a book given its identifier from the library (from books list)
	 * 
	 * @param bookId identifier of the book to remove
	 * @return a reference to the removed book, and null if the book is not found in
	 *         this library or if it is not available
	 */
	public Book removeBook(int bookId) {
		// find the book
		Book book = findBook(bookId); // findBook displays an error message if book not found
		if (book != null) { // book found --> remove the book
			if (book.isAvailable()) // check if the book is available
				books.remove(book);
			else { // display error message, book not available
				System.out.println("You cannot remove a non available book. This book has been "
						+ "checked out by the subscriber n° " + book.getBorrowerCardBarCode() + " and is not "
						+ "yet returned.");
				return null;
			}
		}
		return book;
	}

	/**
	 * Adds a new subscriber to this library (to subscribers list). This method
	 * displays the following message: "Library card with bar code " + card bar code
	 * + " is successfully issued to the new subscriber " + name + "."
	 * 
	 * @param name        name of the new subscriber
	 * @param pin         4-digit personal identifier number of the new subscriber
	 * @param address     address of the new subscriber
	 * @param phoneNumber phone number of the new subscriber
	 * @throws InstantiationException if a new subscriber cannot be created
	 */
	public void addSubscriber(String name, int pin, String address, String phoneNumber) throws InstantiationException {
		// create a new subscriber
		Subscriber newSubscriber = new Subscriber(name, pin, address, phoneNumber); // may throw
		// an InstantiationException
		subscribers.add(newSubscriber); // add new subscriber
		System.out.println("Library card with bar code " + newSubscriber.getCARD_BAR_CODE()
				+ " is successfully issued to the new subscriber " + name + ".");
	}

	/**
	 * Finds a subscriber given its cardBarCode. This method displayed the following
	 * message: "Error: this card bar code didn't match any of our records." and
	 * returns null if the provided cardBarCode did not match with any of the
	 * subscribers' card bar codes
	 * 
	 * @param cardBarCode of the subscriber to find
	 * @return a reference to the subscriber if found, otherwise null
	 */
	public Subscriber findSubscriber(int cardBarCode) {
		// traverse the list of subscribers looking for a subscriber having the provided
		// cardBarCode
		for (int i = 0; i < subscribers.size(); i++)
			if (subscribers.get(i).getCARD_BAR_CODE() == cardBarCode)
				return subscribers.get(i);
		System.out.println("Error: this card bar code didn't match any of our records.");
		return null;
	}

	/**
	 * Displays a list of books
	 * 
	 * @param books ArrayList of books
	 */
	public static void displayBooks(ArrayList<Book> books) {
		// if the list books is empty display "No books found"
		if (books.isEmpty())
			System.out.println("No books found.");
		// books list not empty
		// Traverse the list of books and display book id, title, author, and
		// availability of each book
		for (int i = 0; i < books.size(); i++) {
			System.out.print("<Book ID>: " + books.get(i).getID() + " ");
			System.out.print("<Title>: " + books.get(i).getTitle() + " ");
			System.out.print("<Author>: " + books.get(i).getAuthor() + " ");
			System.out.println("<Is Available>: " + books.get(i).isAvailable());
		}
	}

	/**
	 * Checks if an array of command arguments has the correct length with respect
	 * to a provided count
	 * 
	 * @param commands      an array of Strings that stores the arguments extracted
	 *                      from a user command line
	 * @param validArgCount valid count of arguments of the provided commands
	 * @throws ParseException if commands length is different from validArgCount
	 *                        with default errorOffset equals to zero
	 */
	public void checkCommandArgumentsCount(String[] commands, int validArgCount) throws ParseException {
		if (commands.length != validArgCount)
			throw new ParseException(this.getSyntaxErrorMsg(), 0);
	}

	/**
	 * Parses the String argument as a phone number
	 * 
	 * @param s           String that represents a phone number
	 * @param errorOffset errorOffset for the ParseException if thrown
	 * @throws ParseException if the String argument cannot be parsed as a phone
	 *                        number
	 */
	public void parsePhoneNumber(String s, int errorOffset) throws ParseException {
		try {
			Long.parseLong(s); // parse the String argument s as a phone number (as long number)
		} catch (NumberFormatException e) { // syntax error
			throw new ParseException("Error: The phone number MUST be a NUMBER.\n", errorOffset);
		}
	}

	/**
	 * Parses and runs a command line provided by a librarian to add a new book
	 * 
	 * @param commands an array of Strings that stores the arguments extracted from
	 *                 a command line provided by the librarian to add a new book
	 * @throws ParseException if commands include any syntax error or invalid
	 *                        argument
	 */
	public void parseRunLibrarianAddBookCommand(String[] commands) throws ParseException {
		// commands[1]: book title, commands[2]: book author
		// check the syntax of the command line with respect to its arguments count
		this.checkCommandArgumentsCount(commands, 3); // checks if commands stores 3 arguments
		// create and add new Book
		this.addBook(commands[1], commands[2]);
	}

	/**
	 * Parses and runs add subscriber command line provided by a librarian
	 * 
	 * @param commands an array of Strings that stores the arguments extracted from
	 *                 a command line provided by the librarian for adding a new
	 *                 subscriber
	 * @throws InstantiationException if a new instance of the Subscriber cannot be
	 *                                created
	 * @throws ParseException         if the syntax of the provided command line is
	 *                                incorrect
	 */
	public void parseRunLibrarianAddSubscriberCommand(String[] commands) throws
	InstantiationException, ParseException {
		try {
			//check valid commands
			checkCommandArgumentsCount(commands, 5);
			//check and parse pin input
			parsePinCode(commands[2], 1);
		} catch (ParseException e) {
			throw new ParseException("Error", 2);
		}
		// commands[1] name; commands[2] pin; commands[3] address; commands[4] phone
		// number
		
		this.addSubscriber(commands[1], Integer.parseInt(commands[2]), commands[3], commands[4]);
	}

	/**
	 * Parses and runs a command line provided by a librarian to checkout a book for
	 * a subscriber
	 * 
	 * @param commands an array of Strings that stores the arguments extracted from
	 *                 a command line provided by the librarian to checkout a book
	 *                 for a subscriber
	 * @throws ParseException if commands include any syntax error or invalid
	 *                        argument (card bar code or book identifier)
	 */
	public void parseRunLibrarianCheckoutBookCommand(String[] commands) throws ParseException {
		// Check out a Book for a subscriber [3 <card bar code> <book ID>]
			//check valid argument number
			checkCommandArgumentsCount(commands, 3);
			//check and parse bar code input
			parseCardBarCode(commands[1], 1);
			//parse and check book id input
			parseBookId(commands[2], 2);
		
		Book book = this.findBook(Integer.parseInt(commands[2]));
		Subscriber subscriber = this.findSubscriber(Integer.parseInt(commands[1]));
		if (book != null && subscriber != null) { // book ID correct - book found && subscriber found
			subscriber.checkoutBook(book); // helps the subscriber to check out the book
		}
	}

	/**
	 * Parses and runs a command line provided by the librarian to return a book for
	 * a subscriber
	 * 
	 * @param commands commands an array of Strings that stores the arguments
	 *                 extracted from a command line provided by the librarian to
	 *                 return a book for a subscriber
	 * @throws ParseException if commands include any syntax error or invalid
	 *                        argument (card bar code or book identifier)
	 */
	public void parseRunLibrarianReturnBookCommand(String[] commands) throws ParseException {
		// Return a Book for a subscriber [4 <card bar code> <book ID>]
		try {
			//check valid argument count
			checkCommandArgumentsCount(commands, 3);
			//parse and check card bar code input
			parseCardBarCode(commands[1], 2);
			//parse and check book id input
			parseBookId(commands[2], 3);
		} catch (ParseException e) {
			throw new ParseException("Error: Cannot Return Book", 1);
		}

		Book book = this.findBook(Integer.parseInt(commands[2]));
		Subscriber subscriber = this.findSubscriber(Integer.parseInt(commands[1]));

		if (book != null && (subscriber != null)) { // book ID correct - book found
													// subscriber found - correct card bar code
			subscriber.returnBook(book);// helps the subscriber to return a book
		}
	}

	/**
	 * Parses and runs a command line provided by a librarian to display the
	 * personal information of a subscriber
	 * 
	 * @param commands an array of Strings that stores the arguments extracted from
	 *                 a command line provided by the librarian to display the
	 *                 personal info of a subscriber
	 * @throws ParseException if commands include any syntax error or invalid
	 *                        argument (card bar code)
	 */
	public void parseRunLibrarianDisplayPersonalInfoOfSubscriberCommand(String[] commands) throws ParseException {
		try {
			//check valid argument count
			checkCommandArgumentsCount(commands, 2);
			//check and parse card bar code input
			parseCardBarCode(commands[1], 2);
		} catch (ParseException e) {
			throw new ParseException("Error: Cannot Display Personal Info", 1);
		}

		Subscriber subscriber = this.findSubscriber(Integer.parseInt(commands[1]));
		if (subscriber != null) { // subscriber found
			subscriber.displayPersonalInfo();
		}
	}

	/**
	 * Parses and runs a command line provided by a librarian to display the books
	 * checked out by a subscriber
	 * 
	 * @param commands an array of Strings that stores the arguments extracted from
	 *                 a command line provided by the librarian to display the books
	 *                 checked out by a subscriber * @throws ParseException if
	 *                 commands include any syntax error or invalid argument (card
	 *                 bar code)
	 */
	public void parseRunLibrarianDisplayBooksCheckedOutBySubscriberCommand(String[] commands)
			throws ParseException {
		try {
			//check valid argument count
			checkCommandArgumentsCount(commands, 2);
			//check and parse card bar code
			parseCardBarCode(commands[1], 2);
		} catch (ParseException e) {
			throw new ParseException("Error: Cannot Display Books Checked Out", 1);
		}
		Subscriber subscriber = this.findSubscriber(Integer.parseInt(commands[1]));
		if (subscriber != null) { // subscriber found
			subscriber.displayBooksCheckedOut();
		}
	}

	/**
	 * Parses and runs a command line provided by a librarian to remove a book
	 * 
	 * @param commands an array of Strings that stores the arguments extracted from
	 *                 a command line provided by the librarian to remove a book
	 * @throws ParseException if commands include any syntax error or invalid
	 *                        argument (arguments count, book id)
	 */
	public void parseRunLibrarianRemoveBookCommand(String[] commands) throws ParseException {
		try {
			//check valid argument count
			checkCommandArgumentsCount(commands, 2);
			//check and parse book id
			parseBookId(commands[1], 1);
		} catch (ParseException e) {
			throw new ParseException("Error: Cannot Remove", 1);
		}

		this.removeBook(Integer.parseInt(commands[1])); // remove a book given its id
	}

	/**
	 * Parses and runs a command line provided by a subscriber to checkout a book
	 * 
	 * @param commands   an array of Strings that stores the arguments extracted
	 *                   from a command line provided by a subscriber to checkout a
	 *                   book
	 * @param subscriber reference to the subscriber who is going to check out a
	 *                   book
	 * @throws ParseException if commands include any syntax error or invalid
	 *                        argument
	 */
	public void parseRunSubscriberCheckoutBookCommand(String[] commands, Subscriber subscriber) throws ParseException {
		try {
			//check valid argument count
			checkCommandArgumentsCount(commands, 2);
			//check and parse book id input
			parseBookId(commands[1], 1);
		} catch (ParseException e) {
			throw new ParseException("Error: Cannot CheckOut", 1);
		}

		Book book = this.findBook(Integer.parseInt(commands[1]));
		if (book != null)
			subscriber.checkoutBook(book);
	}

	/**
	 * Parses and runs a command line provided by a subscriber to return a book
	 * 
	 * @param commands   commands an array of Strings that stores the arguments
	 *                   extracted from a command line provided by a subscriber to
	 *                   return a book
	 * @param subscriber reference to the subscriber who is going to return a book
	 * @throws ParseException if commands include any syntax error or invalid
	 *                        argument
	 */
	public void parseRunSubscriberReturnBookCommand(String[] commands, Subscriber subscriber) throws ParseException {
		try {
			//check valid argument count
			checkCommandArgumentsCount(commands, 2);
			//check and parse book id input
			parseBookId(commands[1], 1);
		} catch (ParseException e) {
			throw new ParseException("Error: Cannot Return", 1);
		}

		// look for the book and return it if it is already checked out by the
		// subscriber
		Book book = this.findBook(Integer.parseInt(commands[1]));
		if (book != null)
			subscriber.returnBook(book);

	}

	/**
	 * Parses and runs a command line provided by a subscriber to update his phone
	 * number After updating the phone number of the subscriber, this method
	 * displays the following message: "Phone number successfully updated."
	 * 
	 * @param commands   commands an array of Strings that stores the arguments
	 *                   extracted from a command line provided by a subscriber to
	 *                   update his phone number
	 * @param subscriber reference to the subscriber who is going to update his
	 *                   phone number
	 * @throws ParseException if commands include any syntax error or invalid
	 *                        argument
	 */
	public void parseRunSubscriberUpdatePhoneNumberCommand(String[] commands, Subscriber subscriber)
			throws ParseException {
		try {
			//check valid argument count
			checkCommandArgumentsCount(commands, 2);
			//check and parse phone number input
			parsePhoneNumber(commands[1], 1);
		} catch (ParseException e) {
			throw new ParseException("Error: Cannot Update Phone Number", 1);
		}
		subscriber.setPhoneNumber(commands[1]);
		System.out.println("Phone number successfully updated.");
	}

	/**
	 * Parses and runs a command line provided by a subscriber to find a list of
	 * books by title. This method calls findBookByTitle() method and displays the
	 * content of the returned ArrayList of Books if it is not empty. If no books
	 * match the search criteria (findBookByTitle() returned an empty list), this
	 * method displays the following message: "No books match your search."
	 * 
	 * @param commands   commands an array of Strings that stores the arguments
	 *                   extracted from a command line provided by a subscriber to
	 *                   find a list of books by title
	 * @param subscriber reference to the subscriber who is going to search for
	 *                   books
	 * @throws ParseException if commands include any syntax error or invalid
	 *                        argument
	 */
	public void parseRunSubscriberFindBooksByTitleCommand(String[] commands, Subscriber subscriber)
			throws ParseException {
		// Search a book by title commands[1]: title
		this.checkCommandArgumentsCount(commands, 2);
		ArrayList<Book> bookList = this.findBookByTitle(commands[1]);
		if (!bookList.isEmpty())
			displayBooks(bookList);
		else
			System.out.println("No books match your search.");
	}

	/**
	 * Parses and runs a command line provided by a subscriber to a list of books by
	 * author This method makes call of findBookByAuthor() method and displays the
	 * content of the returned ArrayList of Books if it is not empty. If no books
	 * match the search criteria (findBookByAuthor() returned an empty list), this
	 * method displays the following message: "No books match your search."
	 * 
	 * @param commands   commands an array of Strings that stores the arguments
	 *                   extracted from a command line provided by a subscriber to a
	 *                   list of books by author
	 * @param subscriber reference to the subscriber who is going to search for
	 *                   books
	 * @throws ParseException if commands include any syntax error or invalid
	 *                        argument
	 */
	public void parseRunSubscriberFindBooksByAuthorCommand(String[] commands, Subscriber subscriber)
			throws ParseException {
		// Search a book by author commands[1]: author
		this.checkCommandArgumentsCount(commands, 2);
		ArrayList<Book> bookList = this.findBookByAuthor(commands[1]);
		if (!bookList.isEmpty())
			displayBooks(bookList);
		else
			System.out.println("No books match your search.");
	}

	/**
	 * Parses and runs a command line provided by a subscriber to update its home
	 * address This method displays "Address successfully updated." after the
	 * subscriber's address is updated
	 * 
	 * @param commands   commands an array of Strings that stores the arguments
	 *                   extracted from a command line provided by a subscriber to
	 *                   update his address
	 * @param subscriber reference to the subscriber who is going to update his
	 *                   address
	 * @throws ParseException if commands include any syntax error or invalid
	 *                        argument
	 */
	public void parseRunSubscriberUpdateAddressCommand(String[] commands, Subscriber subscriber) throws ParseException {
		// Update address commands[1]: address
		this.checkCommandArgumentsCount(commands, 2);
		subscriber.setAddress(commands[1]);
		System.out.println("Address successfully updated.");
	}

	/**
	 * Parses and runs a command line provided by a librarian to save the titles and
	 * authors of the current list of books
	 * 
	 * @param commands commands an array of Strings that stores the arguments
	 *                 extracted from a command line provided by a librarian to save
	 *                 the current list of books
	 * @throws ParseException if commands include any syntax error or invalid
	 *                        argument
	 */
	public void parseRunLibrarianSaveBooksCommand(String[] commands) throws ParseException {
		//initialize variables
		String author = "";
		String title = "";
		PrintWriter writer = null;
		//try creating a file with name input
		try {
		writer = new PrintWriter(commands[1]);
		}
		catch(FileNotFoundException e) {
			throw new ParseException("Error: File cannot be created", 1);
		}
		//loop through the books arraylist
		for (int i = 0; i < books.size(); i++) {
			//get author of book at index i
			author = books.get(i).getAuthor();
			//get title of book at index i
			title = books.get(i).getTitle();
			//print author:title to file created
			writer.print(author + ":" + title + "\n");
		}
		//close writer
		writer.close();
	}

	/**
	 * Parses and runs a command line provided by a librarian to load a list of
	 * books (title:author) from a file given its filename and new books with these
	 * pairs of title/author to the current list of books
	 * 
	 * @param commands commands an array of Strings that stores the arguments
	 *                 extracted from a command line provided by a librarian to load
	 *                 a list of books from a file and add it to the current list of
	 *                 books
	 * @throws ParseException if commands include any syntax error or invalid
	 *                        argument
	 */
	public void parseRunLibrarianLoadBooksCommand(String[] commands) throws ParseException {
		//initialize variables
		this.checkCommandArgumentsCount(commands, 2);		
		FileReader file = null;
		BufferedReader buffer;
		//try opening files
		try {
			file = new FileReader(commands[1]);
			buffer = new BufferedReader(file);
			String s = buffer.readLine();
			//loop through the open file while there is input on the line
			while(s != null) {
				//skip blank lines
				if (!(s.trim().equals(""))) {
					//retrieve the author and title into an array
					String[] authorTitle = s.trim().split(":");
					//add a book with given parameters
					addBook(authorTitle[0],authorTitle[1]);
					//move to next line
					s = buffer.readLine();
			}
			}
		}
		catch(FileNotFoundException e) {
			System.out.println("Error: File was not found");
		}
		catch(IOException e) {
			System.out.println("Error: an IO exception has occured");
		}
		finally {
			try {
			file.close();
			}
			catch(IOException e) {
				throw new ParseException("Error: an IO exception has occured", 1);
			}
		}
		
	}

	/**
	 * Parses and runs a command line provided by a user to login to the application
	 * as librarian
	 * 
	 * @param commands an array of Strings that stores the arguments extracted from
	 *                 a command line provided by a user to login as a librarian
	 * @param scanner  a reference to a Scanner object to read the librarian command
	 *                 lines after a successful login into the application
	 * @throws ParseException thrown if the command line provided by the user to
	 *                        login as a librarian is invalid or contains a syntax
	 *                        error
	 */
	public void parseRunLoginAsLibrarian(String[] commands, Scanner scanner) throws ParseException {
		try {
			//check valid command argument count
			checkCommandArgumentsCount(commands, 2);
		}
		catch(ParseException e){
			throw new ParseException("Error: Login As Librarian Error", 1);
		}
		
		if (this.librarian.checkPassword(commands[1])) { // check if the password is correct
			// read and process librarian commands
			readProcessLibrarianCommand(scanner);
		} else { // wrong password
			System.out.println("Error: Password incorrect!");
		}
	}

	/**
	 * Parses and runs a command line provided by a user to login to the application
	 * as subscriber
	 * 
	 * @param commands an array of Strings that stores the arguments extracted from
	 *                 a command line provided by a user to login as a subscriber
	 * @param scanner  a reference to a Scanner object to read the subscriber
	 *                 command lines after a successful login into the application
	 * @throws ParseException thrown if the command line provided by the user to
	 *                        login as a subscriber is invalid or contains a syntax
	 *                        error
	 */
	public void parseRunLoginAsSubscriber(String[] commands, Scanner scanner) throws ParseException {
		try {
			checkCommandArgumentsCount(commands, 2);
		}
		catch(ParseException e){
			throw new ParseException("Error: Login As Subscriber Error", 1);
		}

		Subscriber subscriber = this.findSubscriber(Integer.parseInt(commands[1]));
		if (subscriber != null) {
			if (subscriber.getPin() == Integer.parseInt(commands[2])) // correct PIN
				// read and process subscriber commands
				readProcessSubscriberCommand(subscriber, scanner);
			else
				System.out.println("Error: Incorrect PIN.");
		}

	}

	/**
	 * Reads and processes the user commands with respect to the menu of this
	 * application
	 * 
	 * @param scanner Scanner object used to read the user command lines
	 */
	public void readProcessUserCommand(Scanner scanner) {
		String promptCommandLine = "ENTER COMMAND: ";
		displayMainMenu(); // display the library management system main menu
		System.out.print(promptCommandLine);
		String command = scanner.nextLine(); // read user command line
		String[] commands = command.trim().split(" "); // split user command
		while (!(commands[0].equals("3") && commands.length == 1)) { // 3: Exit the application
			try {
				switch (commands[0]) {
				case "1": // login as librarian commands[1]: password
					this.parseRunLoginAsLibrarian(commands, scanner);
					break;
				case "2": // login as subscriber commands[1]: card bar code commands[2]: 4-digit PIN
					this.parseRunLoginAsSubscriber(commands, scanner);
					break;
				default:
					System.out.println(this.getSyntaxErrorMsg());
				}
			} catch (ParseException e) { // This catch block catches only ParseException exceptions thrown
				// if the syntax of the user command line from the main menu is incorrect
				// All exceptions thrown from readProcessLibrarianCommand() or
				// readProcessSubscriberCommand()
				// methods must be handled using try catch blocks within those methods.
				String error;
				if (e.getErrorOffset() == 0) {
					error = " Arguments count is incorrect.";
				} else
					error = " Argument number " + e.getErrorOffset() + " within your command line is invalid.";
				System.out.println(e.getMessage() + error); // display the exception's error message
			}
			// read and split next user command line
			displayMainMenu(); // display the library management system main menu
			System.out.print(promptCommandLine);
			command = scanner.nextLine(); // read user command line
			commands = command.trim().split(" "); // split user command line
		}
	}

	/**
	 * Reads and processes the librarian commands according to the librarian menu
	 * 
	 * @param scanner Scanner object used to read the librarian command lines
	 */
	public void readProcessLibrarianCommand(Scanner scanner) {
		// TODO This method should catch any ParseException or InstantiationException
		// that may be thrown
		// from any called method to parse and run the librarian command line
		String promptCommandLine = "ENTER COMMAND: ";
		ExceptionalLibrary.displayLibrarianMenu(); // display the library management system main menu
		System.out.print(promptCommandLine);
		String command = scanner.nextLine(); // read user command line
		String[] commands = command.trim().split(" "); // split user command
		while (!(commands[0].toUpperCase().equals("9") && commands.length == 1)) { // "9": Exit the
																					// application

			switch (commands[0].toUpperCase()) {
			case "1": // add a new book commands[1] title; commands[2] author
				//call parseRunLibrarianAddBookCommand(commands);
				try {
				parseRunLibrarianAddBookCommand(commands);
				}
				catch(ParseException e) {
					System.out.print(getSyntaxErrorMsg());
				}
				break;
			case "2": // add a new subscriber: commands[1] name; commands[2] pin; commands[3] address;
						// commands[4] phone number
				//call parseRunLibrarianAddSubscriberCommand(commands);
				try {
					parseRunLibrarianAddSubscriberCommand(commands);
					}
					catch(ParseException e) {
						System.out.print(getSyntaxErrorMsg());
					}
					catch(InstantiationException excpt) {
						System.out.print(getSyntaxErrorMsg());
					}
				break;
			case "3": // Check out a Book for a subscriber [3 <card bar code> <book ID>]
				//call parseRunLibrarianCheckoutBookCommand(commands);
				try {
					parseRunLibrarianCheckoutBookCommand(commands);
					}
					catch(ParseException e) {
						System.out.print(getSyntaxErrorMsg());
					}
				break;
			case "4": // Return a Book for a subscriber [4 <card bar code> <book ID>]
				//call parseRunLibrarianReturnBookCommand(commands);
				try {
					parseRunLibrarianReturnBookCommand(commands);
					}
					catch(ParseException e) {
						System.out.print(getSyntaxErrorMsg());
					}
				break;
			case "5": // Display Personal Info of a Subscriber [5 <card bar code>]
				//call parseRunLibrarianDisplayPersonalInfoOfSubscriberCommand(commands);
				try {
					parseRunLibrarianDisplayPersonalInfoOfSubscriberCommand(commands);
					}
					catch(ParseException e) {
						System.out.print(getSyntaxErrorMsg());
					}
				break;
			case "6": // [6 <card bar code>] Display Books Checked out by a Subscriber");
				//call
				// parseRunLibrarianDisplayBooksCheckedOutBySubscriberCommand(commands);
				try {
					parseRunLibrarianDisplayBooksCheckedOutBySubscriberCommand(commands);
					}
					catch(ParseException e) {
						System.out.print(getSyntaxErrorMsg());
					}
				break;
			case "7": // [7] Display Books List
				ExceptionalLibrary.displayBooks(this.books);
				break;
			case "8": // [8 <book ID>] Remove a Book
				//call parseRunLibrarianRemoveBookCommand(commands);
				try {
					parseRunLibrarianRemoveBookCommand(commands);
					}
					catch(ParseException e) {
						System.out.print(getSyntaxErrorMsg());
					}
				break;
			case "L": // [L <filename>] Load list of Books from a data file named filename
				//call parseRunLibrarianLoadBooksCommand(commands);
				try {
					parseRunLibrarianLoadBooksCommand(commands);
				}
				catch(ParseException e) {
					System.out.print(getSyntaxErrorMsg());
				}
				break;
			case "S": // [S <filename>] Save list of Books to a data file named filename
				// call parseRunLibrarianSaveBooksCommand(commands);
				try {
					parseRunLibrarianSaveBooksCommand(commands);
				}
				catch(ParseException e) {
					System.out.println(getSyntaxErrorMsg());
				}
				break;
			default:
				System.out.println(this.getSyntaxErrorMsg()); // Syntax Error

			}

			displayLibrarianMenu(); // display the library management system main menu
			System.out.print(promptCommandLine);
			command = scanner.nextLine(); // read user command line
			commands = command.trim().split(" "); // split user command line
		}
	}

	/**
	 * Reads and processes the subscriber commands according to the subscriber menu
	 * 
	 * @param subscriber Current logged in subscriber
	 * @param scanner    Scanner object used to read the librarian command lines
	 */
	public void readProcessSubscriberCommand(Subscriber subscriber, Scanner scanner) {
		// TODO This method should catch any ParseException that may be thrown
		// from any called method to parse and run the librarian command line
		String promptCommandLine = "ENTER COMMAND: ";
		ExceptionalLibrary.displaySubscriberMenu(); // display the library management system main menu
		System.out.print(promptCommandLine);
		String command = scanner.nextLine(); // read user command line
		String[] commands = command.trim().split(" "); // split user command
		while (!(commands[0].toUpperCase().equals("9") && commands.length == 1)) { // "9": Exit the
																					// Subscriber space

			switch (commands[0]) {
			case "1": // check out a book commands[1]: book id
				//call parseRunSubscriberCheckoutBookCommand(commands, subscriber);
				try {
					parseRunSubscriberCheckoutBookCommand(commands, subscriber);
					}
					catch(ParseException e) {
						System.out.print(getSyntaxErrorMsg());
					}
				break;
			case "2": // return a book commands[1]: book id
				//call parseRunSubscriberReturnBookCommand(commands, subscriber);
				try {
					parseRunSubscriberReturnBookCommand(commands, subscriber);
					}
					catch(ParseException e) {
						System.out.print(getSyntaxErrorMsg());
					}
				break;
			case "3": // Search a book by title commands[1]: title
				//call parseRunSubscriberFindBooksByAuthorCommand(commands, subscriber);
				try {
					parseRunSubscriberFindBooksByAuthorCommand(commands, subscriber);
					}
					catch(ParseException e) {
						System.out.print(getSyntaxErrorMsg());
					}
				break;
			case "4": // Search a book by author commands[1]: author
				//call parseRunSubscriberFindBooksByTitleCommand(commands, subscriber);
				try {
					parseRunSubscriberFindBooksByTitleCommand(commands, subscriber);
					}
					catch(ParseException e) {
						System.out.print(getSyntaxErrorMsg());
					}
				break;
			case "5": // print lists of books checked out
				subscriber.displayBooksCheckedOut();
				break;
			case "6": // print history of books returned
				subscriber.displayHistoryBooksReturned();
				break;
			case "7": // Update address commands[1]: address
				//call parseRunSubscriberUpdateAddressCommand(commands, subscriber);
				try {
					parseRunSubscriberUpdateAddressCommand(commands, subscriber);
					}
					catch(ParseException e) {
						System.out.print(getSyntaxErrorMsg());
					}
				break;
			case "8": // Update phone number commands[1]: phone number
				//call parseRunSubscriberUpdatePhoneNumberCommand(commands, subscriber);
				try {
					parseRunSubscriberUpdatePhoneNumberCommand(commands, subscriber);
					}
					catch(ParseException e) {
						System.out.print(getSyntaxErrorMsg());
					}
				break;

			}

			// read and split next user command line
			displaySubscriberMenu(); // display the library management system main menu
			System.out.print(promptCommandLine);
			command = scanner.nextLine(); // read user command line
			commands = command.trim().split(" "); // split user command line
		}
	}

	/**
	 * helper method that returns a general - Syntax error message
	 */
	private String getSyntaxErrorMsg() {
		return "Syntax Error: Please enter a valid command!\n";
	}

	/**
	 * Displays the main menu for this book library application
	 */
	private static void displayMainMenu() {
		System.out.println("\n--------------------------------------------------------");
		System.out.println("     Welcome to our Book Library Management System");
		System.out.println("--------------------------------------------------------");
		System.out.println("Enter one of the following options:");
		System.out.println("[1 <password>] Login as a librarian");
		System.out.println("[2 <card bar code> <4-digits pin>] Login as a Subscriber");
		System.out.println("[3] Exit"); // Exit the application
		System.out.println("--------------------------------------------------------");
	}

	/**
	 * Displays the menu for a Subscriber
	 */
	private static void displaySubscriberMenu() {
		System.out.println("\n--------------------------------------------------------");
		System.out.println("    Welcome to Subscriber's Space");
		System.out.println("--------------------------------------------------------");
		System.out.println("Enter one of the following options:");
		System.out.println("[1 <book ID>] Check out a book");
		System.out.println("[2 <book ID>] Return a book");
		System.out.println("[3 <title>] Search a Book by title");
		System.out.println("[4 <author>] Search a Book by author");
		System.out.println("[5] Print list of books checked out");
		System.out.println("[6] Print history of returned books");
		System.out.println("[7 <address>] Update address");
		System.out.println("[8 <phone number>] Update phone number");
		System.out.println("[9] Logout");
		System.out.println("--------------------------------------------------------");
	}

	/**
	 * Displays the menu for the Librarian
	 */
	private static void displayLibrarianMenu() {
		System.out.println("\n--------------------------------------------------------");
		System.out.println("    Welcome to Librarian's Space");
		System.out.println("--------------------------------------------------------");
		System.out.println("Enter one of the following options:");
		System.out.println("[1 <title> <author>] Add new Book");
		System.out.println("[2 <name> <pin> <address> <phone number>] Add new subscriber");
		System.out.println("[3 <card bar code> <book ID>] Check out a Book for a subscriber");
		System.out.println("[4 <card bar code> <book ID>] Return a Book for a subscriber");
		System.out.println("[5 <card bar code>] Display Personal Info of a Subscriber");
		System.out.println("[6 <card bar code>] Display Books Checked out by a Subscriber");
		System.out.println("[7] Display All Books");
		System.out.println("[8 <book ID>] Remove a Book");
		System.out.println("[L <filename.data>] Load list of Books from filename.data");
		System.out.println("[S <filename.data>] Save list of Books to filename.data");
		System.out.println("[9] Logout");
		System.out.println("--------------------------------------------------------");
	}

	/**
	 * Display the Application GoodBye and logout message.
	 */
	private static void displayGoodByeLogoutMessage() {
		System.out.println("\n--------------------------------------------------------");
		System.out.println("       Thanks for Using our Book Library App!!!!");
		System.out.println("--------------------------------------------------------");
	}
	/**
	 * 
	 *Parses the String argument as a PIN (Personal Identification Number)
	 *
	 *@param s - A String that represents a PIN code
	 *	     errorOffset - errorOffset for the ParseException if thrown
	 *@return an integer that represents the parsed PIN if it is valid
	 *        (4-digits number in the range of [1000, 9999]        
	 */
	public int parsePinCode(String s, int errorOffset) throws ParseException {
		int parsePin = -1;
		
		try {
			//try to call a parse on the string
			parsePin = Integer.parseInt(s);
		} catch (NumberFormatException e) {
			throw new ParseException("Error: Pin Code must be a NUMBER.\n", errorOffset);
		}
		//check if pin is 4 digits
		if(parsePin < 1000 || parsePin > 9999) {
			throw new ParseException("Error: Pin Code Must be greater than 1000 or less than 9999",
					errorOffset);
		}
		return parsePin;
	}
	/**
	 * 
	 * Parses a String argument as a subscriber card bar code
	 *
	 *@param s - a String that represents a card bar code
	 *	     errorOffset - errorOffset for the ParseException if thrown
	 *@return an integer that represents the parsed card bar code if it is valid        
	 */
	public int parseCardBarCode(String s, int errorOffset) throws ParseException {
		int parseCardBarCode = -1;
		
		try {
			//try to parse the string passed in
			parseCardBarCode = Integer.parseInt(s);
		} catch (NumberFormatException e) {
			throw new ParseException("Error: Card Bar Code must be a NUMBER", errorOffset);
		}
		if (Subscriber.checkCardBarCode(parseCardBarCode) == false) {
			throw new ParseException("Error: Card Bar Code MUST be a 10 digit number", errorOffset);
		}
		return parseCardBarCode;
	}
	/**
	 * 
	 * Parses a String argument as a subscriber card bar code
	 *
	 *@param s - a String that represents a book identifier
	 *	     errorOffset - errorOffset for the ParseException if thrown
	 *@return an integer that represents the book id if valid        
	 */
	public int parseBookId(String s, int errorOffset) throws ParseException {
		int parseBookID = -1;
		
		try {
			//try to parse to string passed in
			parseBookID = Integer.parseInt(s);
		} catch (NumberFormatException e) {
			throw new ParseException("Error: Book Id must be a NUMBER", errorOffset);
		}
		
		return parseBookID;
	}

	/**
	 * Main method that represents the driver for this application
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in); // create a scanner object to read user inputs
		// create a new library object
		ExceptionalLibrary madisonLibrary = new ExceptionalLibrary("Madison, WI", "april", "abc");
		// read and process user command lines
		madisonLibrary.readProcessUserCommand(scanner);
		displayGoodByeLogoutMessage(); // display good bye message
		scanner.close();// close this scanner
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
		//checks if any of the tests fail incorrectly
		if (testParsePin1 == false || testParsePin2 == false || testParsePin3 == false) {
			testParsePin = false;
		}
		return testParsePin;
	}
}
