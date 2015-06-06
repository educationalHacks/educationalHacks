import java.text.NumberFormat;
import java.util.Locale;
import java.util.Random;

/* Name: Shiv Patel 
Assignment 3 */
//------------------------------------------------------
public class Foothill
{
   final static int MAT_SIZE = 1600;
   static boolean clearMatC = false; 

   public static void main(String[] args) throws Exception
   {      
      int r, randRow, randCol;
      long startTime, stopTime;
      double randFrac;
      double smallPercent;
      NumberFormat tidy = NumberFormat.getInstance(Locale.US);
      tidy.setMaximumFractionDigits(4);

      // non-sparse matrices
      double[][] mat1, mat2, matAns;

      // allocate matrices
      mat1 = new double[MAT_SIZE][MAT_SIZE];
      mat2 = new double[MAT_SIZE + 1][MAT_SIZE];//Testing exceptions in matMult.
      matAns = new double[MAT_SIZE][MAT_SIZE];

      // generate small% of non-default values bet 0 and 1
      Random rand = new Random();
      smallPercent =  MAT_SIZE/100. * MAT_SIZE; //((0.1) * (MAT_SIZE * MAT_SIZE)))

      for (r = 0; r < smallPercent; r++)
      {
         randRow = rand.nextInt(MAT_SIZE);
         randCol = rand.nextInt(MAT_SIZE);
         randFrac = rand.nextDouble();
         mat1[randRow][randCol] = randFrac;
      }

      // 10x10 submatrix in lower right
      matShow(mat1, MAT_SIZE - 10, 10);
      System.out.println();

      startTime = System.nanoTime();      
      matMult(mat1, mat1, matAns);
      stopTime = System.nanoTime();
      matShow(matAns, MAT_SIZE - 10, 10);

      System.out.println("\nSize = " + MAT_SIZE + " Mat. Mult. Elapsed Time: "
            + tidy.format( (stopTime - startTime) / 1e9)
            + " seconds.");
   }

   public static void matShow(double[][] matA, int start, int size) ///fix this
   {
      //return if bounds are violated.
      if(start < 0 || size < 0 || 
            (start + size) > matA.length || (start + size) > matA[0].length)
         return;

      size = (start + size);
      for(int row = start; row < size; row++)
      {
         for(int col = start; col  < size; col++)
         {
            System.out.format("%5.2f ", matA[row][col]);
         }
         System.out.println();
      }
   }

   public static void matMult( double[][] matA,  double[][] matB, 
         double[][] matC)
   {
      int matARowSize = matA.length;
      int matAColSize = matA[0].length;
      int matBRowSize = matB.length;
      int matBColSize = matB[0].length;

      if(matARowSize != matBRowSize)
         throw new IndexOutOfBoundsException(); 

      if(clearMatC)//Clearing matC.
      {
         matC = new double[MAT_SIZE][MAT_SIZE];
      }

      for (int matARow = 0; matARow < matARowSize; matARow++)
      {
         for (int matBCol = 0; matBCol < matBColSize; matBCol++)
         {
            for (int matACol = 0; matACol < matAColSize; matACol++)
            {
               matC[matARow][matBCol] += 
                     (matA[matARow][matACol] * matB[matACol][matBCol]); 
            }
         }
      }
      clearMatC = true;
   }
}

