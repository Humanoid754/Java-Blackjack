public class Deck
{
    Card[] card = new Card[52];
    int nextToDeal = 0;
    
    public Deck()
    {
        int counter = 0;
        for (int i = 1; i <= 13; i++) // 13 ranks
        {
            for (int j = 1; j <= 4; j++) // 4 suits
            {
                card[counter] = new Card(Card.figureName(i), Card.figureSuit(j));
                counter++;
            }
        }
    }
    
    public void printDeck()
    {
        for (int k = 0; k < 52; k++)
            System.out.println(card[k]);
    }
        
    public void shuffle()
    {
        Card temp;
        int num1, num2;
        for (int x = 0; x < 1000; x++)
        {
            num1 = (int)(Math.random() * 52);
            num2 = (int)(Math.random() * 52);
            temp = card[num1]; // the 3-step swap
            card[num1] = card[num2];
            card[num2] = temp;
        }
    }
    
    public Card dealOne()
    {
        Card nextCard = card[nextToDeal];
        nextToDeal++;
        return nextCard;
    }
        
    class DeckDriver
    {
        public void main(String[] args)
        {
            Deck myDeck = new Deck();
            myDeck.printDeck();
            myDeck.shuffle();
            System.out.println("\n\nSorted Order");
            myDeck.printDeck();
        }
    }
}