
/* Name: Shiv Patel 
Assignment 7 */


public class Foothill
{
   public static void main(String[] args)
   {
      String[] sImageIn =
         {
            "                                      ",
            "                                      ",
            "                                      ",
            "* * * * * * * * * * * * * * * * *     ",
            "*                                *    ",
            "**** * ****** ** ****** *** ****      ",
            "* ********************************    ",
            "*    *   *  * *  *   *  *   *  *      ",
            "* **    *      *   **    *       *    ",
            "****** ** *** **  ***** * * *         ",
            "* ***  ****    * *  **        ** *    ",
            "* * *   * **   *  *** *   *  * **     ",
            "**********************************    "
         };

      String[] sImageIn_2 =
         {
            "                                          ",
            "                                          ",
            "* * * * * * * * * * * * * * * * * * *     ",
            "*                                    *    ",
            "**** *** **   ***** ****   *********      ",
            "* ************ ************ **********    ",
            "** *      *    *  * * *         * *       ",
            "***   *  *           * **    *      **    ",
            "* ** * *  *   * * * **  *   ***   ***     ",
            "* *           **    *****  *   **   **    ",
            "****  *  * *  * **  ** *   ** *  * *      ",
            "**************************************    "
         };

      BarcodeImage bc = new BarcodeImage(sImageIn);
      DataMatrix dm = new DataMatrix(bc);

      // First secret message
      dm.translateImageToText();
      dm.displayTextToConsole();
      dm.displayImageToConsole();

      // second secret message
      bc = new BarcodeImage(sImageIn_2);
      dm.scan(bc);
      dm.translateImageToText();
      dm.displayTextToConsole();
      dm.displayImageToConsole();

      // create your own message
      dm.readText("CS 1B rocks more than Zeppelin");
      dm.generateImageFromText();
      dm.displayTextToConsole();
      dm.displayImageToConsole();
   }   
}

//BarcodeIO interface ---------------------------------------------------------
interface BarcodeIO
{
   public boolean scan(BarcodeImage bc);
   public boolean readText(String text);
   public boolean generateImageFromText();
   public boolean translateImageToText();
   public void displayTextToConsole();
   public void displayImageToConsole();
}

//BarcodeImage class ---------------------------------------------------------
class BarcodeImage implements Cloneable 
{
   public static final int MAX_HEIGHT = 30;
   public static final int MAX_WIDTH = 65;

   private boolean imageData[][];

   public BarcodeImage()
   {
      imageData = new boolean[MAX_HEIGHT][MAX_WIDTH];

      for(int i = 0; i < MAX_HEIGHT; i++)
      {
         for(int j = 0; j < MAX_WIDTH; j++)
         {
            imageData[i][j] = false;
         }
      }
   }

   public BarcodeImage(String[] str_data)
   {
      this();

      if( !checkSize(str_data) )
      {
         return; // error
      }

      for(int i = 0; i < str_data.length; i++)
      {
         for(int j = 0; j < str_data[0].length(); j++)
         {
            if(str_data[i].charAt(j) == '*') 
            {
               setPixel(MAX_HEIGHT-(str_data.length - i), j, true);
            } else {
               setPixel(MAX_HEIGHT-(str_data.length - i), j, false);
            }
         }
      }

   }

   boolean getPixel(int row, int col)
   {
      return imageData[row][col];
   }

   boolean setPixel(int row, int col, boolean value)
   {
      if(row >= MAX_HEIGHT || col >= MAX_WIDTH)
      {
         return false;
      } else {
         imageData[row][col] = value;
         return true;
      }
   }

   private boolean checkSize(String[] data) 
   {
      if(data == null)
      {
         return false;
      }
      if(data.length > MAX_HEIGHT)
      {
         return false;
      }
      return true;
   }

   public Object clone() throws CloneNotSupportedException 
   {
      BarcodeImage newBC = (BarcodeImage)super.clone();

      for(int i = 0; i < MAX_HEIGHT; i++)
      {
         for(int j = 0; j < MAX_WIDTH; j++)
         {
            newBC.imageData[i][j] = this.getPixel(i, j);
         }
      }

      return newBC;
   }


   public void display() {
      int row, col;

      System.out.println();
      for(col = 0; col < MAX_WIDTH + 2; col++)
      {
         System.out.print("-");
      }
      System.out.println();

      for(row = 0; row < MAX_HEIGHT; row++)
      {
         System.out.print("|");
         for(col = 0; col < MAX_WIDTH; col++)
         {
            if(getPixel(row, col) == true) 
            {
               System.out.print("*");
            } else {
               System.out.print(" ");
            }
         }
         System.out.println("|");
      }

      for(col = 0; col < MAX_WIDTH + 2; col++)
      {
         System.out.print("-");
      }
   }

}


// DataMatrix class ---------------------------------------------------------
class DataMatrix implements BarcodeIO 
{
   public static final char BLACK_CHAR = '*';
   public static final char WHITE_CHAR = ' ';

   private BarcodeImage image;
   private String text;
   private int actualWidth;
   private int actualHeight;

   public DataMatrix() 
   {
      actualWidth = 0;
      actualHeight = 0;
      text = "";
      image = new BarcodeImage();
   }

   public DataMatrix(BarcodeImage image) 
   {
      this();
      scan(image);
   }

   public DataMatrix(String text) 
   {
      this();
      readText(text);
   }

   //A mutator for text
   public boolean readText(String text) 
   {
      this.text = text;
      return true;
   }

