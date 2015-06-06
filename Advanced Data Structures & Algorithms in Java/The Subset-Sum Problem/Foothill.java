import java.util.ArrayList;
import java.util.ListIterator;

/* Name: Shiv Patel 
Assignment 1 */

public class Foothill
{
   public static void main(String[] args) throws Exception
   {
      int target = 72; 
      ArrayList<Integer> dataSet = new ArrayList<Integer>();
      ArrayList<Sublist> choices = new ArrayList<Sublist>();
      int k, j, max, kBest;
      boolean foundPerfect = false;
      kBest = 0;
      max = 0;

      dataSet.add(2); dataSet.add(12); dataSet.add(22); 
      dataSet.add(5); dataSet.add(15); dataSet.add(25);
      dataSet.add(9); dataSet.add(19); dataSet.add(29);

      Sublist init = new Sublist(dataSet); 
      choices.add(init);

      //looping over all elements of dataSet.
      for(k = 0; k < dataSet.size() && !foundPerfect; k++) 
      {
         //Using temp because choices grows as we add to it.
         int temp = choices.size(); 
         //looping over all elements, L that already are members of choices.
         for(j = 0; j < temp && !foundPerfect; j++) 
         {
            if((choices.get(j).getSum() + dataSet.get(k)) <= target) 
            { 
               Sublist addSub = choices.get(j).addItem(k);
               if(addSub.getSum() > max)
               {
                  max = addSub.getSum();
               }
               choices.add(addSub);
               kBest = (choices.size() - 1);

               if((choices.get(j).getSum() + dataSet.get(k)) == target)
               {
                  foundPerfect = true;
                  kBest = (choices.size() - 1);
               }
            }
         }
      }

      System.out.println("Target time: " + target);
      choices.get(kBest).showSublist();
   }
}


class Sublist implements Cloneable
{
   private int sum = 0;
   private ArrayList<Integer> originalObjects;
   private ArrayList<Integer> indices;

   // constructor creates an empty Sublist (no indices)
   public Sublist(ArrayList<Integer> orig) 
   {
      sum = 0;
      originalObjects = orig;
      indices = new ArrayList<Integer>();
   }

   int getSum() { return sum; }

   public Object clone() throws CloneNotSupportedException
   {
      // shallow copy
      Sublist newObject = (Sublist)super.clone();
      // deep copy
      newObject.indices = (ArrayList<Integer>)indices.clone();

      return newObject;
   }

   Sublist addItem( int indexOfItemToAdd ) throws CloneNotSupportedException
   { 
      Sublist obj = (Sublist) this.clone();       
      obj.indices.add(indexOfItemToAdd);       
      obj.sum = obj.sum + originalObjects.get(indexOfItemToAdd);                 
      return obj;
   }

   void showSublist()
   { 
      ListIterator<Integer> p;
      System.out.println("Sublist -----------------------------");
      System.out.println(" sum: " + this.sum);

      for (p = indices.listIterator(); p.hasNext();  )
      {
         int indicesVal = p.next();
         System.out.println(" array[" + indicesVal + "] = " 
               + originalObjects.get(indicesVal) + "," ); 
      }
   }

}

/*---------------- paste of Part_1 run_1 from Console window -----------------
Target time: 72
Sublist -----------------------------
 sum: 72
 array[0] = 2,
 array[2] = 22,
 array[3] = 5,
 array[4] = 15,
 array[6] = 9,
 array[7] = 19,

--------------------------------------------------------------------- */

/*---------------- paste of Part_1 run_2 from Console window -----------------
Target time: 82
Sublist -----------------------------
 sum: 82
 array[1] = 12,
 array[2] = 22,
 array[3] = 5,
 array[4] = 15,
 array[6] = 9,
 array[7] = 19,

--------------------------------------------------------------------- */

/*-------------- paste of Part_1 run_3; did not meet the target ---------------
Target time: 1000
Sublist -----------------------------
 sum: 138
 array[0] = 2,
 array[1] = 12,
 array[2] = 22,
 array[3] = 5,
 array[4] = 15,
 array[5] = 25,
 array[6] = 9,
 array[7] = 19,
 array[8] = 29,

--------------------------------------------------------------------- */



//------------Part 2: The Subset Sum problem for iTunesEntries-----------------
import cs_1c.*;
import java.text.*;
import java.util.*;

