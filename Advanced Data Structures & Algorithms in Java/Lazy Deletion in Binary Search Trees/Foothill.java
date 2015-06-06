import java.util.*;
import cs_1c.*;

/* Name: Shiv Patel 
Assignment 4 */

class PrintObject<E> implements Traverser<E>
{
   public void visit(E x)
   {
      System.out.print( x + " ");
   }
};

//------------------------------------------------------
public class Foothill
{
   // -------  main --------------
   public static void main(String[] args) throws Exception
   {
      int k;
      FHlazySearchTree<Integer> searchTree = new FHlazySearchTree<Integer>();
      PrintObject<Integer> intPrinter = new PrintObject<Integer>();

      searchTree.traverse(intPrinter);

      System.out.println( "\ninitial size: " + searchTree.size() );
      searchTree.insert(50);
      searchTree.insert(20);
      searchTree.insert(30);
      searchTree.insert(70);
      searchTree.insert(10);
      searchTree.insert(60);

      System.out.println( "After populating -- traversal and sizes: ");
      searchTree.traverse(intPrinter);
      System.out.println( "\ntree 1 size: " + searchTree.size() 
            + "  Hard size: " + searchTree.sizeHard() );

      System.out.println( "Collecting garbage on new tree - should be " +
            "no garbage." );
      searchTree.collectGarbage();
      System.out.println( "tree 1 size: " + searchTree.size() 
            + "  Hard size: " + searchTree.sizeHard() );

      // test assignment operator
      FHlazySearchTree<Integer> searchTree2 
      = (FHlazySearchTree<Integer>)searchTree.clone();

      System.out.println( "\n\nAttempting 1 removal: ");
      if (searchTree.remove(20))
         System.out.println( "removed " + 20 );
      System.out.println( "tree 1 size: " + searchTree.size() 
            + "  Hard size: " + searchTree.sizeHard() );

      System.out.println( "Collecting Garbage - should clean 1 item. " );
      searchTree.collectGarbage();
      System.out.println( "tree 1 size: " + searchTree.size() 
            + "  Hard size: " + searchTree.sizeHard() );

      System.out.println( "Collecting Garbage again - no change expected. " );
      searchTree.collectGarbage();
      System.out.println( "tree 1 size: " + searchTree.size() 
            + "  Hard size: " + searchTree.sizeHard() );

      // test soft insertion and deletion:

      System.out.println( "Adding 'hard' 22 - should see new sizes. " );
      searchTree.insert(22);
      searchTree.traverse(intPrinter);
      System.out.println( "\ntree 1 size: " + searchTree.size() 
            + "  Hard size: " + searchTree.sizeHard() );

      System.out.println( "\nAfter soft removal. " );
      searchTree.remove(22);
      searchTree.traverse(intPrinter);
      System.out.println( "\ntree 1 size: " + searchTree.size() 
            + "  Hard size: " + searchTree.sizeHard() );

      System.out.println( "Repeating soft removal. Should see no change. " );
      searchTree.remove(22);
      searchTree.traverse(intPrinter);
      System.out.println( "\ntree 1 size: " + searchTree.size() 
            + "  Hard size: " + searchTree.sizeHard() );

      System.out.println( "Soft insertion. Hard size should not change. " );
      searchTree.insert(22);
      searchTree.traverse(intPrinter);
      System.out.println( "\ntree 1 size: " + searchTree.size() 
            + "  Hard size: " + searchTree.sizeHard() );

      System.out.println( "\n\nAttempting 100 removals: " );
      for (k = 100; k > 0; k--)
      {
         if (searchTree.remove(k))
            System.out.println( "removed " + k );
      }
      //      searchTree.collectGarbage();

      System.out.println( "\nsearch_tree now:");
      searchTree.traverse(intPrinter);
      System.out.println( "\ntree 1 size: " + searchTree.size() 
            + "  Hard size: " + searchTree.sizeHard() );

      searchTree2.insert(500);
      searchTree2.insert(200);
      searchTree2.insert(300);
      searchTree2.insert(700);
      searchTree2.insert(100);
      searchTree2.insert(600);
      System.out.println( "\nsearchTree2:\n" );
      searchTree2.traverse(intPrinter);
      System.out.println( "\ntree 2 size: " + searchTree2.size() 
            + "  Hard size: " + searchTree2.sizeHard() );
   }
}

