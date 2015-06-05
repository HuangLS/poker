package poker.cardspool;

import java.util.HashMap;

public class CardsUtil
{
    public static final HashMap<String,Integer> point2num = new HashMap<String,Integer>();
    public static final HashMap<String,Integer> color2num = new HashMap<String,Integer>();
    static
    {
        point2num.put( "2", 0 );
        point2num.put( "3", 1 );
        point2num.put( "4", 2 );
        point2num.put( "5", 3 );
        point2num.put( "6", 4 );
        point2num.put( "7", 5 );
        point2num.put( "8", 6 );
        point2num.put( "9", 7 );
        point2num.put( "10", 8 );
        point2num.put( "J", 9 );
        point2num.put( "Q", 10 );
        point2num.put( "K", 11 );
        point2num.put( "A", 12 );
        color2num.put( "SPADES", 0 );
        color2num.put( "HEARTS", 1 );
        color2num.put( "DIAMONDS", 2 );
        color2num.put( "CLUBS", 3 );
    }
}
