package poker.com.message;

import java.util.Arrays;
import java.util.HashMap;

import poker.cardspool.CardsUtil;

import com.biotools.meerkat.Card;


public class ShowdownMessage
{  
    Card  card1 = new Card();
    Card  card2 = new Card();
    HashMap<String,String> pid2rank = new HashMap<String,String>();
    
    public void convertmessage( String mes )
    {
        String[] lines = mes.split( "\n" );
        String max = null;
        int r = 9;
        for( int i = 8; i< lines.length-1; i++ )
        {
            String[] s = lines[i].split( " " );
            pid2rank.put( s[1], s[0] );
            int rank = Integer.valueOf( s[0].replace( ':', '0' ) );
            rank = rank/10;
            if( rank < r )
                max = lines[i];
        }
        String[] s = max.split( " " );
        String color1 = s[2];
        String point1 = s[3];
        String color2 = s[4];
        String point2 = s[5];
        card1 = new Card( CardsUtil.point2num.get( point1 ), CardsUtil.color2num.get( color1 ) );
        card2 = new Card( CardsUtil.point2num.get( point2 ), CardsUtil.color2num.get( color2 ) );
    }
       
    public Card getcard1()
    {
        return card1;
    }
    public Card getcard2()
    {
        return card2;
    }

    public boolean lose( String id )
    {
        String rank = pid2rank.get( id );
        if( rank != null )
        {
            return !rank.equals( "1:" );
        }
        return false;
    }
    
    public boolean win( String id )
    {
        String rank = pid2rank.get( id );
        if( rank != null )
        {
            return rank.equals( "1:" );
        }
        return false;
    }
}
