package PJ4;
import java.util.*;

/*
 * Ref: http://en.wikipedia.org/wiki/Video_poker
 *      http://www.freeslots.com/poker.htm
 *
 *
 * Short Description and Poker rules:
 *
 * Video poker is also known as draw poker. 
 * The dealer uses a 52-card deck, which is played fresh after each playerHand. 
 * The player is dealt one five-card poker playerHand. 
 * After the first draw, which is automatic, you may hold any of the cards and draw 
 * again to replace the cards that you haven't chosen to hold. 
 * Your cards are compared to a table of winning combinations. 
 * The object is to get the best possible combination so that you earn the highest 
 * payout on the bet you placed. 
 *
 * Winning Combinations
 *  
 * 1. One Pair: one pair of the same card
 * 2. Two Pair: two sets of pairs of the same card denomination. 
 * 3. Three of a Kind: three cards of the same denomination. 
 * 4. Straight: five consecutive denomination cards of different suit. 
 * 5. Flush: five non-consecutive denomination cards of the same suit. 
 * 6. Full House: a set of three cards of the same denomination plus 
 * 	a set of two cards of the same denomination. 
 * 7. Four of a kind: four cards of the same denomination. 
 * 8. Straight Flush: five consecutive denomination cards of the same suit. 
 * 9. Royal Flush: five consecutive denomination cards of the same suit, 
 * 	starting from 10 and ending with an ace
 *
 */


/* This is the video poker game class.
 * It uses Decks and Card objects to implement video poker game.
 * Please do not modify any data fields or defined methods
 * You may add new data fields and methods
 * Note: You must implement defined methods
 */



public class VideoPoker {

    // default constant values
    private static final int startingBalance=100;
    private static final int numberOfCards=5;

    // default constant payout value and playerHand types
    private static final int[] multipliers={1,2,3,5,6,10,25,50,1000};
    private static final String[] goodHandTypes={ 
	  "One Pair" , "Two Pairs" , "Three of a Kind", "Straight", "Flush	", 
	  "Full House", "Four of a Kind", "Straight Flush", "Royal Flush" };

    // must use only one deck
    private final Decks oneDeck;

    // holding current poker 5-card hand, balance, bet    
    private List<Card> playerHand;
    private int playerBalance;
    private int playerBet;

    /** default constructor, set balance = startingBalance */
    public VideoPoker()
    {
	this(startingBalance);
    }

    /** constructor, set given balance */
    public VideoPoker(int balance)
    {
	this.playerBalance= balance;
        oneDeck = new Decks(1, false);
    }

    /** This displays the payout table based on multipliers and goodHandTypes arrays */
    private void showPayoutTable()
    { 
	System.out.println("\n\n");
	System.out.println("Payout Table   	      Multiplier   ");
	System.out.println("=======================================");
	int size = multipliers.length;
	for (int i=size-1; i >= 0; i--) {
		System.out.println(goodHandTypes[i]+"\t|\t"+multipliers[i]);
	}
	System.out.println("\n\n");
    }

    /** Check current playerHand using multipliers and goodHandTypes arrays
     *  Must print yourHandType (default is "Sorry, you lost") at the end of function.
     *  This can be checked by testCheckHands() and main() method.
     */
    private void checkHands()
    {
        // implement this method!
        ArrayList<Card> currentHand = new ArrayList<Card>(playerHand);
        Collections.sort(currentHand, new compareCards());
        System.out.println("Your current hand is " + currentHand);
        int index = -1;
        if (isRoyalFlush(currentHand)) index=8;
        else if (isStraightFlush(currentHand)) index=7;
        else if (isFourOfAKind(currentHand)) index=6;
        else if (isFullHouse(currentHand)) index=5;
        else if (isFlush(currentHand)) index=4;
        else if (isStraight(currentHand)) index=3;
        else if (isThreeOfAKind(currentHand)) index=2;
        else if (isTwoPair(currentHand)) index=1;
        else if (isOnePair(currentHand)) index=0;
        
        switch(index) {
        
        case 8:
        System.out.println(goodHandTypes[index]);
        playerBalance += (playerBet*=multipliers[index]);
        break;
        
        case 7:
        System.out.println(goodHandTypes[index]);
        playerBalance += (playerBet*=multipliers[index]);
        break;
        
        case 6:
        System.out.println(goodHandTypes[index]);
        playerBalance += (playerBet*=multipliers[index]);
        break;
        
        case 5:
        System.out.println(goodHandTypes[index]);
        playerBalance += (playerBet*=multipliers[index]);
        break;
        
        case 4:
        System.out.println(goodHandTypes[index]);
        playerBalance += (playerBet*=multipliers[index]);
        break;
        
        case 3:
        System.out.println(goodHandTypes[index]);
        playerBalance += (playerBet*=multipliers[index]);
        break;
        
        case 2:
        System.out.println(goodHandTypes[index]);
        playerBalance += (playerBet*=multipliers[index]);
        break;
        
        case 1:
        System.out.println(goodHandTypes[index]);
        playerBalance += (playerBet*=multipliers[index]);
        break;
        
        case 0:
        System.out.println(goodHandTypes[index]);
        playerBalance += (playerBet*=multipliers[index]);
        break;
        
        default:
        System.out.println("Sorry, you lose, loser )-: ");
        }
    }
    