public class Foothill
{
   // -------  main --------------
   public static void main(String[] args) throws Exception
   {
      int target = 5700;
      ArrayList<iTunesEntry> dataSet = new ArrayList<iTunesEntry>();
      ArrayList<Sublist> choices = new ArrayList<Sublist>();
      int k, j, max, kBest, arraySize;
      boolean foundPerfect = false;
      kBest = 0;
      max = 0;

      // for formatting and timing
      NumberFormat tidy = NumberFormat.getInstance(Locale.US);
      tidy.setMaximumFractionDigits(4);
      long startTime, stopTime; 

      // read the iTunes Data
      iTunesEntryReader tunesInput = new iTunesEntryReader("itunes_file.txt");

      // test the success of the read:
      if (tunesInput.readError())
      {
         System.out.println("couldn't open " + tunesInput.getFileName()
               + " for input.");
         return;
      }

      // load the dataSet ArrayList with the iTunes:
      arraySize = tunesInput.getNumTunes();
      for (k = 0; k < arraySize; k++)
         dataSet.add(tunesInput.getTune(k));

      choices.clear();
      System.out.println("Target time: " + target);

      // code supplied by student
      startTime = System.nanoTime();
      Sublist init = new Sublist(dataSet); 
      choices.add(init);

      for(k = 0; k < dataSet.size() && !foundPerfect; k++) 
      {
         int temp = choices.size(); 
         for(j = 0; j < temp && !foundPerfect; j++) 
         {
            if((choices.get(j).getSum() + dataSet.get(k).getTime()) <= target) 
            { 
               Sublist addSub = choices.get(j).addItem(k);
               if(addSub.getSum() > max)
               {
                  max = addSub.getSum();
               }
               choices.add(addSub);
               kBest = (choices.size() - 1);

               if((choices.get(j).getSum() + 
                     dataSet.get(k).getTime()) == target)
               {
                  foundPerfect = true;
                  kBest = (choices.size() - 1);
               }
            }
         }
      }

      choices.get(kBest).showSublist();
      stopTime = System.nanoTime();

      // report algorithm time
      System.out.println("\nAlgorithm Elapsed Time: "
            + tidy.format((stopTime - startTime) / 1e9)
            + " seconds.\n");
   }
}


class Sublist implements Cloneable
{
   private int sum = 0;
   private ArrayList<iTunesEntry> originalObjects;
   private ArrayList<Integer> indices;

   // constructor creates an empty Sublist (no indices)
   public Sublist(ArrayList<iTunesEntry> orig) 
   {
      sum = 0;
      originalObjects = orig;
      indices = new ArrayList<Integer>();
   }

   int getSum() { return sum; }

   public Object clone() throws CloneNotSupportedException
   {
      // shallow copy
      Sublist newObject = (Sublist)super.clone();
      // deep copy
      newObject.indices = (ArrayList<Integer>)indices.clone();

      return newObject;
   }

   Sublist addItem( int indexOfItemToAdd ) throws CloneNotSupportedException
   { 
      Sublist obj = (Sublist) this.clone();       
      obj.indices.add(indexOfItemToAdd);       
      obj.sum = obj.sum + originalObjects.get(indexOfItemToAdd).getTime();                 
      return obj;
   }

   void showSublist()
   { 
      ListIterator<Integer> p;
      System.out.println("Sublist -----------------------------");
      System.out.println(" sum: " + this.sum);

      for (p = indices.listIterator(); p.hasNext();  )
      {
         int indicesVal = p.next();
         System.out.println(" array[" + indicesVal + "] = " 
               + originalObjects.get(indicesVal).toString() + ",");
      }
   }

}

/*---------------- paste of Part_2 run_1 from Console window -----------------
Target time: 3600
Sublist -----------------------------
 sum: 3600
 array[0] = Carrie Underwood | Cowboy Casanova |  3:56,
 array[1] = Carrie Underwood | Quitter |  3:40,
 array[2] = Rihanna | Russian Roulette |  3:48,
 array[4] = Foo Fighters | Monkey Wrench |  3:50,
 array[5] = Eric Clapton | Pretending |  4:43,
 array[6] = Eric Clapton | Bad Love |  5:08,
 array[7] = Howlin' Wolf | Everybody's In The Mood |  2:58,
 array[8] = Howlin' Wolf | Well That's All Right |  2:55,
 array[9] = Reverend Gary Davis | Samson and Delilah |  3:36,
 array[11] = Roy Buchanan | Hot Cha |  3:28,
 array[12] = Roy Buchanan | Green Onions |  7:23,
 array[13] = Janiva Magness | I'm Just a Prisoner |  3:50,
 array[14] = Janiva Magness | You Were Never Mine |  4:36,
 array[15] = John Lee Hooker | Hobo Blues |  3:07,
 array[16] = John Lee Hooker | I Can't Quit You Baby |  3:02,

Algorithm Elapsed Time: 0.0774 seconds.


--------------------------------------------------------------------- */