//------------------------------------------------------
class FHlazySearchTree<E extends Comparable< ? super E > >
implements Cloneable
{
   protected int mSize;
   protected int mSizeHard;
   protected FHlazySTNode<E> mRoot;

   public FHlazySearchTree() { clear(); }
   public boolean empty() { return (mSize == 0); }
   public int size() { return mSize; }
   public void clear() { mSize = 0; mSizeHard = 0; mRoot = null; }
   public int showHeight() { return findHeight(mRoot, -1); }
   public int sizeHard() { return mSizeHard; }

   public E findMin()
   {
      if (mRoot == null)
         throw new NoSuchElementException();
      return findMin(mRoot).data;
   }

   public E findMax()
   {
      if (mRoot == null)
         throw new NoSuchElementException();
      return findMax(mRoot).data;
   }

   public E find( E x )
   {
      FHlazySTNode<E> resultNode;
      resultNode = find(mRoot, x);
      if (resultNode == null)
         throw new NoSuchElementException();
      if (resultNode.deleted == true)
         throw new NoSuchElementException();
      return resultNode.data;
   }
   public boolean contains(E x)  { return find(mRoot, x) != null; }

   public boolean insert( E x )
   {
      int oldSize = mSize;
      mRoot = insert(mRoot, x);
      return (mSize != oldSize);
   }

   public boolean remove( E x )
   {
      int oldSize = mSize;
      remove(mRoot, x);
      return (mSize != oldSize);
   }

   public void collectGarbage() {
      mRoot = collectGarbage(mRoot);
   }

   public < F extends Traverser<? super E > >
   void traverse(F func)
   {
      traverse(func, mRoot);
   }

   public Object clone() throws CloneNotSupportedException
   {
      FHlazySearchTree<E> newObject = (FHlazySearchTree<E>)super.clone();
      newObject.clear();  // can't point to other's data

      newObject.mRoot = cloneSubtree(mRoot);
      newObject.mSize = mSize;
      newObject.mSizeHard = mSizeHard;

      return newObject;
   }

   // private helper methods ----------------------------------------
   protected FHlazySTNode<E> findMin( FHlazySTNode<E> root )
   {
      if (root == null)
         return null;
      if (root.lftChild == null && root.deleted == true)
         return null;
      if (root.lftChild == null)
         return root;
      FHlazySTNode<E> min = findMin(root.lftChild);
      return (min==null)? root : min;
   }

   protected FHlazySTNode<E> findMax( FHlazySTNode<E> root )
   {
      if (root == null)
         return null;
      if (root.rtChild == null && root.deleted == true)
         return null;
      if (root.rtChild == null)
         return root;
      FHlazySTNode<E> max = findMax(root.rtChild);
      return (max==null)? root : max;
   }

   protected FHlazySTNode<E> insert( FHlazySTNode<E> root, E x )
   {
      int compareResult;  // avoid multiple calls to compareTo()

      if (root == null)
      {
         mSize++;
         mSizeHard++;
         return new FHlazySTNode<E>(x, null, null);
      }

      compareResult = x.compareTo(root.data);
      if ( compareResult < 0 )
         root.lftChild = insert(root.lftChild, x);
      else if ( compareResult > 0 )
         root.rtChild = insert(root.rtChild, x);
      else if ( compareResult == 0 ) 
      {
         root.deleted = false;
         mSize++;
      }

      return root;
   }

   protected void remove( FHlazySTNode<E> root, E x  )
   {
      int compareResult;  // avoid multiple calls to compareTo()

      if (root == null)
         return;

      compareResult = x.compareTo(root.data);
      if ( compareResult < 0 )
         remove(root.lftChild, x);
      else if ( compareResult > 0 )
         remove(root.rtChild, x);

      // found the node
      else
      {
         if (root.deleted == false) 
         {
            root.deleted = true;
            mSize--;
         }
      }
   }

   protected <F extends Traverser<? super E>>
   void traverse(F func, FHlazySTNode<E> treeNode)
   {
      if (treeNode == null)
         return;

      traverse(func, treeNode.lftChild);
      if ( treeNode.deleted == false) 
      {
         func.visit(treeNode.data);
      }
      traverse(func, treeNode.rtChild);
   }

   protected FHlazySTNode<E> find( FHlazySTNode<E> root, E x )
   {
      int compareResult;  // avoid multiple calls to compareTo()

      if (root == null)
         return null;

      compareResult = x.compareTo(root.data);
      if (compareResult < 0)
         return find(root.lftChild, x);
      if (compareResult > 0)
         return find(root.rtChild, x);
      if (compareResult == 0 && root.deleted == true) 
      {
         return null;
      }
      return root;   // found
   }

   protected FHlazySTNode<E> cloneSubtree(FHlazySTNode<E> root)
   {
      FHlazySTNode<E> newNode;
      if (root == null)
         return null;

      // does not set myRoot which must be done by caller
      newNode = new FHlazySTNode<E>
      (
            root.data,
            cloneSubtree(root.lftChild),
            cloneSubtree(root.rtChild)
            );
      return newNode;
   }

   protected int findHeight( FHlazySTNode<E> treeNode, int height )
   {
      int leftHeight, rightHeight;
      if (treeNode == null)
         return height;
      height++;
      leftHeight = findHeight(treeNode.lftChild, height);
      rightHeight = findHeight(treeNode.rtChild, height);
      return (leftHeight > rightHeight)? leftHeight : rightHeight;
   }

   private FHlazySTNode<E> collectGarbage(FHlazySTNode<E> root)
   {
      // pre-order search for deleted nodes, call removeHard on deleted nodes
      if (root.lftChild != null) 
      {
         root.lftChild = collectGarbage(root.lftChild);
      }    
      if (root.rtChild != null) 
      {
         root.rtChild = collectGarbage(root.rtChild);
      }      
      if (root.deleted == true) 
      {
         root = removeHard(root, root.data);
      }

      return root;
   }

   private FHlazySTNode<E> removeHard(FHlazySTNode<E> root, E x)
   {
      if (root == null)
         return null;

      if (root.lftChild != null && root.rtChild != null)
      {
         root.data = findMin(root.rtChild).data;
         root.rtChild = removeHard(root.rtChild, root.data);
         root.deleted = false;
      }
      else
      {
         root =
               (root.lftChild != null)? root.lftChild : root.rtChild;
         mSizeHard--;
      }
      return root;
   }
}