    /*************************************************
     *   add new private methods here ....
     *
     *************************************************/
     

    
    class compareCards implements Comparator<Card> {
      private Card c1; private Card c2;
      public int compare(Card c1, Card c2){
         return c1.getRank()-c2.getRank();
      }
    
    }
    
    private boolean isOnePair(ArrayList<Card> hand){
      boolean isPair = false;
         for(int i = 0; i < 4 && !isPair; i++) {
            if(getValue(hand,i) == getValue(hand,i+1))
                isPair = true;
        }
      return isPair;
    }
    
    private boolean isTwoPair(ArrayList<Card> hand){
      boolean twoPairs = false;
      for(int i = 0; i < 2 && !twoPairs; i++) {
          if(getValue(hand,i) == getValue(hand,i+1))
                for(int j = i+2; j < 4; j++)
		    if(getValue(hand,j) == getValue(hand,j+1))
		twoPairs = true;
        }      
      return twoPairs;
    }
    
    private boolean isThreeOfAKind(ArrayList<Card> hand){
      boolean toak = false;
            if ((getValue(hand,0)==getValue(hand,1)&&getValue(hand,1)==getValue(hand,2))
            || (getValue(hand,1)==getValue(hand,2)&&getValue(hand,2)==getValue(hand,3))
            || (getValue(hand,2)==getValue(hand,3)&&getValue(hand,3)==getValue(hand,4))) toak=true;
      return toak;
    }
    
    private boolean isStraight(ArrayList<Card> hand){
      boolean straight = false;
      int counter=0;
         for (int i=0;i<4;i++){
            if(hand.get(i).getRank()==hand.get(i+1).getRank()-1) counter++;
         }
      if(counter==4) straight=true;
      return straight;
      }
    
    private boolean isFlush(ArrayList<Card> hand){
      boolean flush = false;
      int counter=0;
      for (int i=0;i<4;i++){
         if(hand.get(i).getSuit()==hand.get(i+1).getSuit()) counter++;
      }
      if(counter==4) flush=true;
      return flush;
    }
    
    // **** this private method just makes my code look a little less chaotic **** //
    private int getValue(ArrayList<Card> hand, int n){
      return hand.get(n).getRank();
    }
    // **** the end **** //
    
    private boolean isFullHouse(ArrayList<Card> hand){
      boolean fullHouse = false; int counter=0;
      if (getValue(hand,0)==getValue(hand,1)){
         if (getValue(hand,2)==getValue(hand,3)&&getValue(hand,3)==getValue(hand,4)) fullHouse=true;
         }
      if (getValue(hand,3)==getValue(hand,4)){
         if (getValue(hand,0)==getValue(hand,1)&&getValue(hand,1)==getValue(hand,2)) fullHouse=true;
         }
      return fullHouse;
    }
    
    private boolean isStraightFlush(ArrayList<Card> hand){
      boolean straightFlush = false;
      if (isStraight(hand)&&isFlush(hand)) straightFlush=true;
      return straightFlush;
    }
    
    private boolean isFourOfAKind(ArrayList<Card> hand){
      boolean foak = false; int counter=0;
      for (int i=0;i<3;i++){
         if(getValue(hand,i)==getValue(hand,i+1)) counter++;
         }
      if((counter==3)&&!isFullHouse(hand)) foak = true;
      return foak;
    }
    
    private boolean isRoyalFlush(ArrayList<Card> hand){
      boolean royalFlush = false;
      if(isFlush(hand)&&getValue(hand,0)==1){
         if( (getValue(hand,1)==10)
         &&  (getValue(hand,2)==11)
         &&  (getValue(hand,3)==12)
         &&  (getValue(hand,4)==13))
         royalFlush=true;
         }
      return royalFlush;
    }

    

