public class Hand {
    Card[] card = new Card[5];
    public int numCards = 0;
    
    public Hand(Card card1, Card card2)
    {
        card[0] = card1;
        card[1] = card2; //Add both cards to hand
        
        numCards = 2;
    }
    
    public int calculateHandValue()
    {
        int x;
        int value = 0;
        int aceCount = 0;
        
        for (x = 0; x < numCards; x++) {
            value = value + card[x].getPoints();
            if (card[x].getFaceValue().equals("A"))
                aceCount++;
        }
        return value;
    }
    
    public String showAllCards()
    {
        String s = "This hand has these cards:\n";
        for(int i = 0; i < numCards; i++)
        {
            s = s + card[i] + "\n";
        }
        return s;
    }
    
    public String showOneCard()
    {
        String s = "This hand has these cards:\n";
        s = s + card[0] + "\n";
        return s;
    }
    
    public void hit(Card c)
    {
        card[numCards] = c;
        numCards++;
    }
}