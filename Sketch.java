import processing.core.PApplet;
import processing.core.PFont;

/*
    If I remembered it existed before now I would probably have added
    dynamic Ace logic, where if you're above 21 with an Ace worth 11
    in your hand that Ace's value is set to 1.
*/

public class Sketch extends PApplet {
    
    boolean isPlaying = false; //For title screen
    
    boolean cursorVisible = true;
    
    boolean isPlayerTurn = true;
    
    //Standing
    boolean hasPlayerStood = false;
    boolean hasDealerStood = false;
    
    //End conditions
    boolean loseGameBust = false;
    boolean loseGame = false;
    boolean winGame = false;
    
    //Inputs
    char inputLetter = ' ';
    
    //Distance between each card
    int playerCardOffset = 0;
    int dealerCardOffset = 0;
    
    //Dealer decision making
    int dealerActionTime = 0;
    
    //Displayed suit on card
    char cardSuit = ' ';
    
    //Objects
    Deck d;
    Hand player;
    Hand dealer;
    
    //Overall color
    int r = 51;
    int g = 255;
    int b = 51;
    
    //Fonts
    PFont jersey10;
    PFont silkscreen;
    PFont unifont;
    PFont printchar21;
    
    public void setup() {
        size(640, 480);
        
        //Load all fonts that will be used
        jersey10 = createFont("Jersey10.ttf",1);
        silkscreen = createFont("Silkscreen.ttf",1);
        unifont = createFont("Unifont.ttf",1);
        printchar21 = createFont("PrintChar21.ttf",1);
    }
    
    public void startGame() {
        d = new Deck();
        d.shuffle();
        
        player = new Hand(d.dealOne(), d.dealOne());
        dealer = new Hand(d.dealOne(), d.dealOne());
        
        isPlaying = true;
    }
    