/*---------------- paste of Part_2 run_2 from Console window -----------------
Target time: 4600
Sublist -----------------------------
 sum: 4600
 array[0] = Carrie Underwood | Cowboy Casanova |  3:56,
 array[1] = Carrie Underwood | Quitter |  3:40,
 array[2] = Rihanna | Russian Roulette |  3:48,
 array[4] = Foo Fighters | Monkey Wrench |  3:50,
 array[5] = Eric Clapton | Pretending |  4:43,
 array[6] = Eric Clapton | Bad Love |  5:08,
 array[7] = Howlin' Wolf | Everybody's In The Mood |  2:58,
 array[8] = Howlin' Wolf | Well That's All Right |  2:55,
 array[9] = Reverend Gary Davis | Samson and Delilah |  3:36,
 array[10] = Reverend Gary Davis | Twelve Sticks |  3:14,
 array[11] = Roy Buchanan | Hot Cha |  3:28,
 array[12] = Roy Buchanan | Green Onions |  7:23,
 array[13] = Janiva Magness | I'm Just a Prisoner |  3:50,
 array[14] = Janiva Magness | You Were Never Mine |  4:36,
 array[16] = John Lee Hooker | I Can't Quit You Baby |  3:02,
 array[17] = Snoop Dogg | That's The Homie |  5:43,
 array[18] = Snoop Dogg | Gangsta Luv |  4:17,
 array[19] = The Rubyz | Ladies and Gentleman |  3:21,
 array[20] = The Rubyz | Watch the Girl |  3:12,

Algorithm Elapsed Time: 0.5595 seconds.


--------------------------------------------------------------------- */

/*-------------- paste of Part_2 run_3 from Console window -----------------
Target time: 5000
Sublist -----------------------------
 sum: 5000
 array[0] = Carrie Underwood | Cowboy Casanova |  3:56,
 array[2] = Rihanna | Russian Roulette |  3:48,
 array[3] = Foo Fighters | All My Life |  4:23,
 array[4] = Foo Fighters | Monkey Wrench |  3:50,
 array[5] = Eric Clapton | Pretending |  4:43,
 array[6] = Eric Clapton | Bad Love |  5:08,
 array[7] = Howlin' Wolf | Everybody's In The Mood |  2:58,
 array[8] = Howlin' Wolf | Well That's All Right |  2:55,
 array[9] = Reverend Gary Davis | Samson and Delilah |  3:36,
 array[10] = Reverend Gary Davis | Twelve Sticks |  3:14,
 array[11] = Roy Buchanan | Hot Cha |  3:28,
 array[12] = Roy Buchanan | Green Onions |  7:23,
 array[13] = Janiva Magness | I'm Just a Prisoner |  3:50,
 array[15] = John Lee Hooker | Hobo Blues |  3:07,
 array[16] = John Lee Hooker | I Can't Quit You Baby |  3:02,
 array[17] = Snoop Dogg | That's The Homie |  5:43,
 array[18] = Snoop Dogg | Gangsta Luv |  4:17,
 array[23] = Berliner Philharmoniker | Brahms: Symphony No. 1 in C Minor Op. 68 
|  13:59,

Algorithm Elapsed Time: 52.6754 seconds.


--------------------------------------------------------------------- */

/*-------------- paste of Part_2 run_4 from Console window -----------------
Target time: 5500
Sublist -----------------------------
 sum: 5500
 array[0] = Carrie Underwood | Cowboy Casanova |  3:56,
 array[1] = Carrie Underwood | Quitter |  3:40,
 array[2] = Rihanna | Russian Roulette |  3:48,
 array[3] = Foo Fighters | All My Life |  4:23,
 array[4] = Foo Fighters | Monkey Wrench |  3:50,
 array[5] = Eric Clapton | Pretending |  4:43,
 array[6] = Eric Clapton | Bad Love |  5:08,
 array[7] = Howlin' Wolf | Everybody's In The Mood |  2:58,
 array[8] = Howlin' Wolf | Well That's All Right |  2:55,
 array[9] = Reverend Gary Davis | Samson and Delilah |  3:36,
 array[11] = Roy Buchanan | Hot Cha |  3:28,
 array[12] = Roy Buchanan | Green Onions |  7:23,
 array[13] = Janiva Magness | I'm Just a Prisoner |  3:50,
 array[14] = Janiva Magness | You Were Never Mine |  4:36,
 array[16] = John Lee Hooker | I Can't Quit You Baby |  3:02,
 array[17] = Snoop Dogg | That's The Homie |  5:43,
 array[18] = Snoop Dogg | Gangsta Luv |  4:17,
 array[19] = The Rubyz | Ladies and Gentleman |  3:21,
 array[21] = Veggie Tales | Donuts for Benny |  3:04,
 array[23] = Berliner Philharmoniker | Brahms: Symphony No. 1 in C Minor Op. 68 
|  13:59,

Algorithm Elapsed Time: 61.8278 seconds.


--------------------------------------------------------------------- */




