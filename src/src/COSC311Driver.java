/**
 * Stefano Prezioso (Updated)
 * E01243936
 * COSC 311 Project 3
 * Version 02
 * Fall 2014
 */

/**
 * This will be the main driver program for many of your programs. Specifically,
 * you will need to define a data structure and related algorithms to use with this program.
 * We will be using the data file I have provided for you: a file of 68 records. Each record is composed
 * of three fields:
 *      String lastName
 *      String firstName
 *      String ID
 * ID may be implemented as an integer, but it is easier to implement as a string. Both lastName and firstName
 * may not be unique, but the ID **is** unique.
 * 
 * @author Bill Sverdlik
 * @version Version 1.0
 */

/*
 * IMPORTANT!!!!!!
 * Your projects should not contain any code in this file that modifies the class DataStructure directly.
 * You may find it convenient (but not required) that the file DataStructure contain an inner class.
 */
package src;

import java.io.IOException;
import java.util.*;

public class COSC311Driver {

	static DataStructure myStructure = new DataStructure();

	public static void main(String[] args) {

		/*
		 * The following declaration declares a data structure that will change
		 * from one assignment to the next. For example, you will need to
		 * implement the following as a doubly linked list, as well as a tree.
		 */

		System.out.println("Project 2");

		// Comment out this try-catch block of code to disable automatic filling
		// of dataStrucutre!
		try {
			FileReader.fillDataStructure(myStructure);
		} catch (IOException e) {
			e.printStackTrace();
		}

		int response;
		Scanner keyboard = new Scanner(System.in);

		do {
			System.out.println(" 1 Add a new student");
			System.out.println(" 2 Delete a student");
			System.out.println(" 3 Find a student by ID");
			System.out.println(" 4 List students by ID increasing");
			System.out.println(" 5 List students by first name increasing");
			System.out.println(" 6 List students by last name increasing");
			System.out.println(" 7 List students by ID decreasing");
			System.out.println(" 8 List students by first name decreasing");
			System.out.println(" 9 List students by last name decreasing");
			System.out.println(" ");
			System.out.println(" 0 End");

			response = keyboard.nextInt();

			switch (response) {
			case 1:
				addIt();
				break;
			case 2:
				deleteIt();
				break;
			case 3:
				findIt();
				break;
			case 4:
				listItIncreasingID(); // or see below for programming trick
				break;
			case 5:
				listItIncreasingfName();
				break;
			case 6:
				listItIncreasinglName();
				break;
			case 7:
				listItDecreasingID();
				break;
			case 8:
				listItDecreasingfName();
				break;
			case 9:
				listItDecreasinglName();
				break;
			default:
			}

		} while (response != 0);

		keyboard.close();
	}

	// OK Folks. I won't write all of these, but I'll give you an idea

	// Case 1: Add a new student. We need a unique ID number

	public static void addIt() {
		String name1, name2, tempID;
		// boolean found;
		Scanner keyboard = new Scanner(System.in);

		// *****Update: My insert method handles checking for a unique ID*****
		// do {
		System.out.print("Enter a unique ID number to add: ");
		tempID = keyboard.nextLine();
		//
		// // is it unique ?
		// found = myStructure.search(tempID);
		// if (found) {
		// System.out.println("ID already in use");
		// System.out.println("Please re-enter a unique ID.");
		// }
		// } while (found);

		// We found a unique ID. Now ask for first and last name

		System.out.print("Enter first name: ");
		name1 = keyboard.nextLine();
		System.out.print("Enter last name: ");
		name2 = keyboard.nextLine();

		// add to our data structure
		myStructure.insert(name1, name2, tempID);
	}

	// Case 2: delete a student. A student ID must be prompted for. If the ID
	// number does not exist in the database,
	// print out a message indicating a such, otherwise delete the entire record

	public static void deleteIt() {
		String deleteID;
		Scanner keyboard = new Scanner(System.in);

		System.out.print("Please enter the ID to be deleted: ");
		deleteID = keyboard.next();

		if (myStructure.deleteRecord(deleteID) == -1)
			System.out.println("The ID you entered does not exist.");
		else {
			System.out.println("Record has been deleted. Thank you.\n");
		}

	}

	// Case 3: find a student. A student ID must be prompted for. If the ID
	// number is not found, print out a
	// message indicating as such. Otherwise print out the entire record

	public static void findIt() {

		String findID;
		Scanner keyboard = new Scanner(System.in);

		System.out.print("Please enter the ID to be found: ");
		findID = keyboard.next();

		int indexOfRecord = myStructure.find(findID);

		if (indexOfRecord == -1)
			System.out.println("The ID you entered does not exist.");
		else
			System.out.println(myStructure.getDatabaseRecord(indexOfRecord)
					.toString());

	}

	// Cases 4,5,6,7,8,9
	// A little programming trickery. All of the listing methods below can call
	// the SAME method in DataStructure.
	// We'll pass 2 parameters: the first indicates which field, the second
	// indicates which order
	// fieldNum=1 first name
	// fieldNum=2 last name
	// fieldNum=3 ID
	// orderNum=1 increasing
	// orderNum=2 decreasing

	public static void listItIncreasingID() {
		myStructure.listIt(3, 1);
	}

	public static void listItIncreasingfName() {
		myStructure.listIt(1, 1);
	}

	public static void listItIncreasinglName() {
		myStructure.listIt(2, 1);
	}

	public static void listItDecreasingID() {
		myStructure.listIt(3, 2);
	}

	public static void listItDecreasingfName() {
		myStructure.listIt(1, 2);
	}

	public static void listItDecreasinglName() {
		myStructure.listIt(2, 2);
	}

}
