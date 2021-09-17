package edu.iastate.cs228.hw4;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * 
 * @author Sean Balogh
 * This class holds the main method which uses the methods and 
 * constructor from MsgTree to create a tree, print its characters
 * and binary codes, and decode and print its message.
 */

public class ArchivedMessageDecoder {	
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scnr = new Scanner(System.in);
		String filename = "";
		System.out.println("Please enter filename to decode: ");
		filename = scnr.next();
		File inputFile = new File(filename);
		Scanner fileScnr = new Scanner(inputFile);
		String encoding = fileScnr.nextLine();
		String codes = fileScnr.nextLine();
		
		if(!Character.isDigit(codes.charAt(0))){ // if the encoding scheme is split into two lines
			encoding += '\n';
			encoding += codes;
			codes = fileScnr.nextLine();
		}
		
		MsgTree messageTree = new MsgTree(encoding);
		System.out.println("Character  Code");
		System.out.println("----------------------------");
		MsgTree.printCodes(messageTree, "");
		System.out.println("\nMessage: ");
		MsgTree.decode(messageTree, codes);
		fileScnr.close();
		scnr.close();	
	}	
}
