import java.util.ListIterator;
import cs_1c.*;

/* Name: Shiv Patel 
Assignment 2 */

public class Foothill
{
   final static int MAT_SIZE = 100000;
   // -------  main --------------
   public static void main(String[] args) throws Exception
   {
      // 100000 x 100000 filled with 0
      int k;

      SparseMat<Double> mat 
      = new SparseMat<Double>(MAT_SIZE, MAT_SIZE, 0.); 

      // test mutators
      for (k = 0; k < 10; k++)
      {
         mat.set(k, k, k*1.);
         mat.set(4, k, k*10.);
         mat.set(k, 4, -k*10.);
      }
      mat.showSubSquare(0, 12);
      System.out.println();

      SparseMat<Double> mat2 = (SparseMat<Double>)mat.clone();

      for (k = 0; k < 10; k++)
      {
         mat.set(k, k, 1.);
         mat.set(4, k, 10.);
         mat.set(k, 4, -10.);
      }

      mat.showSubSquare(0, 12);
      System.out.println();
      mat2.showSubSquare(0, 12);

   }
}


//--------------- Class SparseMat ---------------
class SparseMat<E> implements Cloneable
{
   protected int rowSize, colSize;
   protected E defaultVal;
   protected FHarrayList < FHlinkedList< MatNode > > rows;

   SparseMat( int numRows, int numCols, E defaultVal)
   {
      //do range check and create matrix.
      if (numRows >= 1 && numCols >= 1) 
      {
         this.rowSize = numRows;
         this.colSize = numCols;
         this.defaultVal = defaultVal;
         allocateEmptyMatrix();
      } else {
         this.rowSize = 10;
         this.colSize = 10;
         this.defaultVal = defaultVal;
         allocateEmptyMatrix();
      }

   }

   void allocateEmptyMatrix()
   {
      rows = new FHarrayList<FHlinkedList< MatNode >>(rowSize);

      for (int k = 0; k < rowSize; k++) 
      {
         rows.add(new FHlinkedList<MatNode>());
      }
   }

   E get(int r, int c)
   {
      ListIterator<MatNode> p;

      if(r >= rowSize || c >= colSize || r < 0 || c < 0)                     
         throw new IndexOutOfBoundsException();    

      for(p = this.rows.get(r).listIterator(); p.hasNext(); )
      {
         if(p.next().col == c)
            return p.previous().data;
      }
      return defaultVal;
   }

   boolean set(int r, int c, E x)
   {
      ListIterator<MatNode> p;

      //returning false without an exception if bounds are violated
      if(r >= rowSize || c >= colSize || r < 0 || c < 0)                     
         return false;

      if(x == defaultVal)
      {
         for(p = this.rows.get(r).listIterator(); p.hasNext(); )
         {
            if(p.next().col == c)
            {
               rows.get(r).remove(p.previous());
               return true;
            }
         }
         return true;

      }else {
         for(p = this.rows.get(r).listIterator(); p.hasNext(); )
         {
            if(p.next().col == c)
            {
               p.previous().data = x;
               return true;
            }            
         }
         rows.get(r).add(new MatNode(c, x));
         return true;
      }

   }

   void clear()
   {
      for(int k = 0; k < rows.size(); k++)
      {
         rows.get(k).clear();
      }
   }

   void showSubSquare(int start, int size) 
   {
      //return if bounds are violated.
      if(start < 0 || size < 0 || 
            (start + size) > rowSize || (start + size) > colSize)
         return;

      size = (start + size);
      for(int row =  start; row < size; row++)
      {
         for(int col = start; col  < size; col++)
         {
            System.out.format("%5s ", get(row, col));
         }
         System.out.println();
      }
   }