    public void draw() {
        
        background(10,11,10);
        
        noStroke();
        
        //Title Screen
        if(isPlaying == false && loseGameBust == false && winGame == false && loseGame == false)
        {
            fill(r,g,b);
            textAlign(CENTER,CENTER);
            textFont(jersey10);
            textSize(50);
            text("Welcome to Blackjack",width/2,60);
            
            fill(r,g,b);
            textAlign(CENTER,CENTER);
            textFont(silkscreen);
            textSize(8);
            text("COPYRIGHT © 1985 CHRISTIAN WILLIAMS\nALL RIGHTS RESERVED • VERSION 1.0",width/2,440);
            
            fill(r,g,b);
            textAlign(CENTER,CENTER);
            textFont(jersey10);
            textSize(25);
            text("PRESS [SPACE] TO BEGIN",width/2,height/2+40);
            
            if (keyPressed && key == ' ')
            {
                startGame();
            }
        }
        
        //Main
        if(isPlaying == true)
        {
            fill(r,g,b);
            textAlign(CENTER,CENTER);
            textFont(jersey10);
            textSize(20);
            textLeading(25);
            text("DEALER'S HAND\n" + "CARD 1 VALUE: " + dealer.card[0].points,width/4,height/3);
            
            fill(r,g,b);
            textAlign(CENTER,CENTER);
            textFont(jersey10);
            textSize(20);
            textLeading(25);
            text("YOUR HAND\n" + "TOTAL: " + player.calculateHandValue(),width-width/4,height/3);
            
            //Dealer Card Visual
            cardVisual((int)((width/4)-((dealer.numCards-1)*50)/2) + dealerCardOffset,height/2,8,dealer.card[0]);
            dealerCardOffset = dealerCardOffset+50;
            
            for(int i = 1; i<dealer.numCards; i++)
            {
                cardBackVisual((int)((width/4)-((dealer.numCards-1)*50)/2) + dealerCardOffset,height/2,8);
                dealerCardOffset = dealerCardOffset+50;
            }
            dealerCardOffset = 0;
            
            //Player Card Visual
            for(int i = 0; i<player.numCards; i++)
            {
                cardVisual((int)((width-width/4)-((player.numCards-1)*50)/2) + playerCardOffset,height/2,8,player.card[i]);
                playerCardOffset = playerCardOffset+50;
            }
            playerCardOffset = 0;
            
            fill(r,g,b);
            textAlign(CENTER,CENTER);
            textFont(silkscreen);
            textSize(15);
            text("--------------------------------------------------------------------------",width/2,height-height/12);
            
            //Player's turn
            if(isPlayerTurn == true && hasPlayerStood == false)
            {
                fill(r,g,b);
                textAlign(LEFT,CENTER);
                textFont(jersey10);
                textSize(20);
                text("WOULD YOU LIKE TO [H]IT OR [S]TAND?",0,height-height/9);
                
                //Blinking cursor
                if(frameCount % 15 == 0)
                {
                    cursorVisible = !cursorVisible;
                }
                
                if(cursorVisible == true)
                {
                    rectMode(CENTER);
                    fill(r,g,b);
                    rect(width/3+45,height-height/9+5,12,15);
                }
                
                //Answer input
                if(keyPressed && inputLetter == ' ' && (key == 'h' || key == 'H'))
                {
                    inputLetter = 'H';
                    
                } else if(keyPressed && inputLetter == ' ' && (key == 's' || key == 'S'))
                {
                    inputLetter = 'S';
                }
                
                fill(r,g,b);
                textAlign(CENTER,CENTER);
                textFont(jersey10);
                textSize(20);
                text(inputLetter,width/3+45,height-height/9);
                
                //Enter or backspace
                if(keyPressed && inputLetter != ' ' && (key == ENTER || key == RETURN))
                {
                    if(inputLetter == 'H')
                    {
                        player.hit(d.dealOne());
                        
                    } else if(inputLetter == 'S')
                    {
                        hasPlayerStood = true;
                    }
                    if(hasDealerStood == false)
                    {
                        isPlayerTurn = false;
                    }
                    inputLetter = ' ';
                    
                } else if(keyPressed && inputLetter != ' ' && key == BACKSPACE)
                {
                    inputLetter = ' ';
                }
                
            }
            
            //Dealer's turn
            if(isPlayerTurn == false)
            {
                fill(r,g,b);
                textAlign(LEFT,CENTER);
                textFont(jersey10);
                textSize(20);
                text("DEALER'S TURN",0,height-height/9);
                
                //Delay to simulate making a decision
                if(dealerActionTime == 0)
                {
                    dealerActionTime = round((int)(Math.random()*10))+(int)50;
                }
                
                if(dealerActionTime >= 1)
                {
                    dealerActionTime--;
                }
                
                //Below 17, hit. 17 or above stand
                if(dealer.calculateHandValue() < 17 && dealerActionTime == 0)
                {
                    dealer.hit(d.dealOne());
                    
                    if(hasPlayerStood == false)
                    {
                        isPlayerTurn = true;
                    }
                } else if(dealerActionTime == 0){
                    hasDealerStood = true;
                    
                    if(hasPlayerStood == false)
                    {
                        isPlayerTurn = true;
                    }
                }
            }
            
            //Trigger player bust
            if(player.calculateHandValue() > 21)
            {
                isPlayerTurn = false;
                isPlaying = false;
                loseGameBust = true;
            }
            
            //Trigger dealer bust
            if(dealer.calculateHandValue() > 21)
            {
                isPlayerTurn = false;
                isPlaying = false;
                hasPlayerStood = true;
                hasDealerStood = true;
            }
        }
        
        //Player bust
        if(loseGameBust == true)
        {
            fill(r,g,b);
            textAlign(CENTER,CENTER);
            textFont(jersey10);
            textSize(50);
            text("Busted",width/2,60);
            
            fill(r,g,b);
            textAlign(CENTER,CENTER);
            textFont(jersey10);
            textSize(20);
            textLeading(25);
            text("DEALER SCORE\n" + dealer.calculateHandValue(),width/4,height/2);
            
            fill(r,g,b);
            textAlign(CENTER,CENTER);
            textFont(jersey10);
            textSize(20);
            textLeading(25);
            text("PLAYER SCORE\n" + player.calculateHandValue(),width-width/4,height/2);
        }
        
        //Show results
        if(hasPlayerStood == true)
        {
            if(hasDealerStood == true)
            {
                if ((dealer.calculateHandValue() < player.calculateHandValue()) && (player.calculateHandValue()<=21))
                {
                    //Player win
                    fill(r,g,b);
                    textAlign(CENTER,CENTER);
                    textFont(jersey10);
                    textSize(50);
                    text("You Win",width/2,60);
                    winGame = true;
                } else if((dealer.calculateHandValue() > player.calculateHandValue()) && (dealer.calculateHandValue()<=21))
                {
                    //Dealer win
                    fill(r,g,b);
                    textAlign(CENTER,CENTER);
                    textFont(jersey10);
                    textSize(50);
                    text("Dealer Wins",width/2,60);
                    loseGame = true;
                } else if (dealer.calculateHandValue() == player.calculateHandValue())
                {
                    //Tie
                    fill(r,g,b);
                    textAlign(CENTER,CENTER);
                    textFont(jersey10);
                    textSize(50);
                    text("Tie",width/2,60);
                    winGame = true;
                    loseGame = true;
                } else
                {
                    //Dealer bust
                    fill(r,g,b);
                    textAlign(CENTER,CENTER);
                    textFont(jersey10);
                    textSize(50);
                    text("Dealer Busted",width/2,60);
                    winGame = true;
                }
                
                isPlaying = false;
                
                fill(r,g,b);
                textAlign(CENTER,CENTER);
                textFont(jersey10);
                textSize(20);
                textLeading(25);
                text("DEALER SCORE\n" + dealer.calculateHandValue(),width/4,height/2);
                
                fill(r,g,b);
                textAlign(CENTER,CENTER);
                textFont(jersey10);
                textSize(20);
                textLeading(25);
                text("PLAYER SCORE\n" + player.calculateHandValue(),width-width/4,height/2);
            }
        }
        
    }
    
