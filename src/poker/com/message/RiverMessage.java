package poker.com.message;

import com.biotools.meerkat.Card;

import poker.cardspool.CardsUtil;

public class RiverMessage
{
    Card card = new Card();
    public void convertmessage( String mes )
    {
        String[] lines = mes.split( "\n" );
        String[] s = lines[1].split( " " );
        card.setCard( CardsUtil.point2num.get( s[1] ), CardsUtil.color2num.get( s[0] ) );
    }
    
    public Card getcard()
    {
        return card;
    }
}
