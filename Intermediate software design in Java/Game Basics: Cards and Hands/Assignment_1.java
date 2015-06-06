/* Name: Shiv Patel 
   Assignment 1 */
public class Foothill
{
   public static void main(String[] args)
   {
      Card card1, card2, card3, card4, card5;

      card1 = new Card('3', Card.Suit.clubs);
      card2 = new Card('t', Card.Suit.clubs);
      card3 = new Card('9', Card.Suit.hearts);

      Hand hand = new Hand();
      // Using takeCard to populate the hand.
      while(true)
      {  
         if(!hand.takeCard(card1))
         {
            break;
         }
         if(!hand.takeCard(card2))
         {
            break;
         }        
         if(!hand.takeCard(card3))
         {
            break;
         }
      }
      System.out.println("Hand full\nAfter deal");
      // Displaying the hand using toString().
      System.out.println(hand.toString() + "\n"); 

      for(int i = 0; i < Hand.MAX_CARDS ; i++)
      {
         // Displaying the card played as it is plays.
         Card played = hand.playCard();
         System.out.println(played.toString());
      }      
      // Displaying the empty hand.
      System.out.println(hand.toString());

      // Inspecting cards
      System.out.println("\nTesting inspectCard()");
      card4 = new Card('9', Card.Suit.hearts);
      card5 = new Card('b', Card.Suit.clubs);
      hand.takeCard(card4);
      hand.takeCard(card5); 
      Card inspect1 = hand.inspectCard(0);
      Card inspect2 = hand.inspectCard(3);
      System.out.println(inspect1);
      System.out.println(inspect2);
   }
}


class Card
{
   // A Public enum Type.
   public enum Suit
   {
      clubs, diamonds, hearts, spades
   }

   // Private data.
   private char value;
   private Suit suit;
   private boolean errorFlag;

   // Card constructor.
   public Card(char value, Suit suit)
   {
      set(value, suit);        
   }

   // Default constructor.
   public Card()
   {
      this('A', Suit.spades);
   }

   // Returns true if all the members are identical and false, otherwise.
   public boolean equals(Card card)
   {
      if( this.suit == card.suit && this.value == card.value)
      {   
         return true;
      }
      else
      {
         return false;
      }
   }

   // A mutator.
   public boolean set(char value, Suit suit)
   {
      char upVal; // For upcasing char.
      boolean validate;

      // Suit is enum, so no test needed: all suits are allowed.
      this.suit = suit;

      // Converting value to uppercase to simplify.
      upVal = Character.toUpperCase(value);

      // Checking if value is valid or not.
      validate = isValid(upVal, suit);
      if (validate)
      {
         this.value = upVal;
         this.errorFlag = false;
      }
      else
      {
         this.errorFlag = true;
         validate = false;     
         this.value = 'A';
      }
      return validate;
   }

   // Accessors.
   public char getVal()
   {
      return value;
   }

   public Suit getSuit()
   {
      return suit;
   }

   public boolean getErrorFlag()
   {
      return this.errorFlag;
   }

   private boolean isValid(char upVal, Suit suit)
   {     
      //Checking if upVal is a valid parameter or not.
      if (upVal == 'A' || upVal == 'K' || upVal == 'Q' || upVal == 'J'
            || upVal == 'T' || (upVal >= '2' && upVal <= '9'))
      {
         return true;
      }
      else
      {
         return false;
      }
   }

   // stringizer.
   public String toString()
   {
      String retVal;
      if (getErrorFlag())
      {
         retVal = "** illegal **";
         return retVal;
      }
      else
      {
         retVal = value + " of " + suit;
         return retVal;
      }
   }
}

class Hand{

   // Static class constant.
   public static int MAX_CARDS = 50;

   // Private data.  
   Card[] myCards;
   int numCards;

   // Default constructor.
   public Hand()
   { 
      myCards = new Card[MAX_CARDS];
      resetHand();
   }

   // Resetting hand.
   public void resetHand()
   {
      for(int i = 0; i < MAX_CARDS; i++)
      {
         myCards[i] = null;
      }
      numCards = 0;
   }

   // Adds a card to the next available position in the myCards array.
   public boolean takeCard(Card card)
   {
      if(numCards < MAX_CARDS)
      { 
         myCards[numCards++] = card;
         return true;
      }
      else 
      {
         return false;
      }
   }

   // Returns and removes the card in the top occupied position of the array.
   public Card playCard()
   {      
      Card retPlayCard =  myCards[numCards - 1];
      myCards[numCards - 1] = null; 
      numCards-- ;
      return retPlayCard;
   }

   // stringizer
   public String toString()
   {
      if(numCards == 0)
      {
         return "Hand = ( )";
      }

      String retHand = myCards[0].toString();//Adjusting "," for correct output.
      for(int i = 1; i < numCards ; i++)
      {
         retHand = retHand + ", " + myCards[i].toString();
      }
      retHand = new String("Hand = ( " + retHand + " )");
      return retHand;
   }

   // Accessors.
   public int getnumCards()
   {
      return numCards;
   }

   // Inspecting if card is legal or not.
   public Card inspectCard(int k)
   {
      if(numCards == 0 || k < 0 || k > numCards)
      {
         //This will generate the ** illegal ** message from Card.toString()
         return new Card('F', Card.Suit.spades);
      }
      else 
      {
         return myCards[k];
      }
   }
}

/*------------------- paste of run from Console window -----------------------
Hand full
After deal
Hand = ( 3 of clubs, T of clubs, 9 of hearts, 3 of clubs, T of clubs, 9 of heart
s, 3 of clubs, T of clubs, 9 of hearts, 3 of clubs, T of clubs, 9 of hearts, 3 o
f clubs, T of clubs, 9 of hearts, 3 of clubs, T of clubs, 9 of hearts, 3 of club
s, T of clubs, 9 of hearts, 3 of clubs, T of clubs, 9 of hearts, 3 of clubs, T o
f clubs, 9 of hearts, 3 of clubs, T of clubs, 9 of hearts, 3 of clubs, T of club
s, 9 of hearts, 3 of clubs, T of clubs, 9 of hearts, 3 of clubs, T of clubs, 9 o
f hearts, 3 of clubs, T of clubs, 9 of hearts, 3 of clubs, T of clubs, 9 of hear
ts, 3 of clubs, T of clubs, 9 of hearts, 3 of clubs, T of clubs )

T of clubs
3 of clubs
9 of hearts
T of clubs
3 of clubs
9 of hearts
T of clubs
3 of clubs
9 of hearts
T of clubs
3 of clubs
9 of hearts
T of clubs
3 of clubs
9 of hearts
T of clubs
3 of clubs
9 of hearts
T of clubs
3 of clubs
9 of hearts
T of clubs
3 of clubs
9 of hearts
T of clubs
3 of clubs
9 of hearts
T of clubs
3 of clubs
9 of hearts
T of clubs
3 of clubs
9 of hearts
T of clubs
3 of clubs
9 of hearts
T of clubs
3 of clubs
9 of hearts
T of clubs
3 of clubs
9 of hearts
T of clubs
3 of clubs
9 of hearts
T of clubs
3 of clubs
9 of hearts
T of clubs
3 of clubs
Hand = ( )

Testing inspectCard()
9 of hearts
 ** illegal **

--------------------------------------------------------------------- */

