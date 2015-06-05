package poker.com.message;

import java.util.Arrays;

import com.biotools.meerkat.Card;

import poker.cardspool.CardsUtil;
import poker.log.Log;

public class FlopMessage
{

    Card card1 = new Card();
    Card card2 = new Card();
    Card card3 = new Card();
    public void convertmessage( String mes )
    {
        String[] lines = mes.split( "\n" );
        String[] s1 = lines[1].split( " " );
        card1.setCard( CardsUtil.point2num.get( s1[1] ), CardsUtil.color2num.get( s1[0] ) );
        String[] s2 = lines[2].split( " " );
        card2.setCard( CardsUtil.point2num.get( s2[1] ), CardsUtil.color2num.get( s2[0] ) );
        String[] s3 = lines[3].split( " " );
        card3.setCard( CardsUtil.point2num.get( s3[1] ), CardsUtil.color2num.get( s3[0] ) );
    }
    
    public Card getcard1()
    {
        return card1;
    }
    public Card getcard2()
    {
        return card2;
    }
    public Card getcard3()
    {
        return card3;
    }
}
