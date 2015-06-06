import java.util.Random;
import java.util.Scanner;

/* Name: Shiv Patel 
   Assignment 2 */
public class Foothill
{
   public static void main(String[] args)
   {
      //Phase1: part1 (testing of Class Deck with two deck)
      Deck deck1 = new Deck(2);
      for(int i = 0; i < 52*2; i++)
      {
         // Displaying the cards dealed as it is dealed.
         Card dealedCards = deck1.dealCard();
         System.out.print(dealedCards.toString() + " / ");
      } 
      System.out.println("\n"); 

      deck1.init(2);
      deck1.shuffle();
      for(int i = 0; i < 52*2; i++)
      {
         Card dealedCards = deck1.dealCard();
         System.out.print(dealedCards.toString() + " / ");
      } 
      System.out.println("\n"); 

      //Phase1: part2 (testing of Class Deck with single deck)
      deck1.init(1);
      for(int i = 0; i < 52; i++)
      {
         Card dealedCards = deck1.dealCard();
         System.out.print(dealedCards.toString() + " / ");
      } 
      System.out.println("\n"); 

      deck1.init(1);
      deck1.shuffle();
      for(int i = 0; i < 52; i++)
      {
         Card dealedCards = deck1.dealCard();
         System.out.print(dealedCards.toString() + " / ");
      } 
      System.out.println("\n");
      //Asking the user to continue to next phase.
      System.out.println("Press any key to continue . . .");
      Scanner enter1 = new Scanner(System.in);
      enter1.nextLine();

      //Phase2 (testing the Deck and Hand Classes)
      int input = 0;
      Scanner in = new Scanner(System.in);//user input.

      //loop until the user provides a legal input.
      while(input < 1 || input > 10) 
      {
         System.out.println("How many hands? (1-10, please): ");
         input = in.nextInt();
      }

      Deck deck2 = new Deck();
      Hand[] hands = new Hand[input];

      for(int i =0; i < hands.length; i++){
         hands[i] = new Hand();
      }
      //dealing a deck into user provided hands.
      while(true){
         Card dealedCards = null;
         for(int i=0; i < hands.length; i++){
            dealedCards = deck2.dealCard();
            if(dealedCards == null){
               break;
            }
            hands[i].takeCard(dealedCards);
         }
         if(dealedCards == null){
            break;
         }
      }
      //Printing hands
      System.out.println();
      System.out.println("Here are our hands, from unshuffled deck: ");
      for(int i=0; i < hands.length; i++){
         System.out.println(hands[i].toString());
         System.out.println();
      }

      //Reseting hand
      for(int i=0; i<hands.length; i++){
         hands[i].resetHand();
      }

      deck2.init(1);
      deck2.shuffle();

      //dealing a deck again into user provided hands.
      while(true){
         Card dealedCards = null;
         for(int i=0; i < hands.length; i++){
            dealedCards = deck2.dealCard();
            if(dealedCards == null){
               break;
            }
            hands[i].takeCard(dealedCards);
         }
         if(dealedCards == null){
            break;
         }
      }

      //Printing shuffled hands
      System.out.println();
      System.out.println("Here are our hands, from SHUFFLED deck: ");
      for(int i=0; i < hands.length; i++){
         System.out.println(hands[i].toString());
         System.out.println();
      }

   }
}