    public void play() 
    {
    /** The main algorithm for single player poker game 
     *
     * Steps:
     * 		showPayoutTable()
     *
     * 		++	
     * 		show balance, get bet 
     *		verify bet value, update balance
     *		reset deck, shuffle deck, 
     *		deal cards and display cards
     *		ask for positions of cards to replace 
     *          get positions in one input line
     *		update cards
     *		check hands, display proper messages
     *		update balance if there is a payout
     *		if balance = O:
     *			end of program 
     *		else
     *			ask if the player wants to play a new game
     *			if the answer is "no" : end of program
     *			else : showPayoutTable() if user wants to see it
     *			goto ++
     */

        // implement this method!
   showPayoutTable();
	Scanner input = new Scanner(System.in);
	boolean playAgain = false;
	do {
	    System.out.println("*********************************");
	    System.out.println("Balance: $" + playerBalance);
	    do {
		System.out.print("Enter bet: $");
		playerBet = input.nextInt();
	    } while(playerBet < 0 || playerBet > playerBalance);
	    playerBalance = playerBalance - playerBet;
	    oneDeck.reset();
	    oneDeck.shuffle();
	    try {
		playerHand = new ArrayList<Card>(oneDeck.deal(numberOfCards));
	    	System.out.println("Hand: " + playerHand);
	        System.out.print("Enter positions of cards(1-5) to replace (e.g. 2 3 5 ): ");
	        input = new Scanner(System.in);
	        String line = input.nextLine();
	        String[] strs = line.trim().split("\\s+");
	        for (int i = 0; i < strs.length; i++) {
		    try {
		        int replaceCard = Integer.parseInt(strs[i]);
		    	if(replaceCard > 0 && replaceCard < 6)
			    playerHand.set(replaceCard-1, oneDeck.deal(1).get(0));
		    } 
		    catch (NumberFormatException e) {
		    	continue;
		    }
		    catch (PlayingCardException e) {
                    	System.out.println("Not enough cards for ya\n");
                    }
	        }
	        System.out.println("Hand: " + playerHand);
	        checkHands();
	        playerBalance += playerBet;
	        if(playerBalance <= 0)
		    playAgain = false;
	        else {
		    char resp;
		    System.out.print("Your balance: $" + playerBalance + ". Play one more game?(y/n) ");
		    resp = input.next().charAt(0);
		    if(resp == 'n')
		    	playAgain = false;
		    else {
		        playAgain = true;
		    	System.out.print("Would you like to the see payout table?(y/n) ");
		    	resp = input.next().charAt(0);
		    	if(resp == 'y')
			    showPayoutTable();
		    }
	        }
	    }
	    catch (PlayingCardException e) {
            	System.out.println("Not enough for ya\n");
            } 
	} while(playAgain);
	System.out.println("Tschuss!");
    }


    /*************************************************
     *   Do not modify methods below
    /*************************************************

    /** testCheckHands() is used to test checkHands() method 
     *  checkHands() should print your current hand type
     */ 

    public void testCheckHands()
    {
      	try {
    		playerHand = new ArrayList<Card>();

		// set Royal Flush
		playerHand.add(new Card(3,1));
		playerHand.add(new Card(3,10));
		playerHand.add(new Card(3,12));
		playerHand.add(new Card(3,11));
		playerHand.add(new Card(3,13));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// set Straight Flush
		playerHand.set(0,new Card(3,9));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// set Straight
		playerHand.set(4, new Card(1,8));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// set Flush 
		playerHand.set(4, new Card(3,5));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// "Royal Pair" , "Two Pairs" , "Three of a Kind", "Straight", "Flush	", 
	 	// "Full House", "Four of a Kind", "Straight Flush", "Royal Flush" };

		// set Four of a Kind
		playerHand.clear();
		playerHand.add(new Card(4,8));
		playerHand.add(new Card(1,8));
		playerHand.add(new Card(4,12));
		playerHand.add(new Card(2,8));
		playerHand.add(new Card(3,8));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// set Three of a Kind
		playerHand.set(4, new Card(4,11));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// set Full House
		playerHand.set(2, new Card(2,11));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// set Two Pairs
		playerHand.set(1, new Card(2,9));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// set One Pair
		playerHand.set(0, new Card(2,3));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// set One Pair
		playerHand.set(2, new Card(4,3));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");

		// set no Pair
		playerHand.set(2, new Card(4,6));
		System.out.println(playerHand);
    		checkHands();
		System.out.println("-----------------------------------");
      	}
      	catch (Exception e)
      	{
		System.out.println(e.getMessage());
      	}
    }

    /* Quick testCheckHands() */
    public static void main(String args[]) 
    {
	VideoPoker pokergame = new VideoPoker();
	pokergame.testCheckHands();
    }
}
