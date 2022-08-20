/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package binarytree;

/**
 *
 * @author Daddy
 */
public class BinaryTreeMain {

    /**
     * @param args the command line arguments
     */
    public static void rec(int i){
         System.out.println(i);if(i>1)
            rec(i-1);
       
    }
    public static void aNewOp(){
        System.out.println("Hello from new op()");
    }
    public static void main(String[] args) {
        BinaryTree bt = new BinaryTree();//(11);
	

	bt.insert( 50);
	bt.insert( 23);
	bt.insert( 71);
	bt.insert( 19);
	bt.insert( 31);
	bt.insert( 55);
	bt.insert( 87);
	bt.insert( 26);
	bt.insert( 37);
	bt.insert( 63);
	bt.insert( 98);
	bt.insert( 25);
	bt.insert( 33);
	bt.insert( 41);
	bt.insert( 92);
	bt.insert( 99);
	

	System.out.println("Tree size is " + bt.size());
	System.out.println ( "Tree depth is " + bt.maxDepth());
	System.out.println ( "Is the tree a BST: " + (bt.isBST() ? "true" : "false"));
	System.out.println ( "In order " ) ;
	bt.printInOrder();
	System.out.println ( "==========================");
	System.out.println ( "Post order ");
	bt.printPostOrder();
	System.out.println ("Pre Order " );
	bt.printPreOrder();
      
        rec(3);
    }
    
}
