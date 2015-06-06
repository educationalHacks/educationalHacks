import cs_1c.*;

import java.util.*;
import java.text.*;

/* Name: Shiv Patel 
Assignment 7 */

//------------------------------------------------------
public class Foothill
{
   // -------  main --------------
   public static void main(String[] args) throws Exception
   {
      //10,000  20,000  60,000  100,000  160,000  200,000 
      final int ARRAY_SIZE = 20000;
      int randomInt, gapSequence;
      long startTime, stopTime;
      NumberFormat tidy = NumberFormat.getInstance(Locale.US);
      tidy.setMaximumFractionDigits(4);

      Integer[] arrayOfInts1 = new Integer[ARRAY_SIZE];
      Integer[] arrayOfInts2 = new Integer[ARRAY_SIZE];
      Integer[] arrayOfInts3 = new Integer[ARRAY_SIZE];;

      //Shell's short
      int[] gapArray = { 1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024,
            2048, 4096, 8192, 16384, 32768, 65536, 131072, 262144, 524288,
            1048576};

      //Sedgewick's gap sequence
      int[] sedgewickArray = new int[30]; 
      for(int k = 0, i = 2; k < sedgewickArray.length/2; k++, i++)
      {
         int formulaOne = (9 * (int)(Math.pow(4, k)) 
                            - 9 * (int) (Math.pow(2, k)) + 1);
         int formulaTwo = ((int) (Math.pow(4, i)) 
                            - 3 * (int) (Math.pow(2, i)) + 1);

         if(formulaOne < 0)
            formulaOne = Math.abs(formulaOne);//if negative, get absolute value
         sedgewickArray[k*2] = formulaOne;
         if(formulaTwo < 0)
            formulaTwo = Math.abs(formulaTwo);//if negative, get absolute value
         sedgewickArray[k*2+1] = formulaTwo;        
      }
      //Uncomment this to confirm sedgewickArray has good sequence.
      //System.out.println(Arrays.toString(sedgewickArray) + "\n");

      //Hibbard's formulas  1, 3, 7, 15, ..., 2^k - 1, ...
      int[] hibbardArray = new int[30]; 
      for (int k = 0; k < hibbardArray.length; k++) 
      {
         gapSequence = (int) (Math.pow(2, k + 1) - 1);
         hibbardArray[k] = gapSequence;
      }

      //fill distinct arrays with identical random values so we can compare gaps
      for (int k = 0; k < ARRAY_SIZE; k++)
      {
         randomInt = (int) (Math.random() * ARRAY_SIZE);
         arrayOfInts1[k] = randomInt;
         arrayOfInts2[k] = randomInt;
         arrayOfInts3[k] = randomInt;
      }

      //System.out.println();
      System.out.println("Array Size: " + ARRAY_SIZE);
      // shellSort1 -- using shell's outer loop 
      //if ARRAY_SIZE = 10000 then shellSort1 with gap sequence: ARRAY_SIZE/2
      //1, 2, 4, 9, 19, 39, 78, 156, 312, 625, 1250, 2500, 5000
      startTime = System.nanoTime();
      FHsort.shellSort1(arrayOfInts1);//Shell's implied gap sequence
      stopTime = System.nanoTime();
      System.out.println("Shell's implied Elapsed Time: "
            + tidy.format( (stopTime - startTime) / 1e9)
            + " seconds.");

      //gapArray - from module Shell's gap sequence: 
      //1, 2, 4, 8, 16, 32, 64, ..., 2k, ...
      startTime = System.nanoTime();
      shellSortX(arrayOfInts1, gapArray); //Shell's explicit gap sequence
      stopTime = System.nanoTime();
      System.out.println("Shell's explicit Elapsed Time: "
            + tidy.format( (stopTime - startTime) / 1e9)
            + " seconds.");

      //sedgewickArray - from text Sedgewick's gap sequence: 
      //1, 5, 19, 41, 109, 209, ...
      startTime = System.nanoTime();
      shellSortX(arrayOfInts2, sedgewickArray); //Sedgewick's gap sequence
      stopTime = System.nanoTime();
      System.out.println("Sedgewick's Elapsed Time: "
            + tidy.format( (stopTime - startTime) / 1e9)
            + " seconds.");

      //hibbardArray - from text and module Hibbard's gap sequence: 
      //1, 3, 7, 15, ..., 2k - 1, ...
      startTime = System.nanoTime();
      shellSortX(arrayOfInts3, hibbardArray); //Hibbard's gap sequence
      stopTime = System.nanoTime();
      System.out.println("Hibbard's Elapsed Time: "
            + tidy.format( (stopTime - startTime) / 1e9)
            + " seconds.");
   }

