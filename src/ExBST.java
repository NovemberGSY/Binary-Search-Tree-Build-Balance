/* Binary Search Tree - Search and Operate
 * The projec accepts an input file as a command line argument, reads and processes the file.
 * As the file is pre-determined, the first character operator is read, followed by the node input.
 * Using the linked structure, the operations are performed to Insert(I), Find(F), Remove (R), Display (D), Empty (E).
 * Error checking is performed to flag redundant insert, removal of invalid node.
 * Tree display is inspired by stackoverflow solution
 */

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.Scanner;

/**
 *@author: Ashwin Ravishankar
 */
public class ExBST {
    public static void main(String[] args) throws FileNotFoundException {
        
        FileReader fileRead = new FileReader(args[0]);
        char[] operations = {'I', 'F', 'R', 'D', 'E'};
        Scanner inputRead = new Scanner (fileRead);
        String[] parts = new String[2];
        String[][] treeList = new String[1001][3];
        String inputLine ="", number="";
        displayHeader();
        int spaceCount=0,treeListCount=0, totalTreeCount=0, treeDisplayCount=0, emptyChecker=0;
        char ch;
        while (inputRead.hasNext()){
            inputLine = inputRead.nextLine();
            spaceCount=0; 
            number="";
            for (int i=0; i<inputLine.length();i++) {
                ch = inputLine.charAt(i);
                if(isOperation(ch)) {
                    parts[0] = String.valueOf(ch);
                    spaceCount=0; 
                    number="";
                }
                if(Character.isWhitespace(ch)) {
                    spaceCount++;
                }
                if(Character.isDigit(ch)) {
                    number+=ch;
                    spaceCount=0;
                    
                }
                if((ch=='/')|| spaceCount>3) {
                    parts[1]=number;
                    break;
                }
                if((i==inputLine.length()-1) && number!= "") {
                    parts[1]=number;
                    break;
                }
                
            } 
            treeList[treeListCount][0]=parts[0];
            treeList[treeListCount][1]=parts[1];
            treeListCount++; parts[0]=""; parts[1]="";
        }
        
        BinarySearchTree bstTree = new BinarySearchTree();
        for (int i=0; i<treeListCount; i++) {
            char chOpr = treeList[i][0].charAt(0);
            switch(chOpr){
                case 'I':
                    if((bstTree.Find(Integer.valueOf(treeList[i][1]))) == -1) {
                        displayMessageFormatiing("Insert Node = " +treeList[i][1], treeList[i][1]+ " Inserted");
                        bstTree.Insert(Integer.valueOf(treeList[i][1]));
                    }
                    else 
                        displayMessageFormatiing("Insert Node = " +treeList[i][1], treeList[i][1] + " already present in tree. Invalid Insert.");
                    emptyChecker=0;
                    break;
                case 'F':
                    if((bstTree.Find(Integer.valueOf(treeList[i][1]))) != -1) {
                        //if(Objects.equals(Integer.valueOf(bstTree.Find(Integer.valueOf(treeList[i][1])).toString()), Integer.valueOf(treeList[i][1].toString()))) {
                            displayMessageFormatiing("Finding Node = " +treeList[i][1], treeList[i][1] + " is present");
                        //}
                    }
                    else {
                        displayMessageFormatiing("Insert Node = " +treeList[i][1], treeList[i][1] + " is not found");
                    }
                    emptyChecker=0;
                    break;
                case 'R':
                    if((bstTree.Find(Integer.valueOf(treeList[i][1]))) != -1) {
                        displayMessageFormatiing("Delete Node = " +treeList[i][1], treeList[i][1]+ " Deleted");
                        bstTree.deleteKey(Integer.valueOf(treeList[i][1]));
                    }
                    else 
                        displayMessageFormatiing("Delete Node = " +treeList[i][1], treeList[i][1] + " not present in tree. Invalid Delete.");
                    emptyChecker=0;
                    break;
                case 'D':
                    treeDisplayCount++;
                    displayMessageFormatiing("Display Tree", "Tree is Displayed");
                    bstTree.DisplayTree();
                    displayHeader();
                    emptyChecker=0;
                    break;
                case 'E':
                    totalTreeCount++;
                    displayMessageFormatiing("Empty / Reset Tree", "Tree is reset");
                    bstTree = new BinarySearchTree();
                    //bstTree.makeEmpty();    
                    emptyChecker=1;
                    break;    
                default:
                    break;
            }
        }
       
        for (int i=0; i<treeListCount; i++) {
            //System.out.println(treeList[i][0] + "   " + treeList[i][1]);
        }
        
        System.out.println("\n\n");
        System.out.println("Total count of trees BUILT: " + (emptyChecker==0 ? ++totalTreeCount : emptyChecker));
        System.out.println("Total count of trees DISPLAYED: " + treeDisplayCount);
        System.out.println("End of processing line\n\n");
    }
    
