import java.util.Scanner;

/* Name: Shiv Patel 
Assignment 2 */
public class Foothill
{
   public static void main(String[] args)
   {
      int rule, k;
      String strUserIn;

      Scanner inputStream = new Scanner(System.in);
      Automaton aut;

      // get rule from user
      do
      {
         System.out.print("Enter Rule (0 - 255): ");
         // get the answer in the form of a string:
         strUserIn = inputStream.nextLine();
         // and convert it to a number so we can compute:
         rule = Integer.parseInt(strUserIn);
      } while (rule < 0 || rule > 255);

      // create automaton with this rule and single central dot
      aut = new Automaton(rule);
      // now show it
      System.out.println("   start");
      for (k = 0; k < 100; k++)
      {
         System.out.println( aut.toStringCurrentGen() );
         aut.propagateNewGeneration();
      }
      System.out.println("   end");
      inputStream.close();
   }
}


class Automaton 
{
   // class constants
   public final static int MAX_DISPLAY_WIDTH = 121;

   // private member
   private boolean rules[];        // allocate rules[8] in constructor!
   private StringBuilder thisGen;  // same here
   String extremeBit;   // bit, "*" or " ", implied everywhere "outside"
   int displayWidth;    // an odd number so it can be perfectly centered

   // public constructors, mutators, etc. (need to be written)
   public Automaton(int rule)
   {
      rules = new boolean[8];
      setRule(rule);
      thisGen = new StringBuilder(79);
      resetFirstGen();
      extremeBit = " ";
   }

   public void resetFirstGen()
   {
      thisGen.append("*");
      displayWidth = 79;
   }

   public boolean setRule(int newRule) 
   {
      if (newRule < 0 || newRule > 255) 
      {
         return false;
      }
      //Creating ruleset using bitwise-AND
      for (int k = 0; k < 8; k++) 
      {
         if ((newRule & (int)(Math.pow(2, k))) != 0)
            rules[k] = true;
         else
            rules[k] = false;
      }
      return true;
   }

   public boolean setDisplayWidth(int width)
   {
      //check if width is an odd number that is less than MAX_DISPLAY_WIDTH.
      if(width < 1 || width % 2 != 1)
      {
         return false;
      }
      if(width > MAX_DISPLAY_WIDTH)
      {
         displayWidth = MAX_DISPLAY_WIDTH;
      } else {
         displayWidth = width;
      }
      return true;
   }

   public String toStringCurrentGen()
   {
      String returnString;
      int numOfExtremes;
      if(thisGen.length() > MAX_DISPLAY_WIDTH)
      {
         int subStringStart = (thisGen.length()/2)-(MAX_DISPLAY_WIDTH/2);
         returnString = thisGen.substring(subStringStart,
               thisGen.length()-subStringStart); 
         return returnString;
      } else {
         numOfExtremes = (MAX_DISPLAY_WIDTH/2)-(thisGen.length()/2);     
      }

      StringBuilder extremes = new StringBuilder(numOfExtremes);
      for(int k=0; k < numOfExtremes; k++)
      {
         extremes.append(extremeBit);
      }
      //returning returnString that consist of the current generation.
      returnString = extremes + thisGen.toString() + extremes;
      return returnString;
   }

   public void propagateNewGeneration()
   {
      StringBuilder nextGen = new StringBuilder(thisGen.length() + 2);
      thisGen.append(extremeBit + extremeBit);
      thisGen.insert(0, extremeBit + extremeBit);

      //convert star and space to 1's and 0's.
      for(int k=0; k < thisGen.length(); k++)
      {
         if(((int)(thisGen.charAt(k)) & 42) == 42)
         {
            thisGen.replace(k,k+1,"1");
         }else{
            thisGen.replace(k,k+1,"0");
         }
      }

      for(int k = 1; k < thisGen.length()-1; k++)
      {
         int cellEnvironment = Integer.parseInt(thisGen.substring(k-1,k+2),2);
         //apply rules to create nextGen String.
         if(rules[cellEnvironment])
         {
            nextGen.append("*");
         }else{
            nextGen.append(" ");
         }
      }
      thisGen = nextGen;
      setDisplayWidth(thisGen.length());

      //applying rule to extremeBit
      if(extremeBit == "*")
      {
         if(rules[7])
         {
            extremeBit = "*";
         } else {
            extremeBit = " ";
         }
      } else {
         if(rules[0])
         {
            extremeBit = "*";
         }else{
            extremeBit = " ";
         }
      }

   }

}

