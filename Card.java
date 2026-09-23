public class Card
{
    //Instance Variables
    private String faceValue;
    private String suitValue;
    public int points;
    
    //Constructors
    public Card(String f, String s)
    {
        faceValue = f;
        suitValue = s;
        
        if (f.equals("A" ))
        {
            points = 11;
        }
        else if (f.equals("K") || f.equals("Q") || f.equals("J") || f.equals("10"))
        {
            points = 10;
        }
        else
        {
            points = Integer.parseInt(f);
        }
        
    }
    
    //To String
    public String toString()
    {
        return("The " + faceValue + " of " + suitValue + " has a value of " + points);
    }
    
    //Getters
    public String getFaceValue() { return faceValue; }
    public String getSuit() { return suitValue; }
    public int getPoints() { return points; }
    
    //Setters
    public void setFaceValue(String face)
    {
        faceValue = face;
    }
    
    public void setSuitValue(String suit)
    {
        suitValue = suit;
    }
    
    public void setFaceValue(int p)
    {
        points = p;
    }
    
    public static String figureName(int i)
    {
        switch (i)
        {
        case 1: return "A";
        case 11: return "J";
        case 12: return "Q";
        case 13: return "K";
        default:
        if (i <= 0 || i >= 14) return "error";
        else return "" + i; // 2..10 become "2".."10"
        }
    }

    public static String figureSuit(int i)
    {
        switch (i)
        {
        case 1: return "Hearts";
        case 2: return "Diamonds";
        case 3: return "Spades";
        case 4: return "Clubs";
        default: return "error";
        }
    }
    
    
    
}