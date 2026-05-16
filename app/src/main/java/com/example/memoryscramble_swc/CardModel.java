package com.example.memoryscramble_swc;

public class CardModel {

    public static final String[] SYMBOLS = {
            "★", "♠", "♥", "♦", "♣", "☀", "☁", "☂",
            "✿", "❤", "✦", "⚡", "⚽", "♫", "◆", "▲",
            "◉", "✌", "☎", "✈", "⚓", "☯", "✎", "☘"
    };

    private final String symbol;
    private boolean faceUp;
    private boolean matched;

    public CardModel(String symbol) {
        this.symbol = symbol;
        this.faceUp  = false;
        this.matched = false;
    }

    public String  getSymbol()               { return symbol;  }
    public boolean isFaceUp()                { return faceUp;  }
    public void    setFaceUp(boolean faceUp) { this.faceUp = faceUp; }
    public boolean isMatched()               { return matched; }
    public void    setMatched(boolean m)     { this.matched = m; }
}