/*------------------- paste of run from Console window -----------------------
Enter Rule (0 - 255): 4
   start
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
                                                            *                                                            
   end

--------------------------------------------------------------------- */
/*------------------- paste of run from Console window -----------------------
Enter Rule (0 - 255): 126
start
                                                         *                                                            
                                                        ***                                                           
                                                       ** **                                                          
                                                      *******                                                         
                                                     **     **                                                        
                                                    ****   ****                                                       
                                                   **  ** **  **                                                      
                                                  ***************                                                     
                                                 **             **                                                    
                                                ****           ****                                                   
                                               **  **         **  **                                                  
                                              ********       ********                                                 
                                             **      **     **      **                                                
                                            ****    ****   ****    ****                                               
                                           **  **  **  ** **  **  **  **                                              
                                          *******************************                                             
                                         **                             **                                            
                                        ****                           ****                                           
                                       **  **                         **  **                                          
                                      ********                       ********                                         
                                     **      **                     **      **                                        
                                    ****    ****                   ****    ****                                       
                                   **  **  **  **                 **  **  **  **                                      
                                  ****************               ****************                                     
                                 **              **             **              **                                    
                                ****            ****           ****            ****                                   
                               **  **          **  **         **  **          **  **                                  
                              ********        ********       ********        ********                                 
                             **      **      **      **     **      **      **      **                                
                            ****    ****    ****    ****   ****    ****    ****    ****                               
                           **  **  **  **  **  **  **  ** **  **  **  **  **  **  **  **                              
                          ***************************************************************                             
                         **                                                             **                            
                        ****                                                           ****                           
                       **  **                                                         **  **                          
                      ********                                                       ********                         
                     **      **                                                     **      **                        
                    ****    ****                                                   ****    ****                       
                   **  **  **  **                                                 **  **  **  **                      
                  ****************                                               ****************                     
                 **              **                                             **              **                    
                ****            ****                                           ****            ****                   
               **  **          **  **                                         **  **          **  **                  
              ********        ********                                       ********        ********                 
             **      **      **      **                                     **      **      **      **                
            ****    ****    ****    ****                                   ****    ****    ****    ****               
           **  **  **  **  **  **  **  **                                 **  **  **  **  **  **  **  **              
          ********************************                               ********************************             
         **                              **                             **                              **            
        ****                            ****                           ****                            ****           
       **  **                          **  **                         **  **                          **  **          
      ********                        ********                       ********                        ********         
     **      **                      **      **                     **      **                      **      **        
    ****    ****                    ****    ****                   ****    ****                    ****    ****       
   **  **  **  **                  **  **  **  **                 **  **  **  **                  **  **  **  **      
  ****************                ****************               ****************                ****************     
 **              **              **              **             **              **              **              **    
****            ****            ****            ****           ****            ****            ****            ****   
**  **          **  **          **  **          **  **         **  **          **  **          **  **          **  **  
********        ********        ********        ********       ********        ********        ********        ******** 
**      **      **      **      **      **      **      **     **      **      **      **      **      **      **      **
***    ****    ****    ****    ****    ****    ****    ****   ****    ****    ****    ****    ****    ****    ****    ***
**  **  **  **  **  **  **  **  **  **  **  **  **  **  ** **  **  **  **  **  **  **  **  **  **  **  **  **  **  **  
*************************************************************************************************************************
                                                                                                                      
                                                                                                                      
                                                                                                                      
*                                                                                                                       *
**                                                                                                                     **
***                                                                                                                   ***
**                                                                                                                 **  
*****                                                                                                               *****
 **                                                                                                             **    
****                                                                                                           ****   
**  **                                                                                                         **  **  
********                                                                                                       ******** 
**      **                                                                                                     **      **
***    ****                                                                                                   ****    ***
**  **  **                                                                                                 **  **  **  
*************                                                                                               *************
         **                                                                                             **            
        ****                                                                                           ****           
       **  **                                                                                         **  **          
      ********                                                                                       ********         
     **      **                                                                                     **      **        
    ****    ****                                                                                   ****    ****       
   **  **  **  **                                                                                 **  **  **  **      
  ****************                                                                               ****************     
 **              **                                                                             **              **    
****            ****                                                                           ****            ****   
**  **          **  **                                                                         **  **          **  **  
********        ********                                                                       ********        ******** 
**      **      **      **                                                                     **      **      **      **
***    ****    ****    ****                                                                   ****    ****    ****    ***
**  **  **  **  **  **  **                                                                 **  **  **  **  **  **  **  
*****************************                                                               *****************************
                         **                                                             **                            
                        ****                                                           ****                           
                       **  **                                                         **  **                          
                      ********                                                       ********                         
end
--------------------------------------------------------------------- */