   //A mutator for image. Call clone() to create copy of BarcodeImage
   public boolean scan(BarcodeImage image) 
   {
      try 
      {
         this.image = (BarcodeImage) image.clone();
      } catch(CloneNotSupportedException e)
      {
         System.out.println("Clone not supported Exception");
      }

      actualHeight = computeSignalHeight();
      actualWidth = computeSignalWidth();

      return true;
   }

   private int computeSignalWidth() 
   {
      int width = 0;
      for(int i = 0; i < image.MAX_WIDTH; i++)
      {
         if(image.getPixel(image.MAX_HEIGHT-1, i) == true) 
         {
            width++;
         } else {
            return width;
         }
      }

      return width;
   }

   private int computeSignalHeight() 
   {
      int height = 0;
      for(int i = image.MAX_HEIGHT - 1; i >= 0; i--) 
      {
         if(image.getPixel(i, 0) == true) 
         {
            height++;
         } else {
            return height;
         }
      }

      return height;

   }

   //Convert text to BarcodeImage
   public boolean generateImageFromText() 
   {
      //Creating the spine.
      for(int i = 0; i < 10; i++)
      {
         image.setPixel(image.MAX_HEIGHT-i, 0, true);
      }

      //writing characters. 
      for(int i = 0; i < text.length(); i++)
      {
         writeCharToCol(i+1, (int)text.charAt(i));
      }

      //alternating border.
      for(int i = 0; i < 10; i++)
      {
         if(i%2 == 0)
         {
            image.setPixel(image.MAX_HEIGHT-i, text.length()+1, true);
         }
      }

      actualHeight = computeSignalHeight();
      actualWidth = computeSignalWidth();
      return true;
   }

   //Convert BarcodeImage to text
   public boolean translateImageToText() 
   {
      StringBuilder sb = new StringBuilder();
      for(int i = 1; i < actualWidth - 1; i++)
      {
         sb.append(readCharFromCol(i));
      }

      text = sb.toString();

      return false;
   }


   public void displayImageToConsole() 
   {
      int row, col;

      System.out.println();
      for(col = 0; col < actualWidth + 2; col++)
      {
         System.out.print("-");
      }
      System.out.println();

      for(row = image.MAX_HEIGHT-actualHeight; row < image.MAX_HEIGHT; row++)
      {
         System.out.print("|");
         for(col = 0; col < actualWidth; col++)
         {
            if(image.getPixel(row, col) == true) 
            {
               System.out.print("*");
            } else {
               System.out.print(" ");
            }
         }
         System.out.println("|");
      }

      for(col = 0; col < actualWidth + 2; col++)
      {
         System.out.print("-");
      }
      System.out.println();
   }


   public void displayTextToConsole() 
   {
      System.out.println(text);
   }


   //Converts a specified column in BarcodeImage and returns the char value.
   private char readCharFromCol(int col) 
   {
      int numVal = 0;
      for(int i = 0; i < actualHeight-2; i++)
      {
         if(image.getPixel((image.MAX_HEIGHT-1)-(i+1),col)) 
         {
            numVal += Math.pow(2, i);
         }
      }

      return ((char)numVal);
   }

   private boolean writeCharToCol(int col, int code) 
   {
      String binary = Integer.toBinaryString(code);
      image.setPixel(image.MAX_HEIGHT, col, true);

      if(col%2 == 0) 
      {
         image.setPixel(image.MAX_HEIGHT-(binary.length()+1), col, true);
      }
      for(int i = 0; i < binary.length(); i++)
      {
         if(binary.charAt(i) == '1')
         {
            image.setPixel((image.MAX_HEIGHT-1)-(i+1), col, true);
         } else {
            image.setPixel((image.MAX_HEIGHT-1)-(i+1), col, false);
         }
      }

      return true;
   }

   public void displayRawImage() 
   {
      image.display();
   }

   public int getActualWidth() 
   {
      return actualWidth;
   }

   public int getActualHeight() 
   {
      return actualHeight;
   }

}


/*------------------- paste of run from Console window -----------------------
Don't forget to remove the tabs!

------------------------------------
|* * * * * * * * * * * * * * * * * |
|*                                *|
|**** * ****** ** ****** *** ****  |
|* ********************************|
|*    *   *  * *  *   *  *   *  *  |
|* **    *      *   **    *       *|
|****** ** *** **  ***** * * *     |
|* ***  ****    * *  **        ** *|
|* * *   * **   *  *** *   *  * ** |
|**********************************|
------------------------------------
You did it!  Great work.  Celebrate.

----------------------------------------
|* * * * * * * * * * * * * * * * * * * |
|*                                    *|
|**** *** **   ***** ****   *********  |
|* ************ ************ **********|
|** *      *    *  * * *         * *   |
|***   *  *           * **    *      **|
|* ** * *  *   * * * **  *   ***   *** |
|* *           **    *****  *   **   **|
|****  *  * *  * **  ** *   ** *  * *  |
|**************************************|
----------------------------------------
CS 1B rocks more than Zeppelin

----------------------------------------
|* * * * * * * * * * * * * * * * * * * |
|*                                    *|
|****  * **** ** **  * * *  * * *****  |
|*** ** *****  **     * *      ********|
|*       *    ** * *  *  *  ** *** *   |
|*       * *  **    * * *    ***     **|
|* *    *   *   *  *    * **    *  *** |
|*   *  ***** **** ****  *********   **|
|******************************** * *  |
|**************************************|
----------------------------------------

--------------------------------------------------------------------- */