    public static void displayMessageFormatiing (String operation, String message) {
        System.out.format("%25s \t %45s\n", operation, message);        
    }
    
    public static void displayHeader() {
        System.out.format("%25s \t %45s\n", "Echo of Transaction", "Result of transaction");
        System.out.format("%25s \t %45s\n", "-----------------------------", "---------------------------");
    }
    
    public static boolean isOperation(char ch) {
        return ch == 'I' || ch == 'F' || ch == 'R' || ch == 'D' || ch == 'E';
    }   
}

//Reference - Weiss 19.6
class BinaryNode {
    BinaryNode(int theElement){ 
        element = theElement;
        left = right = null;  
        height = 1;
    }
    int element;
    BinaryNode left;
    BinaryNode right;
    int height;
}

//Reference - Weiss 19.6
class BinarySearchTree {
    
    public int height(BinaryNode N) {
		if (N == null)
			return 0;

		return N.height;
	}
    
    public BinarySearchTree() {
        root = null;
    }
    
    public void Insert(int x){
        root = Insert(x, root);
    }
    
    public int Find(int x ) {
        return (int) ElementAt(Find( x, root ) ); 
    }
    
    public void makeEmpty( ) {
        root = null; 
    }
    
    public int TreeHeight () {
        return maxLevel(root); 
    }
    
    public void DisplayTree() {
        System.out.println("\n\n");
        //printNode(root);
        //printLevelOrder(root);
        printBinaryTree(root, 0);
        System.out.println("\n\n");
    }
    
    public static void printBinaryTree(BinaryNode root, int level){
        if(root==null)
             return;
        printBinaryTree(root.right, level+1);
        if(level!=0){
            for(int i=0;i<level-1;i++)
                System.out.print("|\t");
                System.out.println("|-------"+root.element);
        }
        else
            System.out.println(root.element);
        printBinaryTree(root.left, level+1);
        
    }    
    
    void PrintSpace(int n) {
        for (int i = 0; i < n; ++i)
            System.out.print("null");
    }
    
    void deleteKey(int key) {
	root = deleteRec(root, key);
    }
    
    BinaryNode deleteRec(BinaryNode root, int key) {
        /* Base Case: If the tree is empty */
        if (root == null) return root;

        /* Otherwise, recur down the tree */
        if ( key <  root.element)
            root.left = deleteRec(root.left, key);
        else if ( key > root.element)
            root.right = deleteRec(root.right, key);

        // if key is same as root's key, then This is the node
        // to be deleted
        else
        {
            // node with only one child or no child
            if (root.left == null)
                return root.right;
            else if (root.right == null)
                return root.left;

            // node with two children: Get the inorder successor (smallest
            // in the right subtree)
            root.element = minValue(root.right);

            // Delete the inorder successor
            root.right = deleteRec(root.right, root.element);
        }

        return root;
    }
    
    int minValue(BinaryNode root) {
        int minv = root.element;
        while (root.left != null)
        {
                minv = root.left.element;
                root = root.left;
        }
        return minv;
    }
    
    public static <T extends Comparable<?>> void printNode(BinaryNode root) {
        int maxLevel = maxLevel(root);

        printNodeInternal(Collections.singletonList(root), 1, maxLevel);
    }