   public static < E extends Comparable< ? super E > > 
   void shellSortX(E[] a, int[] gapSequence)
   {
      int arraySize, arraySizeOfGap;
      int gap;
      int i, k, pos;
      E tmp;

      arraySize = a.length;
      arraySizeOfGap = gapSequence.length;
      for (i = arraySizeOfGap - 1; i >= 0;  i--)
      {
         gap = gapSequence[i];
         for(pos = gap; pos < arraySize; pos++ )
         {
            tmp = a[pos];
            for(k = pos; k >= gap && tmp.compareTo(a[k-gap]) < 0; k -= gap )
            {
               a[k] = a[k-gap];
            }
            a[k] = tmp;
         }        
      }
   }

}

/*------------------- paste of run from Console window -----------------------
Array Size: 10000
Shell's implied Elapsed Time: 0.0111 seconds.
Shell's explicit Elapsed Time: 0.0074 seconds.
Sedgewick's Elapsed Time: 0.0068 seconds.
Hibbard's Elapsed Time: 0.0122 seconds.

Array Size: 20000
Shell's implied Elapsed Time: 0.0202 seconds.
Shell's explicit Elapsed Time: 0.0104 seconds.
Sedgewick's Elapsed Time: 0.025 seconds.
Hibbard's Elapsed Time: 0.0295 seconds.

Array Size: 60000
Shell's implied Elapsed Time: 0.1398 seconds.
Shell's explicit Elapsed Time: 0.0938 seconds.
Sedgewick's Elapsed Time: 0.0879 seconds.
Hibbard's Elapsed Time: 0.0559 seconds.

Array Size: 100000
Shell's implied Elapsed Time: 0.1228 seconds.
Shell's explicit Elapsed Time: 0.0712 seconds.
Sedgewick's Elapsed Time: 0.108 seconds.
Hibbard's Elapsed Time: 0.0821 seconds.

Array Size: 160000
Shell's implied Elapsed Time: 0.1994 seconds.
Shell's explicit Elapsed Time: 0.1013 seconds.
Sedgewick's Elapsed Time: 0.1656 seconds.
Hibbard's Elapsed Time: 0.144 seconds.

Array Size: 2000000
Shell's implied Elapsed Time: 3.7889 seconds.
Shell's explicit Elapsed Time: 1.019 seconds.
Sedgewick's Elapsed Time: 2.5193 seconds.
Hibbard's Elapsed Time: 3.4599 seconds.

--------------------------------------------------------------------- */

/*Table of the four sequences and six array sizes 
-------------------+---------+---------+---------+---------+---------+--------+
                   | 10K     | 20K     | 60K     | 100K    | 160K    | 200K   |
-------------------+---------+---------+---------+---------+---------+--------+
Shell's Implicit   | 0.0111  | 0.0202  | 0.1398  | 0.1228  | 0.1994  | 3.7889 |
Shell's explicit   | 0.0074  | 0.0104  | 0.0938  | 0.0712  | 0.1013  | 1.019  | 
Sedgewick          | 0.0068  | 0.025   | 0.0879  | 0.108   | 0.1656  | 2.5193 |
Hibbart            | 0.0122  | 0.0295  | 0.0559  | 0.0821  | 0.144   | 3.4599 |
-------------------+---------+---------+---------+---------+---------+--------+
 */

/*
Why does Shell's gap sequence implied by shellSort1() give a 
   different timing result than the explicit array described 
   above and passed to shellSortX()?  Which is faster and why?

      shellShort1() and shellSortX() gives us different timing result because 
      of the difference in the gap sequence. ShellShortX() is faster because 
      the early phase moves out-of-place numbers very quickly toward their 
      final positions because of the large gaps.
 */


