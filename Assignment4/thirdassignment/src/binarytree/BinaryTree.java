/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package binarytree;

public class BinaryTree {
  
    
    
    
   private Node root;
   
   public BinaryTree(){
      
	root = null;
    }
	
    public void insert(int val){
        root=insert(root, val);
    }

    public void printPostOrder(){
            printPostOrder(root);
    }

    public void printInOrder(){
            printInOrder(root);
    }

    public void printPreOrder(){
            printPreOrder(root);
    }

    public int size(){
            return size(root);
    }

    public int maxDepth(){
            return maxDepth(root);
    }

    public boolean isBST(){
            return isBST(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private	boolean isBST(Node node, int min, int max){
        
        if(node == null)
        {    return true;}
      
        if(node.data<min || node.data>max) 
        {   return false;}
       
        return isBST(node.left, min, node.data) &&
                   isBST(node.right, node.data, max);
    }
    private Node NewNode(int val){
        Node root = new Node();
        root.data = val;
        root.left = null;
        root.right = null;
        return root;
	}
    private void printInOrder(Node node){
        
        if(node == null) {
           return;
        }
        printInOrder(node.left);
        System.out.println(node.data);
        printInOrder(node.right);
	}

    private void printPostOrder(Node node){
       
        if(node == null){
            return;
        }
        printInOrder(node.left);
        printInOrder(node.right);
        System.out.println ( node.data );
	}
    private void printPreOrder(Node node) {
       
        if(node==null){
           return;
        }
        System.out.println(node.data);
        printPreOrder(node.left);
        printPreOrder(node.right);
	}


    private int size(Node node){
       
        if(node==null){
           return 0;
        }
        else {
           return (size(node.left) + 1 + size(node.right));
        }
    }

    private Node insert(Node node, int val){
      
	if(node==null){
            return NewNode(val);
        }
	
        if(val <= node.data ){
           node.left = insert(node.left, val);
        }
        else {
           node.right = insert(node.right, val);
        }
        
        return node;
	}
    private int maxDepth(Node node){
        
        if(node==null){
            return 0;
        }
        else
        {   
            int leftDepth = maxDepth(node.left);
            int rightDepth = maxDepth(node.right);
            
            if(leftDepth>rightDepth){
                return leftDepth+1;
            }
            else {
                return rightDepth+1;
            }
        }
    }
}