    private static <T extends Comparable<?>> void printNodeInternal(List<BinaryNode> nodes, int level, int maxLevel) {
        if (nodes.isEmpty() || isAllElementsNull(nodes))
            return;

        int floor = maxLevel - level;
        int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
        int firstSpaces = (int) Math.pow(2, (floor)) - 1;
        int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

        printWhitespaces(firstSpaces);

        List<BinaryNode> newNodes = new ArrayList<BinaryNode>();
        for (BinaryNode node : nodes) {
            if (node != null) {
                System.out.print(node.element);
                newNodes.add(node.left);
                newNodes.add(node.right);
            } else {
                newNodes.add(null);
                newNodes.add(null);
                System.out.print(" ");
            }

            printWhitespaces(betweenSpaces);
        }
        System.out.println("");

        for (int i = 1; i <= endgeLines; i++) {
            for (int j = 0; j < nodes.size(); j++) {
                printWhitespaces(firstSpaces - i);
                if (nodes.get(j) == null) {
                    printWhitespaces(endgeLines + endgeLines + i + 1);
                    continue;
                }
                
                if (nodes.get(j).left != null)
                    System.out.print("/");
                else
                    printWhitespaces(1);

                printWhitespaces(i + i - 1);

                if (nodes.get(j).right != null)
                    System.out.print("\\");
                else
                    printWhitespaces(1);

                printWhitespaces(endgeLines + endgeLines - i);
            }

            System.out.println("");
        }

        printNodeInternal(newNodes, level + 1, maxLevel);
    }

    private static void printWhitespaces(int count) {
        for (int i = 0; i < count; i++)
            System.out.print(" ");
    }

    private static <T extends Comparable<?>> int maxLevel(BinaryNode node) {
        if (node == null)
            return 0;

        return Math.max(maxLevel(node.left), maxLevel(node.right)) + 1;
    }

    private static <T> boolean isAllElementsNull(List<T> list) {
        for (Object object : list) {
            if (object != null)
                return false;
        }

        return true;
    }
    
    private int ElementAt( BinaryNode t ) {
        return t == null ? -1 : t.element;
    }
    
    private BinaryNode Find( int x, BinaryNode t ) {
        while( t != null )  {
            if(x < t.element)
                t = t.left;
            else if(x > t.element)
                t = t.right;
            else
                return t; // Match
            }
            return null; // Not found
    }
    
    public BinaryNode rightRotate(BinaryNode y) {
		BinaryNode x = y.left;
		BinaryNode T2 = x.right;

		// Perform rotation
		x.right = y;
		y.left = T2;

		// Update heights
		y.height = max(height(y.left), height(y.right)) + 1;
		x.height = max(height(x.left), height(x.right)) + 1;

		// Return new root
		return x;
	}
    
    public BinaryNode leftRotate(BinaryNode x) {
		BinaryNode y = x.right;
		BinaryNode T2 = y.left;

		// Perform rotation
		y.left = x;
		x.right = T2;

		// Update heights
		x.height = max(height(x.left), height(x.right)) + 1;
		y.height = max(height(y.left), height(y.right)) + 1;

		// Return new root
		return y;
	}
    
    int getBalance(BinaryNode N) {
		if (N == null)
			return 0;

		return height(N.left) - height(N.right);
	}
    
    int max(int a, int b) {
	return (a > b) ? a : b;
    }
    
    private BinaryNode Insert (int x, BinaryNode t) {
        if(t == null)
            t = new BinaryNode (x);
        else if (x < t.element) {
            t.left = Insert(x, t.left);
        }
        else if (x > t.element) {
        //else if ( x.compareTo(t.element) > 0) {
            t.right = Insert(x, t.right);   
        }
        else {
            return t;
        }
        t.height = 1 + max(height(t.left),height(t.right));
        
        int balance = getBalance(t);
        
        if (balance > 1 && x < t.left.element)
            return rightRotate(t);
        
        if (balance < -1 && x > t.right.element) 
            return leftRotate(t);
        
        if (balance > 1 && x > t.left.element) {
            t.left = leftRotate(t.left);
            return rightRotate(t);
	}
        
        if (balance < -1 && x < t.right.element) {
            t.right = rightRotate(t.right);
            return leftRotate(t);
        }
        
        return t;
    }
    
    public BinaryNode root;
    
    public int treeLevel=0;
}