   public Object clone() throws CloneNotSupportedException
   {
      int linkedListSize;
      SparseMat<E> newObject = (SparseMat<E>)super.clone();
      newObject.allocateEmptyMatrix();

      for(int k = 0; k < this.rows.size(); k++)
      {
         linkedListSize = (rows.get(k).size());
         for(int j = 0; j < linkedListSize; j++)
         {
            newObject.rows.get(k).add((MatNode)this.rows.get(k).get(j).clone());
         }
      }
      return (Object) newObject;
   }

   protected class MatNode implements Cloneable
   {
      public int col;
      public E data;

      // we need a default constructor for lists
      MatNode()
      {
         col = 0;
         data = null;
      }

      MatNode(int cl, E dt)
      {
         col = cl;
         data = dt;
      }

      public Object clone() throws CloneNotSupportedException
      {
         // shallow copy
         MatNode newObject = (MatNode)super.clone();
         return (Object) newObject;
      }
   };


}


/*------------------- paste of run from Console window -----------------------
  0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0 
  0.0   1.0   0.0   0.0 -10.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0 
  0.0   0.0   2.0   0.0 -20.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0 
  0.0   0.0   0.0   3.0 -30.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0 
  0.0  10.0  20.0  30.0 -40.0  50.0  60.0  70.0  80.0  90.0   0.0   0.0 
  0.0   0.0   0.0   0.0 -50.0   5.0   0.0   0.0   0.0   0.0   0.0   0.0 
  0.0   0.0   0.0   0.0 -60.0   0.0   6.0   0.0   0.0   0.0   0.0   0.0 
  0.0   0.0   0.0   0.0 -70.0   0.0   0.0   7.0   0.0   0.0   0.0   0.0 
  0.0   0.0   0.0   0.0 -80.0   0.0   0.0   0.0   8.0   0.0   0.0   0.0 
  0.0   0.0   0.0   0.0 -90.0   0.0   0.0   0.0   0.0   9.0   0.0   0.0 
  0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0 
  0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0 

  1.0   0.0   0.0   0.0 -10.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0 
  0.0   1.0   0.0   0.0 -10.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0 
  0.0   0.0   1.0   0.0 -10.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0 
  0.0   0.0   0.0   1.0 -10.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0 
 10.0  10.0  10.0  10.0 -10.0  10.0  10.0  10.0  10.0  10.0   0.0   0.0 
  0.0   0.0   0.0   0.0 -10.0   1.0   0.0   0.0   0.0   0.0   0.0   0.0 
  0.0   0.0   0.0   0.0 -10.0   0.0   1.0   0.0   0.0   0.0   0.0   0.0 
  0.0   0.0   0.0   0.0 -10.0   0.0   0.0   1.0   0.0   0.0   0.0   0.0 
  0.0   0.0   0.0   0.0 -10.0   0.0   0.0   0.0   1.0   0.0   0.0   0.0 
  0.0   0.0   0.0   0.0 -10.0   0.0   0.0   0.0   0.0   1.0   0.0   0.0 
  0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0 
  0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0 

  0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0 
  0.0   1.0   0.0   0.0 -10.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0 
  0.0   0.0   2.0   0.0 -20.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0 
  0.0   0.0   0.0   3.0 -30.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0 
  0.0  10.0  20.0  30.0 -40.0  50.0  60.0  70.0  80.0  90.0   0.0   0.0 
  0.0   0.0   0.0   0.0 -50.0   5.0   0.0   0.0   0.0   0.0   0.0   0.0 
  0.0   0.0   0.0   0.0 -60.0   0.0   6.0   0.0   0.0   0.0   0.0   0.0 
  0.0   0.0   0.0   0.0 -70.0   0.0   0.0   7.0   0.0   0.0   0.0   0.0 
  0.0   0.0   0.0   0.0 -80.0   0.0   0.0   0.0   8.0   0.0   0.0   0.0 
  0.0   0.0   0.0   0.0 -90.0   0.0   0.0   0.0   0.0   9.0   0.0   0.0 
  0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0 
  0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0   0.0 

--------------------------------------------------------------------- */









