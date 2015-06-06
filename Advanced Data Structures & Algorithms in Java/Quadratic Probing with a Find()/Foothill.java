import java.util.NoSuchElementException;
import java.util.Random;

import cs_1c.*;

/* Name: Shiv Patel 
Assignment 6 */

//----------- wrapper classes -------------

class EBookCompInt
implements Comparable<Integer>
{
   EBookEntry data;
   public EBookCompInt(EBookEntry e){ data = e; }
   public String toString() { return data.toString(); }  

   // we'll use compareTo() to implement our find on key
   public int compareTo(Integer key)
   {
      return data.getETextNum() - key; 
   }

   // let equals() preserve the equals() provided by embedded data
   public boolean equals( EBookCompInt rhs ) 
   {
      return data.equals(rhs.data);
   }

   public int hashCode()
   { 
      return data.getETextNum();
   }   
}

class EBookCompString 
implements Comparable<String>
{
   EBookEntry data;
   public EBookCompString(EBookEntry e){ data = e; }
   public String toString() { return data.toString(); }

   // we'll use compareTo() to implement our find on key
   public int compareTo(String key)
   {
      return data.getTitle().compareTo(key); 
   }

   public boolean equals(EBookCompString rhs)
   {
      return data.equals(rhs.data);
   }

   public int hashCode()
   {
      return data.getTitle().hashCode();
   }
}

//------------------------------------------------------
public class Foothill
{

   public static final int NUM_RANDOM_INDICES = 25;

   // -------  main --------------
   public static void main(String[] args) throws Exception
   {
      // FHhashQPwFind< Integer, EBookCompInt> hashTable 
      //   = new FHhashQPwFind<Integer, EBookCompInt>();

      FHhashQPwFind< String, EBookCompString> hashTable 
      = new FHhashQPwFind<String, EBookCompString>();

      EBookEntryReader bookInput = new EBookEntryReader("catalog-short4.txt"); 
      int totalBooks;
      int[] randomIndices;
      // EBookCompInt bookResult; 
      EBookCompString bookResult;

      // how we test the success of the read:
      if (bookInput.readError())
      {
         System.out.println("couldn't open " + bookInput.getFileName()
               + " for input.");
         return;
      }

      //Insert all the books
      totalBooks = bookInput.getNumBooks();
      for (int k = 0; k < totalBooks; k++)
      {
         // hashTable.insert(new EBookCompInt(bookInput.getBook(k)));
         hashTable.insert(new EBookCompString(bookInput.getBook(k))); 
      }      

      //Generating random indices.
      Random rand = new Random();
      randomIndices = new int[NUM_RANDOM_INDICES];
      for(int k = 0; k < NUM_RANDOM_INDICES; k++)
      {
         randomIndices[k] = rand.nextInt(totalBooks);
      }

      // display NUM_RANDOM_INDICES books from array ...
      for(int k = 0; k < NUM_RANDOM_INDICES; k++)
         System.out.print(bookInput.getBook(randomIndices[k]));

      // attempt to find on the selected key
      System.out.println( "The same random books from the hash table " );
      for(int k = 0; k < NUM_RANDOM_INDICES; k++)
      {        
         //followed by 25 find calls.
         try
         {
            bookResult = hashTable.find(
                  bookInput.getBook(randomIndices[k]).getTitle()); 
            // bookResult = hashTable.find(
            //       bookInput.getBook(randomIndices[k]).getETextNum()); 
            System.out.println( "Found " + bookResult.toString() );
         }
         catch (Exception e)
         {
            System.out.println( "Not found" );
         }
      }

      // test known successes failures exceptions:
      try
      {
         bookResult = hashTable.find("Batman");
         System.out.println( "Found " + bookResult.toString() );
      }
      catch (Exception e)
      {
         System.out.println( "Not found" );
      }

      // more failures
      try
      {
         bookResult = hashTable.find(null);
      }
      catch (Exception e)
      {
         System.out.println( "Not found" );
      }

      /*
      try
      {
         bookResult = hashTable.find( -3 );
      }
      catch (Exception e)
      {
         System.out.println( "Not found" );
      }

      try
      {
         bookResult = hashTable.find( totalBooks + totalBooks );
      }
      catch (Exception e)
      {
         System.out.println( "Not found" );
      }
       */

   } 
}