/*------------------- paste of run from Console window -----------------------
Enter Rule (0 - 255): 130
   start
                                                            *                                                            
                                                           *                                                             
                                                          *                                                              
                                                         *                                                               
                                                        *                                                                
                                                       *                                                                 
                                                      *                                                                  
                                                     *                                                                   
                                                    *                                                                    
                                                   *                                                                     
                                                  *                                                                      
                                                 *                                                                       
                                                *                                                                        
                                               *                                                                         
                                              *                                                                          
                                             *                                                                           
                                            *                                                                            
                                           *                                                                             
                                          *                                                                              
                                         *                                                                               
                                        *                                                                                
                                       *                                                                                 
                                      *                                                                                  
                                     *                                                                                   
                                    *                                                                                    
                                   *                                                                                     
                                  *                                                                                      
                                 *                                                                                       
                                *                                                                                        
                               *                                                                                         
                              *                                                                                          
                             *                                                                                           
                            *                                                                                            
                           *                                                                                             
                          *                                                                                              
                         *                                                                                               
                        *                                                                                                
                       *                                                                                                 
                      *                                                                                                  
                     *                                                                                                   
                    *                                                                                                    
                   *                                                                                                     
                  *                                                                                                      
                 *                                                                                                       
                *                                                                                                        
               *                                                                                                         
              *                                                                                                          
             *                                                                                                           
            *                                                                                                            
           *                                                                                                             
          *                                                                                                              
         *                                                                                                               
        *                                                                                                                
       *                                                                                                                 
      *                                                                                                                  
     *                                                                                                                   
    *                                                                                                                    
   *                                                                                                                     
  *                                                                                                                      
 *                                                                                                                       
*                                                                                                                        
                                                                                                                         
                                                                                                                         
                                                                                                                         
                                                                                                                         
                                                                                                                         
                                                                                                                         
                                                                                                                         
                                                                                                                         
                                                                                                                         
                                                                                                                         
                                                                                                                         
                                                                                                                         
                                                                                                                         
                                                                                                                         
                                                                                                                         
                                                                                                                         
                                                                                                                         
                                                                                                                         
                                                                                                                         
                                                                                                                         
                                                                                                                         
                                                                                                                         
                                                                                                                         
                                                                                                                         
                                                                                                                         
                                                                                                                         
                                                                                                                         
                                                                                                                         
                                                                                                                         
                                                                                                                         
                                                                                                                         
                                                                                                                         
                                                                                                                         
                                                                                                                         
                                                                                                                         
                                                                                                                         
                                                                                                                         
                                                                                                                         
                                                                                                                         
   end

--------------------------------------------------------------------- */
/*------------------- paste of run from Console window -----------------------
Enter Rule (0 - 255): 124
   start
                                                            *                                                            
                                                            **                                                           
                                                            ***                                                          
                                                            * **                                                         
                                                            *****                                                        
                                                            *   **                                                       
                                                            **  ***                                                      
                                                            *** * **                                                     
                                                            * *******                                                    
                                                            ***     **                                                   
                                                            * **    ***                                                  
                                                            *****   * **                                                 
                                                            *   **  *****                                                
                                                            **  *** *   **                                               
                                                            *** * ****  ***                                              
                                                            * *****  ** * **                                             
                                                            ***   ** ********                                            
                                                            * **  ****      **                                           
                                                            ***** *  **     ***                                          
                                                            *   **** ***    * **                                         
                                                            **  *  *** **   *****                                        
                                                            *** ** * *****  *   **                                       
                                                            * ********   ** **  ***                                      
                                                            ***      **  ****** * **                                     
                                                            * **     *** *    *******                                    
                                                            *****    * ****   *     **                                   
                                                            *   **   ***  **  **    ***                                  
                                                            **  ***  * ** *** ***   * **                                 
                                                            *** * ** ****** *** **  *****                                
                                                            * ********    *** ***** *   **                               
                                                            ***      **   * ***   ****  ***                              
                                                            * **     ***  *** **  *  ** * **                             
                                                            *****    * ** * ***** ** ********                            
                                                            *   **   ********   ******      **                           
                                                            **  ***  *      **  *    **     ***                          
                                                            *** * ** **     *** **   ***    * **                         
                                                            * **********    * *****  * **   *****                        
                                                            ***        **   ***   ** *****  *   **                       
                                                            * **       ***  * **  ****   ** **  ***                      
                                                            *****      * ** ***** *  **  ****** * **                     
                                                            *   **     ******   **** *** *    *******                    
                                                            **  ***    *    **  *  *** ****   *     **                   
                                                            *** * **   **   *** ** * ***  **  **    ***                  
                                                            * *******  ***  * ******** ** *** ***   * **                 
                                                            ***     ** * ** ***      ****** *** **  *****                
                                                            * **    ********* **     *    *** ***** *   **               
                                                            *****   *       *****    **   * ***   ****  ***              
                                                            *   **  **      *   **   ***  *** **  *  ** * **             
                                                            **  *** ***     **  ***  * ** * ***** ** ********            
                                                            *** * *** **    *** * ** ********   ******      **           
                                                            * ***** *****   * ********      **  *    **     ***          
                                                            ***   ***   **  ***      **     *** **   ***    * **         
                                                            * **  * **  *** * **     ***    * *****  * **   *****        
                                                            ***** ***** * *******    * **   ***   ** *****  *   **       
                                                            *   ***   *****     **   *****  * **  ****   ** **  ***      
                                                            **  * **  *   **    ***  *   ** ***** *  **  ****** * **     
                                                            *** ***** **  ***   * ** **  ****   **** *** *    *******    
                                                            * ***   ***** * **  ******** *  **  *  *** ****   *     **   
                                                            *** **  *   ******* *      **** *** ** * ***  **  **    ***  
                                                            * ***** **  *     ****     *  *** ******** ** *** ***   * ** 
                                                            ***   ***** **    *  **    ** * ***      ****** *** **  *****
                                                            * **  *   *****   ** ***   ****** **     *    *** ***** *   *
                                                            ***** **  *   **  **** **  *    *****    **   * ***   ****  *
                                                            *   ***** **  *** *  ***** **   *   **   ***  *** **  *  ** *
                                                            **  *   ***** * **** *   *****  **  ***  * ** * ***** ** ****
                                                            *** **  *   *****  ****  *   ** *** * ** ********   ******   
                                                            * ***** **  *   ** *  ** **  **** ********      **  *    **  
                                                            ***   ***** **  ***** ****** *  ***      **     *** **   *** 
                                                            * **  *   ***** *   ***    **** * **     ***    * *****  * **
                                                            ***** **  *   ****  * **   *  *******    * **   ***   ** ****
                                                            *   ***** **  *  ** *****  ** *     **   *****  * **  ****   
                                                            **  *   ***** ** ****   ** *****    ***  *   ** ***** *  **  
                                                            *** **  *   ******  **  ****   **   * ** **  ****   **** *** 
                                                            * ***** **  *    ** *** *  **  ***  ******** *  **  *  *** **
                                                            ***   ***** **   **** **** *** * ** *      **** *** ** * *** 
                                                            * **  *   *****  *  ***  *** *********     *  *** ******** **
                                                            ***** **  *   ** ** * ** * ***       **    ** * ***      ****
                                                            *   ***** **  ************** **      ***   ****** **     *   
                                                            **  *   ***** *            *****     * **  *    *****    **  
                                                            *** **  *   ****           *   **    ***** **   *   **   *** 
                                                            * ***** **  *  **          **  ***   *   *****  **  ***  * **
                                                            ***   ***** ** ***         *** * **  **  *   ** *** * ** ****
                                                            * **  *   ****** **        * ******* *** **  **** ********   
                                                            ***** **  *    *****       ***     *** ***** *  ***      **  
                                                            *   ***** **   *   **      * **    * ***   **** * **     *** 
                                                            **  *   *****  **  ***     *****   *** **  *  *******    * **
                                                            *** **  *   ** *** * **    *   **  * ***** ** *     **   ****
                                                            * ***** **  **** *******   **  *** ***   *******    ***  *   
                                                            ***   ***** *  ***     **  *** * *** **  *     **   * ** **  
                                                            * **  *   **** * **    *** * ***** ***** **    ***  ******** 
                                                            ***** **  *  *******   * *****   ***   *****   * ** *      **
                                                            *   ***** ** *     **  ***   **  * **  *   **  *******     * 
                                                            **  *   *******    *** * **  *** ***** **  *** *     **    **
                                                            *** **  *     **   * ******* * ***   ***** * ****    ***   **
                                                            * ***** **    ***  ***     ***** **  *   *****  **   * **  * 
                                                            ***   *****   * ** * **    *   ***** **  *   ** ***  ***** **
                                                            * **  *   **  **********   **  *   ***** **  **** ** *   ****
                                                            ***** **  *** *        **  *** **  *   ***** *  *******  *   
                                                            *   ***** * ****       *** * ***** **  *   **** *     ** **  
                                                            **  *   *****  **      * *****   ***** **  *  ****    ****** 
   end

--------------------------------------------------------------------- */
/*------------------- paste of run from Console window -----------------------
Enter Rule (0 - 255): 30
   start
                                                            *                                                            
                                                           ***                                                           
                                                          **  *                                                          
                                                         ** ****                                                         
                                                        **  *   *                                                        
                                                       ** **** ***                                                       
                                                      **  *    *  *                                                      
                                                     ** ****  ******                                                     
                                                    **  *   ***     *                                                    
                                                   ** **** **  *   ***                                                   
                                                  **  *    * **** **  *                                                  
                                                 ** ****  ** *    * ****                                                 
                                                **  *   ***  **  ** *   *                                                
                                               ** **** **  *** ***  ** ***                                               
                                              **  *    * ***   *  ***  *  *                                              
                                             ** ****  ** *  * *****  *******                                             
                                            **  *   ***  **** *    ***      *                                            
                                           ** **** **  ***    **  **  *    ***                                           
                                          **  *    * ***  *  ** *** ****  **  *                                          
                                         ** ****  ** *  ******  *   *   *** ****                                         
                                        **  *   ***  ****     **** *** **   *   *                                        
                                       ** **** **  ***   *   **    *   * * *** ***                                       
                                      **  *    * ***  * *** ** *  *** ** * *   *  *                                      
                                     ** ****  ** *  *** *   *  ****   *  * ** ******                                     
                                    **  *   ***  ****   ** *****   * ***** *  *     *                                    
                                   ** **** **  ***   * **  *    * ** *     *****   ***                                   
                                  **  *    * ***  * ** * ****  ** *  **   **    * **  *                                  
                                 ** ****  ** *  *** *  * *   ***  **** * ** *  ** * ****                                 
                                **  *   ***  ****   **** ** **  ***    * *  ****  * *   *                                
                               ** **** **  ***   * **    *  * ***  *  ** ****   *** ** ***                               
                              **  *    * ***  * ** * *  ***** *  ******  *   * **   *  *  *                              
                             ** ****  ** *  *** *  * ****     ****     **** ** * * *********                             
                            **  *   ***  ****   **** *   *   **   *   **    *  * * *        *                            
                           ** **** **  ***   * **    ** *** ** * *** ** *  ***** * **      ***                           
                          **  *    * ***  * ** * *  **  *   *  * *   *  ****     * * *    **  *                          
                         ** ****  ** *  *** *  * **** **** ***** ** *****   *   ** * **  ** ****                         
                        **  *   ***  ****   **** *    *    *     *  *    * *** **  * * ***  *   *                        
                       ** **** **  ***   * **    **  ***  ***   ******  ** *   * *** * *  **** ***                       
                      **  *    * ***  * ** * *  ** ***  ***  * **     ***  ** ** *   * ****    *  *                      
                     ** ****  ** *  *** *  * ****  *  ***  *** * *   **  ***  *  ** ** *   *  ******                     
                    **  *   ***  ****   **** *   ******  ***   * ** ** ***  ******  *  ** *****     *                    
                   ** **** **  ***   * **    ** **     ***  * ** *  *  *  ***     ******  *    *   ***                   
                  **  *    * ***  * ** * *  **  * *   **  *** *  **********  *   **     ****  *** **  *                  
                 ** ****  ** *  *** *  * **** *** ** ** ***   ****         **** ** *   **   ***   * ****                 
                **  *   ***  ****   **** *    *   *  *  *  * **   *       **    *  ** ** * **  * ** *   *                
               ** **** **  ***   * **    **  *** *********** * * ***     ** *  *****  *  * * *** *  ** ***               
              **  *    * ***  * ** * *  ** ***   *           * * *  *   **  ****    ****** * *   ****  *  *              
             ** ****  ** *  *** *  * ****  *  * ***         ** * ***** ** ***   *  **      * ** **   *******             
            **  *   ***  ****   **** *   ****** *  *       **  * *     *  *  * ***** *    ** *  * * **      *            
           ** **** **  ***   * **    ** **      *****     ** *** **   ******** *     **  **  **** * * *    ***           
          **  *    * ***  * ** * *  **  * *    **    *   **  *   * * **        **   ** *** ***    * * **  **  *          
         ** ****  ** *  *** *  * **** *** **  ** *  *** ** **** ** * * *      ** * **  *   *  *  ** * * *** ****         
        **  *   ***  ****   **** *    *   * ***  ****   *  *    *  * * **    **  * * **** ********  * * *   *   *        
       ** **** **  ***   * **    **  *** ** *  ***   * ******  ***** * * *  ** *** * *    *       *** * ** *** ***       
      **  *    * ***  * ** * *  ** ***   *  ****  * ** *     ***     * * ****  *   * **  ***     **   * *  *   *  *      
     ** ****  ** *  *** *  * ****  *  * *****   *** *  **   **  *   ** * *   **** ** * ***  *   ** * ** ***** ******     
    **  *   ***  ****   **** *   ****** *    * **   **** * ** **** **  * ** **    *  * *  **** **  * *  *     *     *    
   ** **** **  ***   * **    ** **      **  ** * * **    * *  *    * *** *  * *  ***** ****    * *** *****   ***   ***   
  **  *    * ***  * ** * *  **  * *    ** ***  * * * *  ** *****  ** *   **** ****     *   *  ** *   *    * **  * **  *  
 ** ****  ** *  *** *  * **** *** **  **  *  *** * * ****  *    ***  ** **    *   *   *** *****  ** ***  ** * *** * **** 
**  *   ***  ****   **** *    *   * *** ******   * * *   ****  **  ***  * *  *** *** **   *    ***  *  ***  * *   * *   *
* **** **  ***   * **    **  *** ** *   *     * ** * ** **   *** ***  *** ****   *   * * ***  **  ******  *** ** ** ** **
  *    * ***  * ** * *  ** ***   *  ** ***   ** *  * *  * * **   *  ***   *   * *** ** * *  *** ***     ***   *  *  *  * 
****  ** *  *** *  * ****  *  * *****  *  * **  **** **** * * * *****  * *** ** *   *  * ****   *  *   **  * ************
*   ***  ****   **** *   ****** *    ****** * ***    *    * * * *    *** *   *  ** ***** *   * ****** ** *** *           
** **  ***   * **    ** **      **  **      * *  *  ***  ** * * **  **   ** *****  *     ** ** *      *  *   **          
   * ***  * ** * *  **  * *    ** *** *    ** *******  ***  * * * *** * **  *    ****   **  *  **    ****** ** *         
  ** *  *** *  * **** *** **  **  *   **  **  *      ***  *** * * *   * * ****  **   * ** ****** *  **      *  **        
***  ****   **** *    *   * *** **** ** *** ****    **  ***   * * ** ** * *   *** * ** *  *      **** *    ***** *      *
*  ***   * **    **  *** ** *   *    *  *   *   *  ** ***  * ** * *  *  * ** **   * *  *****    **    **  **     **    **
 ***  * ** * *  ** ***   *  ** ***  ****** *** *****  *  *** *  * ******* *  * * ** ****    *  ** *  ** *** *   ** *  ** 
 *  *** *  * ****  *  * *****  *  ***      *   *    ******   **** *       **** * *  *   *  *****  ****  *   ** **  **** *
 ****   **** *   ****** *    ******  *    *** ***  **     * **    **     **    * ***** *****    ***   **** **  * ***    *
**   * **    ** **      **  **     ****  **   *  *** *   ** * *  ** *   ** *  ** *     *    *  **  * **    * *** *  *  **
  * ** * *  **  * *    ** *** *   **   *** * *****   ** **  * ****  ** **  ****  **   ***  ***** *** * *  ** *   ******* 
*** *  * **** *** **  **  *   ** ** * **   * *    * **  * *** *   ***  * ***   *** * **  ***     *   * ****  ** **      *
*   **** *    *   * *** **** **  *  * * * ** **  ** * *** *   ** **  *** *  * **   * * ***  *   *** ** *   ***  * *    **
 * **    **  *** ** *   *    * ****** * * *  * ***  * *   ** **  * ***   **** * * ** * *  **** **   *  ** **  *** **  ** 
** * *  ** ***   *  ** ***  ** *      * * **** *  *** ** **  * *** *  * **    * * *  * ****    * * *****  * ***   * *** *
*  * ****  *  * *****  *  ***  **    ** * *    ****   *  * *** *   **** * *  ** * **** *   *  ** * *    *** *  * ** *   *
**** *   ****** *    ******  *** *  **  * **  **   * ***** *   ** **    * ****  * *    ** *****  * **  **   **** *  ** **
*    ** **      **  **     ***   **** *** * *** * ** *     ** **  * *  ** *   *** **  **  *    *** * *** * **    ****  * 
 *  **  * *    ** *** *   **  * **    *   * *   * *  **   **  * *** ****  ** **   * *** ****  **   * *   * * *  **   ****
 **** *** **  **  *   ** ** *** * *  *** ** ** ** **** * ** *** *   *   ***  * * ** *   *   *** * ** ** ** * **** * **   
 *    *   * *** **** **  *  *   * ****   *  *  *  *    * *  *   ** *** **  *** * *  ** *** **   * *  *  *  * *    * * * *
 **  *** ** *   *    * ******* ** *   * ************  ** ***** **  *   * ***   * ****  *   * * ** ********** **  ** * * *
** ***   *  ** ***  ** *       *  ** ** *           ***  *     * **** ** *  * ** *   **** ** * *  *          * ***  * * *
*  *  * *****  *  ***  **     *****  *  **         **  ****   ** *    *  **** *  ** **    *  * *****        ** *  *** * *
 ****** *    ******  *** *   **    ****** *       ** ***   * **  **  *****    ****  * *  ***** *    *      **  ****   * *
**      **  **     ***   ** ** *  **      **     **  *  * ** * *** ***    *  **   *** ****     **  ***    ** ***   * ** *
* *    ** *** *   **  * **  *  **** *    ** *   ** ****** *  * *   *  *  ***** * **   *   *   ** ***  *  **  *  * ** *  *
* **  **  *   ** ** *** * ******    **  **  ** **  *      **** ** ********     * * * *** *** **  *  ****** ****** *  ****
  * *** **** **  *  *   * *     *  ** *** ***  * ****    **    *  *       *   ** * * *   *   * ******      *      ****   
 ** *   *    * ******* ** **   *****  *   *  *** *   *  ** *  ******     *** **  * * ** *** ** *     *    ***    **   *  
 *  ** ***  ** *       *  * * **    **** *****   ** *****  ****     *   **   * *** * *  *   *  **   ***  **  *  ** * ****
*****  *  ***  **     ***** * * *  **    *    * **  *    ***   *   *** ** * ** *   * ***** ***** * **  *** ******  * *   
*    ******  *** *   **     * * **** *  ***  ** * ****  **  * *** **   *  * *  ** ** *     *     * * ***   *     *** **  
**  **     ***   ** ** *   ** * *    ****  ***  * *   *** *** *   * * ***** ****  *  **   ***   ** * *  * ***   **   * **
* *** *   **  * **  *  ** **  * **  **   ***  *** ** **   *   ** ** * *     *   ****** * **  * **  * **** *  * ** * ** * 
  *   ** ** *** * ******  * *** * *** * **  ***   *  * * *** **  *  * **   *** **      * * *** * *** *    **** *  * *  **
   end

--------------------------------------------------------------------- */

