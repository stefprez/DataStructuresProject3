DataStructuresProject3
======================

The following is by Bill Sverdlik of Eastern Michigan University
http://www.emunix.emich.edu/~sverdlik/COSC311/COSC311Program3.html

COSC 311
Programming Assignment #3


Threaded Binary Search Trees (Threaded-BST)

Recall in our model of Binary Search Trees (BSTs), the leaf nodes all have null pointers. This seems like a waste of space. It has been suggested that these null pointers can be used to reference something useful. In a threaded binary search tree, the right null pointers of a node are used to point to the node's inorder successor and the left null pointers of nodes are employed to point to the node's inorder predecessor. One advantage threaded threaded BST is that one can do an inorder traversal of the tree without using recursion.

We define a class as follows:

class Node                        
{
        public String data;
        public int recNum;    
        public Node  left;
        public Node right;
        public boolean isRightThread;
        public boolean isLeftThread;

   
........
}

We need the boolean variables  to distinguish a thread from a non-thread link.

What to do:

There are three different versions of this program you may choose from:

There are two versions of this program:

Version 1:
No threads and all output is done recursively. This will be worth 30 out of 50 points. This will be worth 80% of full value.

Version 2:
You employ right handed threads only. Outputting ID, first name or last name data in ascending order MAY NOT employ recursion, but output in reverse order MAY employ recursion. This is worth 50 out of 50 points.

Version 2:
You employ left and right handed threads. No recursion is used at all. This is worth 100% of the points.


Repeat assignment 2, but replace the three linked lists with three threaded binary search trees. You should still be able to output the data in ascending and descending order. YOU MAY NOT EMPLOY RECURSION for output.

PLEASE make sure your program contains the following header information. PLEASE MAKE SURE YOUR HEADER CONTAINS WHICH VERSION OF THIS PROGRAM YOU ARE DOING.

/*


Your Name
Your EID
Program Number x
Version xx

*/