//------------------------------------------------------
class FHhashQPwFind<KeyType, E extends Comparable<KeyType> >
extends FHhashQP<E>
{
   E find(KeyType key)
   {
      int location = findPosKey(key);
      HashEntry<E> empData = mArray[location];

      if(empData.state == ACTIVE)
      {
         return empData.data;//returns the found object.
      }else {
         throw new NoSuchElementException();
      }
   }

   protected int findPosKey( KeyType key)
   {
      int kthOddNum = 1;
      int index = myHashKey(key);

      while(mArray[index].state != EMPTY 
            && !(mArray[index].data.compareTo(key) == 0))
      {
         index += kthOddNum; // k squared = (k-1) squared + kth odd #
         kthOddNum += 2;     // compute next odd #
         if ( index >= mTableSize )
            index -= mTableSize;
      }
      return index;
   }

   protected int myHashKey(KeyType key)
   {
      int hashVal;

      hashVal = key.hashCode() % mTableSize;
      if(hashVal < 0)
         hashVal += mTableSize;

      return hashVal;
   }
}

/*------------ paste of run from Console window - EBookCompInt ----------------
# 27616  ---------------
"The Vanishing Race"
by Dixon, Joseph Kossuth, 1856-1926
re: Indians of North America

# 28134  ---------------
"The Nursery, June 1877, Vol. XXI. No. 6A Monthly Magazine for Youngest Reade
rs"
by Various
re: Children's literature, American -- Periodicals

# 27229  ---------------
"Knight, Gerald Featherstone, 1894-"
by Knight, Gerald Featherstone, 1894-
re: World War, 1914-1918 -- Prisoners and prisons, German

# 7872  ---------------
"Dubliners"
by Joyce, James, 1882-1941
re: (no data found)

# 8108  ---------------
"Literary and Social Essays"
by Curtis, George William, 1824-1892
re: (no data found)

# 28164  ---------------
"The Big Bow Mystery"
by Zangwill, Israel, 1864-1926
re: Detective and mystery stories

# 27474  ---------------
"Queen Berngerd, The Bard and the Dreamsand other ballads"
by (no data found)
re: Poetry

# 30016  ---------------
"Medical Investigation in Seventeenth Century EnglandPapers Read at a Clark L
ibrary Seminar, October 14, 1967"
by Bodemer, Charles W.
re: Boyle, Robert, 1627-1691

# 28189  ---------------
"My Friends the SavagesNotes and Observations of a Perak settler (Malay Penin
sula)"
by Cerruti, Giovanni Battista, 1850-1914
re: Malay Peninsula -- Description and travel

# 28331  ---------------
"The Young Ranchersor, Fighting the Sioux"
by Ellis, Edward Sylvester, 1840-1916
re: Indians of North America -- Juvenile fiction

# 67  ---------------
"The Black Experience in America"
by Coombs, Norman, 1932-
re: African Americans -- History

# 10892  ---------------
"Dawn"
by Haggard, Henry Rider, 1856-1925
re: (no data found)

# 24996  ---------------
"The Tapu Of Banderah1901"
by Becke, Louis, 1855-1913
re: Adventure stories

# 29025  ---------------
"Roman Catholicism in Spain"
by Anonymous
re: Catholic Church -- Spain

# 29938  ---------------
"Blackwood's Edinburgh Magazine, Volume 59, No. 364, February 1846"
by Various
re: AP

# 27682  ---------------
"The Panchronicon"
by MacKaye, Harold Steele, 1866-1928
re: Science fiction

# 23870  ---------------
"The Teesdale Angler"
by Lakeland, R.
re: Fishing -- England -- Tees River

# 25737  ---------------
"ConcordanceA Terran Empire concordance"
by Wilson, Ann
re: Reference

# 28628  ---------------
"Devil Crystals of Arret"
by Wells, Hal K., 1900-
re: Science fiction

# 29670  ---------------
"Against OddsA Detective Story"
by Lynch, Lawrence L.
re: Detective and mystery stories

# 24859  ---------------
"Turned Adrift"
by Collingwood, Harry, 1851-1922
re: Survival after airplane accidents, shipwrecks, etc. -- Fiction

# 29895  ---------------
"Criminal ManAccording to the Classification of Cesare Lombroso"
by Lombroso, Gina, 1872-1944
re: Criminal anthropology

# 28807  ---------------
"The Story of My Life"
by Keller, Helen, 1880-1968
re: Keller, Helen, 1880-1968

# 25692  ---------------
"An Epitome of the Homeopathic Healing ArtContaining the New Discoveries and 
Improvements to the Present Time"
by Hill, B. L. (Benjamin L.)
re: Homeopathy -- Popular works

# 28123  ---------------
"The Scarlet Feather"
by Townley, Houghton
re: PS

The same random books from the hash table 
Found    # 27616  ---------------
"The Vanishing Race"
by Dixon, Joseph Kossuth, 1856-1926
re: Indians of North America


Found    # 28134  ---------------
"The Nursery, June 1877, Vol. XXI. No. 6A Monthly Magazine for Youngest Reade
rs"
by Various
re: Children's literature, American -- Periodicals


Found    # 27229  ---------------
"Knight, Gerald Featherstone, 1894-"
by Knight, Gerald Featherstone, 1894-
re: World War, 1914-1918 -- Prisoners and prisons, German


Found    # 7872  ---------------
"Dubliners"
by Joyce, James, 1882-1941
re: (no data found)


Found    # 8108  ---------------
"Literary and Social Essays"
by Curtis, George William, 1824-1892
re: (no data found)


Found    # 28164  ---------------
"The Big Bow Mystery"
by Zangwill, Israel, 1864-1926
re: Detective and mystery stories


Found    # 27474  ---------------
"Queen Berngerd, The Bard and the Dreamsand other ballads"
by (no data found)
re: Poetry


Found    # 30016  ---------------
"Medical Investigation in Seventeenth Century EnglandPapers Read at a Clark L
ibrary Seminar, October 14, 1967"
by Bodemer, Charles W.
re: Boyle, Robert, 1627-1691


Found    # 28189  ---------------
"My Friends the SavagesNotes and Observations of a Perak settler (Malay Penin
sula)"
by Cerruti, Giovanni Battista, 1850-1914
re: Malay Peninsula -- Description and travel


Found    # 28331  ---------------
"The Young Ranchersor, Fighting the Sioux"
by Ellis, Edward Sylvester, 1840-1916
re: Indians of North America -- Juvenile fiction


Found    # 67  ---------------
"The Black Experience in America"
by Coombs, Norman, 1932-
re: African Americans -- History


Found    # 10892  ---------------
"Dawn"
by Haggard, Henry Rider, 1856-1925
re: (no data found)


Found    # 24996  ---------------
"The Tapu Of Banderah1901"
by Becke, Louis, 1855-1913
re: Adventure stories


Found    # 29025  ---------------
"Roman Catholicism in Spain"
by Anonymous
re: Catholic Church -- Spain


Found    # 29938  ---------------
"Blackwood's Edinburgh Magazine, Volume 59, No. 364, February 1846"
by Various
re: AP


Found    # 27682  ---------------
"The Panchronicon"
by MacKaye, Harold Steele, 1866-1928
re: Science fiction


Found    # 23870  ---------------
"The Teesdale Angler"
by Lakeland, R.
re: Fishing -- England -- Tees River


Found    # 25737  ---------------
"ConcordanceA Terran Empire concordance"
by Wilson, Ann
re: Reference


Found    # 28628  ---------------
"Devil Crystals of Arret"
by Wells, Hal K., 1900-
re: Science fiction


Found    # 29670  ---------------
"Against OddsA Detective Story"
by Lynch, Lawrence L.
re: Detective and mystery stories


Found    # 24859  ---------------
"Turned Adrift"
by Collingwood, Harry, 1851-1922
re: Survival after airplane accidents, shipwrecks, etc. -- Fiction


Found    # 29895  ---------------
"Criminal ManAccording to the Classification of Cesare Lombroso"
by Lombroso, Gina, 1872-1944
re: Criminal anthropology


Found    # 28807  ---------------
"The Story of My Life"
by Keller, Helen, 1880-1968
re: Keller, Helen, 1880-1968


Found    # 25692  ---------------
"An Epitome of the Homeopathic Healing ArtContaining the New Discoveries and 
Improvements to the Present Time"
by Hill, B. L. (Benjamin L.)
re: Homeopathy -- Popular works


Found    # 28123  ---------------
"The Scarlet Feather"
by Townley, Houghton
re: PS


Not found
Not found

--------------------------------------------------------------------- */