//Card class ----------------------------------------------------------------
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

   public Card(char value)
   {
      this(value, Suit.spades);
   }

   // Default constructor.
   public Card()
   {
      this('A', Suit.spades);
   }

   //Copy Constructor
   public Card(Card card)
   {
      this(card.value, card.suit);
   }

   // Returns true if all the members are identical and false, otherwise.
   public boolean equals(Card card)
   {
      if( this.suit == card.suit && this.value == card.value
            && this.errorFlag == card.errorFlag)
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

   //Helper
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

//Hand class ----------------------------------------------------------------
class Hand{

   // Static class constant.
   public static final int MAX_CARDS = 100;

   // Private data.  
   private Card[] myCards;
   private int numCards;

   // Default constructor.
   public Hand()
   { 
      myCards = new Card[MAX_CARDS];
      resetHand();
   }

   // Resetting hand.
   public void resetHand()
   {
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

      String retHand = myCards[0].toString();//Adjusting "," for correct output
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

//Deck class ----------------------------------------------------------------
class Deck{

   // public static class constant
   public static final int MAX_CARDS = 6*52;

   // Private static data.
   private static Card[] masterPack;
   static boolean isExecuted; //Check if this is allowed.

   // Private data.
   private Card[] cards;
   private int topCard;
   private int numPacks;


   public Deck(int numPacks)
   {
      allocateMasterPack();
      this.cards = masterPack;
      init(numPacks);
   }

   public Deck()
   {
      this(1);
   }

   public void init(int numPacks)
   {
      this.numPacks = numPacks;
      gettopCard();
      if((topCard) <= MAX_CARDS && numPacks != 0)
      {
         cards = new Card[topCard];
         for(int k=0; k<cards.length; k++)
            cards[k] = new Card();
         // re-populate cards[] with the standard 52 × numPacks cards.
         for(int k=0; k<numPacks; k++)
         {
            for(int i=52*k, j = 0; i<52*k+52; i++, j++)
            {
               cards[i] = masterPack[j];
            }
         }
      }
      else
         return;
   }

   //Shuffles the cards[].
   public void shuffle()
   {
      Random rand = new Random();
      for (int k = cards.length - 1; k > 0; k--)
      {
         //using nextInt(int bound) to get a 
         //random number in the range of 0 to cards.length
         int randIndex = rand.nextInt(k + 1);
         //Making the swap.
         Card tempCard = cards[randIndex]; 
         cards[randIndex] = cards[k]; 
         cards[k] = tempCard;
      }
   }

   public Card dealCard() 
   { 
      if(topCard == 0)
         return null;
      Card retCard =  cards[topCard - 1];
      cards[topCard - 1] = null; 
      topCard-- ;
      return retCard;
   }

   // Accessors.
   public int gettopCard()
   {
      topCard = 52*numPacks;
      return topCard;
   }

   // Accessor for an individual card.
   public Card inspectCard(int k)
   {
      if(topCard == 0 || k < 0 || k > topCard)
      {
         //This will generate the ** illegal ** message from Card.toString()
         return new Card('F', Card.Suit.spades);
      }
      else 
      {
         return cards[k];
      }
   }

   private static void allocateMasterPack()
   {
      if(!isExecuted)
      {
         masterPack = new Card[52];
         Card.Suit suit;
         int k, j;
         char val;

         for (k = 0; k < masterPack.length; k++)
            masterPack[k] = new Card();

         for (k = 0; k<4; k++)
         {
            // Set the suit for this loop pass.
            switch(k)
            {
            case 0:
               suit = Card.Suit.clubs; break;
            case 1:
               suit = Card.Suit.diamonds; break;
            case 2:
               suit = Card.Suit.hearts; break;
            case 3:
               suit = Card.Suit.spades; break;
            default:
               suit = Card.Suit.spades; break;
            }

            masterPack[13*k].set('A', suit);
            for (val='2', j = 1; val<='9'; val++, j++)
               masterPack[13*k + j].set(val, suit);
            masterPack[13*k+9].set('T', suit);
            masterPack[13*k+10].set('J', suit);
            masterPack[13*k+11].set('Q', suit);
            masterPack[13*k+12].set('K', suit);
         }
         isExecuted = true;
      }
      else
         return;
   }
}

/*------------------- paste of run from Console window -----------------------
K of spades / Q of spades / J of spades / T of spades / 9 of spades / 8 of spade
s / 7 of spades / 6 of spades / 5 of spades / 4 of spades / 3 of spades / 2 of s
pades / A of spades / K of hearts / Q of hearts / J of hearts / T of hearts / 9 
of hearts / 8 of hearts / 7 of hearts / 6 of hearts / 5 of hearts / 4 of hearts 
/ 3 of hearts / 2 of hearts / A of hearts / K of diamonds / Q of diamonds / J of
 diamonds / T of diamonds / 9 of diamonds / 8 of diamonds / 7 of diamonds / 6 of
 diamonds / 5 of diamonds / 4 of diamonds / 3 of diamonds / 2 of diamonds / A of
 diamonds / K of clubs / Q of clubs / J of clubs / T of clubs / 9 of clubs / 8 o
f clubs / 7 of clubs / 6 of clubs / 5 of clubs / 4 of clubs / 3 of clubs / 2 of 
clubs / A of clubs / K of spades / Q of spades / J of spades / T of spades / 9 o
f spades / 8 of spades / 7 of spades / 6 of spades / 5 of spades / 4 of spades /
 3 of spades / 2 of spades / A of spades / K of hearts / Q of hearts / J of hear
ts / T of hearts / 9 of hearts / 8 of hearts / 7 of hearts / 6 of hearts / 5 of 
hearts / 4 of hearts / 3 of hearts / 2 of hearts / A of hearts / K of diamonds /
 Q of diamonds / J of diamonds / T of diamonds / 9 of diamonds / 8 of diamonds /
 7 of diamonds / 6 of diamonds / 5 of diamonds / 4 of diamonds / 3 of diamonds /
 2 of diamonds / A of diamonds / K of clubs / Q of clubs / J of clubs / T of clu
bs / 9 of clubs / 8 of clubs / 7 of clubs / 6 of clubs / 5 of clubs / 4 of clubs
 / 3 of clubs / 2 of clubs / A of clubs / 

7 of hearts / 5 of diamonds / A of spades / 8 of clubs / K of spades / 7 of club
s / J of hearts / Q of diamonds / 6 of clubs / 5 of spades / K of clubs / 6 of h
earts / 8 of clubs / 3 of diamonds / T of clubs / T of hearts / 5 of diamonds / 
8 of spades / 6 of spades / J of diamonds / 7 of spades / K of spades / Q of spa
des / 4 of clubs / 7 of spades / 3 of hearts / J of hearts / 3 of hearts / 6 of 
hearts / T of diamonds / 9 of clubs / 4 of hearts / 8 of hearts / 3 of spades / 
T of hearts / K of diamonds / A of clubs / K of hearts / K of diamonds / A of di
amonds / T of spades / 8 of spades / J of diamonds / 5 of hearts / Q of clubs / 
4 of diamonds / A of spades / 3 of diamonds / 6 of diamonds / 8 of hearts / 8 of
 diamonds / 9 of hearts / Q of diamonds / Q of hearts / K of hearts / 6 of clubs
 / Q of spades / 4 of hearts / 9 of diamonds / 9 of clubs / J of clubs / 2 of cl
ubs / J of spades / 4 of diamonds / 9 of hearts / 4 of clubs / J of spades / 2 o
f clubs / 9 of diamonds / 9 of spades / T of clubs / 4 of spades / J of clubs / 
A of hearts / 7 of clubs / 2 of diamonds / 5 of spades / T of diamonds / 5 of cl
ubs / 6 of diamonds / 8 of diamonds / 2 of spades / 7 of diamonds / A of diamond
s / 9 of spades / 6 of spades / T of spades / 2 of hearts / Q of clubs / 3 of sp
ades / A of clubs / 2 of spades / 3 of clubs / 5 of hearts / 7 of hearts / Q of 
hearts / 7 of diamonds / 5 of clubs / 3 of clubs / 2 of hearts / 4 of spades / A
 of hearts / K of clubs / 2 of diamonds / 

K of spades / Q of spades / J of spades / T of spades / 9 of spades / 8 of spade
s / 7 of spades / 6 of spades / 5 of spades / 4 of spades / 3 of spades / 2 of s
pades / A of spades / K of hearts / Q of hearts / J of hearts / T of hearts / 9 
of hearts / 8 of hearts / 7 of hearts / 6 of hearts / 5 of hearts / 4 of hearts 
/ 3 of hearts / 2 of hearts / A of hearts / K of diamonds / Q of diamonds / J of
 diamonds / T of diamonds / 9 of diamonds / 8 of diamonds / 7 of diamonds / 6 of
 diamonds / 5 of diamonds / 4 of diamonds / 3 of diamonds / 2 of diamonds / A of
 diamonds / K of clubs / Q of clubs / J of clubs / T of clubs / 9 of clubs / 8 o
f clubs / 7 of clubs / 6 of clubs / 5 of clubs / 4 of clubs / 3 of clubs / 2 of 
clubs / A of clubs / 

6 of hearts / J of clubs / 2 of spades / T of spades / K of diamonds / A of spad
es / A of hearts / 7 of hearts / 6 of spades / J of spades / J of diamonds / 6 o
f clubs / 8 of hearts / 3 of clubs / 5 of spades / 4 of clubs / 7 of spades / 5 
of diamonds / 2 of diamonds / 8 of clubs / 4 of diamonds / 6 of diamonds / T of 
clubs / Q of clubs / 9 of hearts / 9 of diamonds / 9 of spades / 8 of diamonds /
 4 of spades / 3 of hearts / 2 of clubs / J of hearts / Q of diamonds / 4 of hea
rts / A of clubs / 7 of diamonds / Q of spades / 5 of clubs / Q of hearts / A of
 diamonds / T of diamonds / 3 of diamonds / 2 of hearts / 9 of clubs / 8 of spad
es / 5 of hearts / 7 of clubs / K of hearts / K of spades / T of hearts / K of c
lubs / 3 of spades / 

Press any key to continue . . .

How many hands? (1-10, please): 
6

Here are our hands, from unshuffled deck: 
Hand = ( K of spades, 7 of spades, A of spades, 8 of hearts, 2 of hearts, 9 of d
iamonds, 3 of diamonds, T of clubs, 4 of clubs )

Hand = ( Q of spades, 6 of spades, K of hearts, 7 of hearts, A of hearts, 8 of d
iamonds, 2 of diamonds, 9 of clubs, 3 of clubs )

Hand = ( J of spades, 5 of spades, Q of hearts, 6 of hearts, K of diamonds, 7 of
 diamonds, A of diamonds, 8 of clubs, 2 of clubs )

Hand = ( T of spades, 4 of spades, J of hearts, 5 of hearts, Q of diamonds, 6 of
 diamonds, K of clubs, 7 of clubs, A of clubs )

Hand = ( 9 of spades, 3 of spades, T of hearts, 4 of hearts, J of diamonds, 5 of
 diamonds, Q of clubs, 6 of clubs )

Hand = ( 8 of spades, 2 of spades, 9 of hearts, 3 of hearts, T of diamonds, 4 of
 diamonds, J of clubs, 5 of clubs )


Here are our hands, from SHUFFLED deck: 
Hand = ( J of spades, 2 of spades, 7 of diamonds, 6 of spades, T of clubs, 9 of 
diamonds, 9 of hearts, 8 of spades, 6 of diamonds )

Hand = ( 3 of spades, 9 of clubs, 8 of hearts, J of hearts, 6 of clubs, 5 of dia
monds, 7 of spades, A of hearts, 4 of hearts )

Hand = ( 3 of clubs, 5 of clubs, 7 of hearts, 4 of spades, Q of clubs, A of diam
onds, K of diamonds, K of spades, 6 of hearts )

Hand = ( 3 of hearts, 5 of hearts, 2 of hearts, 4 of clubs, 5 of spades, Q of sp
ades, 3 of diamonds, T of hearts, T of spades )

Hand = ( 2 of diamonds, 7 of clubs, 2 of clubs, J of clubs, Q of hearts, K of he
arts, Q of diamonds, 8 of clubs )

Hand = ( 4 of diamonds, A of spades, 8 of diamonds, T of diamonds, 9 of spades, 
J of diamonds, K of clubs, A of clubs )


--------------------------------------------------------------------- */