//------------------------------------------------------
class FHlazySTNode<E extends Comparable< ? super E > >
{
   // use public access so the tree or other classes can access members 
   public FHlazySTNode<E> lftChild, rtChild;
   public E data;
   public FHlazySTNode<E> myRoot;  // needed to test for certain error
   public boolean deleted;

   public FHlazySTNode(E d, FHlazySTNode<E> lft, FHlazySTNode<E> rt)
   {
      lftChild = lft; 
      rtChild = rt;
      data = d;
      deleted = false;
   }

   public FHlazySTNode()
   {
      this(null, null, null);
   }

   // function stubs -- for use only with AVL Trees when we extend
   public int getHeight() { return 0; }
   boolean setHeight(int height) { return true; }
}


/*------------------- paste of run from Console window -----------------------

initial size: 0
After populating -- traversal and sizes: 
10 20 30 50 60 70 
tree 1 size: 6  Hard size: 6
Collecting garbage on new tree - should be no garbage.
tree 1 size: 6  Hard size: 6


Attempting 1 removal: 
removed 20
tree 1 size: 5  Hard size: 6
Collecting Garbage - should clean 1 item. 
tree 1 size: 5  Hard size: 5
Collecting Garbage again - no change expected. 
tree 1 size: 5  Hard size: 5
Adding 'hard' 22 - should see new sizes. 
10 22 30 50 60 70 
tree 1 size: 6  Hard size: 6

After soft removal. 
10 30 50 60 70 
tree 1 size: 5  Hard size: 6
Repeating soft removal. Should see no change. 
10 30 50 60 70 
tree 1 size: 5  Hard size: 6
Soft insertion. Hard size should not change. 
10 22 30 50 60 70 
tree 1 size: 6  Hard size: 6


Attempting 100 removals: 
removed 70
removed 60
removed 50
removed 30
removed 22
removed 10

search_tree now:

tree 1 size: 0  Hard size: 6

searchTree2:

10 20 30 50 60 70 100 200 300 500 600 700 
tree 2 size: 12  Hard size: 12

--------------------------------------------------------------------- */


