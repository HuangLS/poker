package poker.com.message;

import java.util.Arrays;

import com.biotools.meerkat.Card;

import poker.cardspool.CardsUtil;


public class HoldMessage
{
    
    Card card1 = new Card();
    Card card2 = new Card();
    
    public void convertmessage( String mes )
    {
        String[] lines = mes.split( "\n" );
        int[] num = new int[]{-1,-1};
        for( int i = 1; i < lines.length -1 ; i++ )
        {
            String[] s = lines[i].split( " " );
            if ( i == 1 )
            {
                int point = CardsUtil.point2num.get( s[1] );
                int color = CardsUtil.color2num.get( s[0] );
                card1 = new Card( point , color );
            }
            else
            {
                card2 = new Card( CardsUtil.point2num.get( s[1] ), CardsUtil.color2num.get( s[0] ) );
            }
        }
    }

    public Card getcard1()
    {
        return card1;
    }
    public Card getcard2()
    {
        return card2;
    }
    
}