/*------------------- paste of run from Console window -----------------------
 0.00  0.00  0.00  0.00  0.00  0.00  0.00  0.00  0.00  0.00 
 0.00  0.00  0.00  0.00  0.00  0.00  0.00  0.00  0.00  0.00 
 0.00  0.00  0.00  0.00  0.00  0.00  0.00  0.00  0.00  0.00 
 0.00  0.00  0.00  0.00  0.00  0.00  0.00  0.00  0.00  0.00 
 0.00  0.00  0.00  0.00  0.00  0.00  0.00  0.00  0.26  0.00 
 0.00  0.00  0.00  0.00  0.00  0.00  0.00  0.00  0.00  0.00 
 0.00  0.00  0.00  0.00  0.00  0.00  0.00  0.00  0.00  0.00 
 0.00  0.00  0.00  0.00  0.00  0.00  0.00  0.00  0.00  0.00 
 0.00  0.00  0.00  0.00  0.00  0.00  0.00  0.00  0.00  0.00 
 0.00  0.00  0.00  0.00  0.00  0.00  0.00  0.00  0.00  0.00 

 0.00  0.00  0.00  0.00  0.00  0.00  0.00  0.42  0.00  0.00 
 0.31  0.00  0.00  0.00  0.12  0.00  0.00  0.00  0.00  0.00 
 0.00  0.00  0.00  0.00  0.00  0.00  0.00  0.00  0.00  0.00 
 0.00  0.00  0.00  0.00  0.00  0.00  0.00  0.00  0.00  0.59 
 0.00  0.00  0.00  0.00  0.43  0.00  0.00  0.00  0.00  0.00 
 0.00  0.00  0.00  0.00  0.00  0.00  0.00  0.00  0.00  0.00 
 0.00  0.00  0.00  0.00  0.00  0.00  0.00  0.00  0.00  0.00 
 0.00  0.00  0.00  0.00  0.00  0.00  0.00  0.00  0.00  0.00 
 0.00  0.00  0.00  0.00  0.26  0.00  0.00  0.00  0.00  0.00 
 0.00  0.00  0.00  0.00  0.00  0.02  0.00  0.00  0.00  0.00 

Size = 400 Mat. Mult. Elapsed Time: 0.2455 seconds.
---------------------------------------------------------------------

For 1%:
Size = 400 Mat. Mult. Elapsed Time: 0.2455 seconds.   
Size = 800 Mat. Mult. Elapsed Time: 5.7873 seconds.   
Size = 1600 Mat. Mult. Elapsed Time: 58.7586 seconds.
Size = 3200 Mat. Mult. Elapsed Time: 610.9218 seconds.
Size = 4000 Mat. Mult. Elapsed Time: 1,254.5319 seconds.

For 15%:
Size = 400 Mat. Mult. Elapsed Time: 0.2389 seconds.
Size = 800 Mat. Mult. Elapsed Time: 5.6891 seconds.
Size = 1600 Mat. Mult. Elapsed Time: 60.4733 seconds.
Size = 3200 Mat. Mult. Elapsed Time: 611.7066 seconds.

For 25%:
Size = 400 Mat. Mult. Elapsed Time: 0.2291 seconds.
Size = 800 Mat. Mult. Elapsed Time: 5.7299 seconds.
Size = 1600 Mat. Mult. Elapsed Time: 60.4824 seconds.
Size = 3200 Mat. Mult. Elapsed Time: 603.6291 seconds. 


After reading the definition of matrix multiplication, but before implementing it, determine, 
 using the tools you have learned, the time complexity of this operation relative to M.  
 You should be able to get both a tight O() upper bound as well as a Θ() estimate for multiplying two MxM matrices together. 

    The most inner statement in the for loop is O(1). 
    O(1) gets executed N times. So its O(N * 1).
    O(N * 1) gets executed N times. So its O(N * N * 1).
    O(N * N * 1) gets executed N times. So the BigOh of the algorithm calculates to O(N^3).

    Because the algorithm has to go the same number of steps every time to get the correct answer, the Θ() will be the same as O().

Start out with a small M like 50 or 100.  Then, when everything is working, time your algorithm. Some questions you should answer (required):
   1) What was the smallest M that gave you a non-zero time?
      The smallest M that gave a non-zero time was 11. 1%
      Size = 10 Mat. Mult. Elapsed Time: 0 seconds.
      Size = 11 Mat. Mult. Elapsed Time: 0.0001 seconds.

   2) What happened when you doubled M, tripled it, quadrupled it, etc?  Give several M values and their times in a table.
      Size = 400 Mat. Mult. Elapsed Time: 0.2455 seconds.
      Size = 800 Mat. Mult. Elapsed Time: 5.7873 seconds.
      Size = 1600 Mat. Mult. Elapsed Time: 58.7586 seconds.
      Size = 3200 Mat. Mult. Elapsed Time: 610.9218 seconds.
      When doubling M the time increased based on the size of the matrix.

   3) How large an M can you use before the program refuses to run (exception or run-time error due to memory overload) or it takes so long you can't wait for the run?
      Size = 4000 Mat. Mult. Elapsed Time: 1,254.5319 seconds. 1%

   4) How did the data agree or disagree with your original time complexity estimate?
      The time increases a lot faster then expected which is N^3.

 */



