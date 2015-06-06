import cs_1c.*;

/* Name: Shiv Patel 
Assignment 5 */

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
      FHsplayTree<Integer> searchTree = new FHsplayTree<Integer>();
      PrintObject<Integer> intPrinter = new PrintObject<Integer>();

      searchTree.traverse(intPrinter);

      System.out.println( "Initial size: " + searchTree.size() );
      for (k = 1; k <= 32; k++)
         searchTree.insert(k);
      System.out.println( "New size: " + searchTree.size() );

      System.out.println( "\nTraversal");
      searchTree.traverse(intPrinter);
      System.out.println();

      for (k = -1; k < 10; k++)
      {
         //searchTree.contains(k); //alternative to find() - diff error return
         try
         {
            searchTree.find(k);
         }
         catch( Exception e )
         {
            System.out.println( " oops " );
         }
         System.out.println( "splay " + k + " --> root: " 
               + searchTree.showRoot() 
               + " height: " + searchTree.showHeight() );
      }
   }
}

//------------------------------------------------------
class FHsplayTree<E extends Comparable< ? super E > >
extends FHsearch_tree<E> 
{
   public FHsplayTree() 
   {
      super();
   }

   public boolean insert( E x ) 
   {

      FHs_treeNode<E> node;

      if( empty() ) 
      {
         super.insert(x);
         return true;
      } else {
         mRoot = splay(mRoot, x);
      }

      if ( x.compareTo(mRoot.data) < 0 ) 
      {
         node = new FHs_treeNode<E>(x, mRoot.lftChild, mRoot);
         mRoot = node;
         mSize++;
         return true;
      } else if ( x.compareTo(mRoot.data) > 0 ) {
         node = new FHs_treeNode<E>(x, mRoot, mRoot.rtChild);
         mRoot = node;
         mSize++;
         return true;
      } else {
         return false;
      }
   }

   public boolean remove( E x ) 
   {
      FHs_treeNode<E> newRoot;

      if( empty() ) 
      {
         return false;
      } else {
         mRoot = splay(mRoot, x);
      }

      if( x.compareTo(mRoot.data) != 0 ) 
      {
         return false;
      }

      if (mRoot.lftChild == null) 
      {
         newRoot = mRoot.rtChild;
      } else {
         newRoot = mRoot.lftChild;
         newRoot = splay(newRoot, x);
         newRoot.rtChild = mRoot.rtChild;
      }

      mRoot = newRoot;
      mSize--;
      return true;
   }

   public E showRoot() 
   {
      if( empty() ) 
      {
         return null;
      } else {
         return mRoot.data;
      }
   }

   protected FHs_treeNode<E> splay( FHs_treeNode<E> root, E x ) 
   {
      FHs_treeNode<E> rightTree = null;
      FHs_treeNode<E> leftTree = null;
      FHs_treeNode<E> rightTreeMin = null;
      FHs_treeNode<E> leftTreeMax = null;

      while( root != null ) 
      {
         if( x.compareTo(root.data) < 0  ) 
         {
            if( root.lftChild == null ) 
            {
               break;
            }

            if( x.compareTo(root.lftChild.data) < 0 ) 
            {
               root = rotateWithLeftChild(root);
               if(root.lftChild == null) 
               {
                  break;
               }
            }

            if( rightTree == null ) 
            {
               rightTree = this.cloneSubtree(root);
               rightTreeMin = rightTree;
            }

            rightTreeMin.lftChild = root;
            rightTreeMin = rightTreeMin.lftChild;

            root = root.lftChild;
         } else if( x.compareTo(root.data) > 0) {
            if(root.rtChild == null) {
               break;
            }

            if( x.compareTo(root.rtChild.data) > 0) 
            {
               root = rotateWithRightChild(root);
               if(root.rtChild == null) 
               {
                  break;
               }
            }

            if( leftTree == null ) 
            {
               leftTree = this.cloneSubtree(root);
               leftTreeMax = leftTree;
            }

            leftTreeMax.rtChild = root;
            leftTreeMax = leftTreeMax.rtChild;

            root = root.rtChild;
         } else {
            break;
         }
      }

      if( leftTree != null ) 
      {
         leftTreeMax.rtChild = root.lftChild;
         root.lftChild = leftTree;
      }

      if ( rightTree != null ) 
      {
         rightTreeMin.lftChild = root.rtChild;
         root.rtChild = rightTree;
      }

      return root;
   }

   protected FHs_treeNode<E> rotateWithLeftChild( FHs_treeNode<E> k2 )
   {
      FHs_treeNode<E> k1 = k2.lftChild;
      k2.lftChild = k1.rtChild;
      k1.rtChild = k2;
      return k1;
   }

   protected FHs_treeNode<E> rotateWithRightChild( FHs_treeNode<E> k2 )
   {
      FHs_treeNode<E> k1 = k2.rtChild;
      k2.rtChild = k1.lftChild;
      k1.lftChild = k2;
      return k1;
   }

   protected FHs_treeNode<E> find(FHs_treeNode<E> root, E x) 
   {
      if ( empty() ) 
      {
         return null;
      } else {
         mRoot = splay(root, x);
         if(x.compareTo(mRoot.data) != 0) 
         {
            return null;
         } else {
            return mRoot;
         }
      }
   }
}

/*------------------- paste of run from Console window -----------------------
Initial size: 0
New size: 32

Traversal
1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 
 oops 
splay -1 --> root: 1 height: 17
 oops 
splay 0 --> root: 1 height: 17
splay 1 --> root: 1 height: 17
splay 2 --> root: 2 height: 11
splay 3 --> root: 3 height: 8
splay 4 --> root: 4 height: 8
splay 5 --> root: 5 height: 8
splay 6 --> root: 6 height: 10
splay 7 --> root: 7 height: 12
splay 8 --> root: 8 height: 14
splay 9 --> root: 9 height: 16

--------------------------------------------------------------------- */