    public void cardVisual(int xPos, int yPos, float scale, Card visualCard)
    {
        rectMode(CENTER);
        fill(r,g,b);
        rect(xPos,yPos,(float)5*scale,7*scale); //Main square
        
        //Edges
        fill(r,g,b);
        rect(xPos-(5*scale)/2,yPos,scale/3, (float)7*scale);
        rect(xPos+(5*scale)/2,yPos,scale/3, (float)7*scale);
        rect(xPos,yPos-(7*scale)/2,(float)5*scale, scale/3);
        rect(xPos,yPos+(7*scale)/2,(float)5*scale, scale/3);
        
        //Value
        fill(10,11,10);
        textAlign(CENTER,CENTER);
        textFont(silkscreen);
        textSize(scale*2);
        text(visualCard.getFaceValue().toString(),xPos+(5*scale)/4,yPos+(float)1.4*scale);
        
        fill(10,11,10);
        textAlign(CENTER,CENTER);
        textFont(silkscreen);
        textSize(scale*2);
        text(visualCard.getFaceValue().toString(),xPos-(5*scale)/4,yPos-(float)2.6*scale);
        
        //Set the displayed suit
        if(visualCard.getSuit() == "Spades")
        {
            cardSuit = '♠';
        } else if(visualCard.getSuit() == "Hearts")
        {
            cardSuit = '♥';
        } else if(visualCard.getSuit() == "Diamonds")
        {
            cardSuit = '♦';
        } else if(visualCard.getSuit() == "Clubs")
        {
            cardSuit = '♣';
        }
        
        //Suit
        fill(10,11,10);
        textAlign(CENTER,CENTER);
        textFont(unifont);
        textSize(scale*(float)2.6);
        text(cardSuit,xPos-(5*scale)/4,yPos+(float)1.4*scale);
        
        fill(10,11,10);
        textAlign(CENTER,CENTER);
        textFont(unifont);
        textSize(scale*(float)2.6);
        text(cardSuit,xPos+(5*scale)/4,yPos-(float)2.6*scale);
    }
    
    public void cardBackVisual(int xPos, int yPos, float scale)
    {
        rectMode(CENTER);
        fill(10,11,10);
        rect(xPos,yPos,(float)5*scale,7*scale); //Main square
        
        //Edges
        fill(r,g,b);
        rect(xPos-(5*scale)/2,yPos,scale/2, (float)7*scale);
        rect(xPos+(5*scale)/2,yPos,scale/2, (float)7*scale);
        rect(xPos,yPos-(7*scale)/2,(float)5*scale, scale/2);
        rect(xPos,yPos+(7*scale)/2,(float)5*scale, scale/2);
        
        //Question Mark
        fill(r,g,b);
        textAlign(CENTER,CENTER);
        textFont(jersey10);
        textSize(scale*7);
        text("?",xPos,yPos-scale);
    }
    
    public static void main(String[] args){
        String[] processingArgs = {"Program"};
    	Sketch program = new Sketch();
    	PApplet.runSketch(processingArgs, program);
    }
}