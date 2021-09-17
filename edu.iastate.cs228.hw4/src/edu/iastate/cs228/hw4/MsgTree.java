package edu.iastate.cs228.hw4;

import java.util.Stack;

/**
 * 
 * @author Sean Balogh
 * This class models a binary tree with internal nodes, represented as 
 * '^' and leaf nodes, represented as characters, and contains the methods
 * to decode said binary tree into a message.
 */

public class MsgTree {
	/** Holds the character for a given node */
	public char payloadChar;

	/** Holds the left child for a given node */
	public MsgTree left; 
	
	/** Holds the right child for a given node */
	public MsgTree right;
	
	/**
	 * Builds a binary tree, iteratively with a stack, from a
	 * given encoding string comprised of characters and internal nodes,
	 * represented as '^'
	 * @param encodingString the encoding scheme
	 */
	public MsgTree(String encodingString) {
	
		Stack<MsgTree> tree = new Stack<>();
		MsgTree root = new MsgTree(encodingString.charAt(0));
		tree.push(root);
		
		for(int i=1; i<encodingString.length(); i++) {			
			MsgTree curr = null;
			while(!tree.isEmpty() && tree.peek().payloadChar != '^') {
				curr = tree.pop();
				if(curr.payloadChar != '^') {
					curr = tree.pop();
				}
			}
			if(curr != null) {
				curr.right = new MsgTree(encodingString.charAt(i));
				tree.push(curr.right);
			}
			else {
				tree.peek().left = new MsgTree(encodingString.charAt(i));
				tree.push(tree.peek().left);
			}
						
		}

		left = root.left;
		right = root.right;
		payloadChar = root.payloadChar;
		
	}
	
	/**
	 * Constructs a single node with null parents
	 * and a given payloadChar
	 * @param payloadChar this node's character
	 */
	public MsgTree(char payloadChar) {
		this.payloadChar = payloadChar;
		left = null;
		right = null;
	}
	
	/**
	 * Performs a preorder traversal of the binary tree and prints
	 * out every character, or every leaf node, and the path it took
	 * to get there, where left is represented by '0' and right is 
	 * represented by '1'
	 * @param root the root of the binary tree
	 * @param codes the path of 0s and 1s
	 */
	public static void printCodes(MsgTree root, String codes) {
		
		if(root == null) {
			return;
		}
		if(root.left == null && root.right == null) {
			if(root.payloadChar == '\n') {
				System.out.println("   " + "\\n" + "      " + codes);
			}
			else if(root.payloadChar == '\s') {
				System.out.println("   " + "\\s" + "      " + codes);
			}
			else {
				System.out.println("   " + root.payloadChar + "       " + codes);
			}
		}				
		printCodes(root.left, codes + 0);
		printCodes(root.right, codes + 1);		
	}
	
	
	/**
	 * Traverses the binary tree based on a given string of codes
	 * that tell where to go where a 0 means go to the left node
	 * and a 1 means go to the right node. After finding the desired
	 * leaf node, the payload character is printed.
	 * @param codes the binary tree
	 * @param msg the string of 0s and 1s
	 */
	public static void decode(MsgTree codes, String msg) {
		MsgTree root = codes;
		for(int i=0; i<msg.length(); i++) {
			if(codes.left != null && codes.right != null) {
				if(msg.charAt(i) == '0') {
					codes = codes.left;
				}
				else {
					codes = codes.right;
				}
			}
			else if(codes.left == null && codes.right == null){
				System.out.print(codes.payloadChar);
				codes = root;
				i--;
			}			
		}
		System.out.println(codes.payloadChar);		
	}
}