/*----------- paste of run from Console window - EBookCompString ---------------
   # 29632  ---------------
   "Competition"
   by Causey, James
   re: Science fiction

   # 28415  ---------------
   "History Plays for the Grammar Grades"
   by Lyng, Mary Ella
   re: PN

   # 26646  ---------------
   "The Book of Romance"
   by (no data found)
   re: Arthur, King -- Juvenile literature

   # 28694  ---------------
   "Young Alaskans in the Far North"
   by Hough, Emerson, 1857-1923
   re: Alaska -- Juvenile fiction

   # 22249  ---------------
   "Shorty McCabe"
   by Ford, Sewell, 1868-1946
   re: (no data found)

   # 28335  ---------------
   "How Two Boys Made Their Own Electrical ApparatusContaining Complete Directio
ns for Making All Kinds of Simple Apparatus for the Study of Elementary Electric
ity"
   by St. John, Thomas M. (Thomas Matthew), 1865-
   re: Electric apparatus and appliances

   # 28264  ---------------
   "Cleek, the Master Detective"
   by Hanshew, Thomas W., 1857-1914
   re: Detective and mystery stories

   # 27337  ---------------
   "From the Car Behind"
   by Ingram, Eleanor M. (Eleanor Marie), 1886-1921
   re: PS

   # 26308  ---------------
   "Hints on Extemporaneous Preaching"
   by Ware, Henry, 1794-1843
   re: Extemporaneous preaching

   # 25811  ---------------
   "The Automobile Girls in the BerkshiresThe Ghost of Lost Man's Trail"
   by Crane, Laura Dent
   re: Adventure and adventurers -- Juvenile fiction

   # 29343  ---------------
   "Three Addresses to Girls at School"
   by Wilson, J. M. (James Maurice), 1836-1931
   re: Speeches, addresses, etc.

   # 26619  ---------------
   "Minnie's Pet Lamb"
   by Leslie, Madeline, 1815-1893
   re: Children -- Conduct of life -- Juvenile fiction

   # 28854  ---------------
   "The Leader of the Lower SchoolA Tale of School Life"
   by Brazil, Angela, 1868-1947
   re: Schools -- Juvenile fiction

   # 27356  ---------------
   "ChelseaThe Fascination of London"
   by Mitton, G. E. (Geraldine Edith)
   re: Chelsea (London, England) -- Description and travel

   # 25712  ---------------
   "The Great Events by Famous Historians, Volume 03"
   by (no data found)
   re: World history

   # 4416  ---------------
   "Sandra Belloni — Volume 4"
   by Meredith, George, 1828-1909
   re: English fiction -- 19th century

   # 28033  ---------------
   "The Wild HuntressLove in the Wilderness"
   by Reid, Mayne, 1818-1883
   re: PR

   # 26121  ---------------
   "Notes and Queries, Number 33,  June 15, 1850"
   by Various
   re: Questions and answers -- Periodicals

   # 21364  ---------------
   "The Rajah of Dah"
   by Fenn, George Manville, 1831-1909
   re: (no data found)

   # 25513  ---------------
   "Edmund Dulac's Fairy-BookFairy Tales of the Allied Nations"
   by Dulac, Edmund, 1882-1953
   re: Fairy tales

   # 29146  ---------------
   "Equation of Doom"
   by Vance, Gerald
   re: Science fiction

   # 12770  ---------------
   "A Study of the Topography and Municipal History of Praeneste"
   by Magoffin, Ralph Van Deman, 1874-1942
   re: (no data found)

   # 27039  ---------------
   "Project Gutenberg (1971-2005)"
   by Lebert, Marie
   re: (no data found)

   # 15641  ---------------
   "Notes and Queries, Number 65, January 25, 1851"
   by Various
   re: Questions and answers -- Periodicals

   # 25620  ---------------
   "Asiatic BreezesStudents on The Wing"
   by Optic, Oliver, 1822-1897
   re: Arab countries -- Fiction

The same random books from the hash table 
Found    # 29632  ---------------
   "Competition"
   by Causey, James
   re: Science fiction


Found    # 28415  ---------------
   "History Plays for the Grammar Grades"
   by Lyng, Mary Ella
   re: PN


Found    # 26646  ---------------
   "The Book of Romance"
   by (no data found)
   re: Arthur, King -- Juvenile literature


Found    # 28694  ---------------
   "Young Alaskans in the Far North"
   by Hough, Emerson, 1857-1923
   re: Alaska -- Juvenile fiction


Found    # 22249  ---------------
   "Shorty McCabe"
   by Ford, Sewell, 1868-1946
   re: (no data found)


Found    # 28335  ---------------
   "How Two Boys Made Their Own Electrical ApparatusContaining Complete Directio
ns for Making All Kinds of Simple Apparatus for the Study of Elementary Electric
ity"
   by St. John, Thomas M. (Thomas Matthew), 1865-
   re: Electric apparatus and appliances


Found    # 28264  ---------------
   "Cleek, the Master Detective"
   by Hanshew, Thomas W., 1857-1914
   re: Detective and mystery stories


Found    # 27337  ---------------
   "From the Car Behind"
   by Ingram, Eleanor M. (Eleanor Marie), 1886-1921
   re: PS


Found    # 26308  ---------------
   "Hints on Extemporaneous Preaching"
   by Ware, Henry, 1794-1843
   re: Extemporaneous preaching


Found    # 25811  ---------------
   "The Automobile Girls in the BerkshiresThe Ghost of Lost Man's Trail"
   by Crane, Laura Dent
   re: Adventure and adventurers -- Juvenile fiction


Found    # 29343  ---------------
   "Three Addresses to Girls at School"
   by Wilson, J. M. (James Maurice), 1836-1931
   re: Speeches, addresses, etc.


Found    # 26619  ---------------
   "Minnie's Pet Lamb"
   by Leslie, Madeline, 1815-1893
   re: Children -- Conduct of life -- Juvenile fiction


Found    # 28854  ---------------
   "The Leader of the Lower SchoolA Tale of School Life"
   by Brazil, Angela, 1868-1947
   re: Schools -- Juvenile fiction


Found    # 27356  ---------------
   "ChelseaThe Fascination of London"
   by Mitton, G. E. (Geraldine Edith)
   re: Chelsea (London, England) -- Description and travel


Found    # 25712  ---------------
   "The Great Events by Famous Historians, Volume 03"
   by (no data found)
   re: World history


Found    # 4416  ---------------
   "Sandra Belloni — Volume 4"
   by Meredith, George, 1828-1909
   re: English fiction -- 19th century


Found    # 28033  ---------------
   "The Wild HuntressLove in the Wilderness"
   by Reid, Mayne, 1818-1883
   re: PR


Found    # 26121  ---------------
   "Notes and Queries, Number 33,  June 15, 1850"
   by Various
   re: Questions and answers -- Periodicals


Found    # 21364  ---------------
   "The Rajah of Dah"
   by Fenn, George Manville, 1831-1909
   re: (no data found)


Found    # 25513  ---------------
   "Edmund Dulac's Fairy-BookFairy Tales of the Allied Nations"
   by Dulac, Edmund, 1882-1953
   re: Fairy tales


Found    # 29146  ---------------
   "Equation of Doom"
   by Vance, Gerald
   re: Science fiction


Found    # 12770  ---------------
   "A Study of the Topography and Municipal History of Praeneste"
   by Magoffin, Ralph Van Deman, 1874-1942
   re: (no data found)


Found    # 27039  ---------------
   "Project Gutenberg (1971-2005)"
   by Lebert, Marie
   re: (no data found)


Found    # 15641  ---------------
   "Notes and Queries, Number 65, January 25, 1851"
   by Various
   re: Questions and answers -- Periodicals


Found    # 25620  ---------------
   "Asiatic BreezesStudents on The Wing"
   by Optic, Oliver, 1822-1897
   re: Arab countries -- Fiction


Not found
Not found

--------------------------------------------------------------------- */


