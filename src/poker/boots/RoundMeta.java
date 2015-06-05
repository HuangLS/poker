package poker.boots;

import java.util.HashMap;
import java.util.Map;

import com.biotools.meerkat.Card;
import com.biotools.meerkat.Hand;

import poker.com.message.*;

public class RoundMeta
{
    private Map< String, Integer > pid2jetton = new HashMap<String,Integer>();
    private Map< String, Integer > pid2money = new HashMap<String,Integer>();
    private Map< Integer, String > oder2pid = new HashMap<Integer,String>();
    private Map< String, Integer > pid2oder = new HashMap<String,Integer>();
    
    public int mylastjet;
    
    private String button;
    
    private int Bigblind = -1;
    private int Smallblind = -1;
    
    private int cardnum= 0;
    
    private Card holdcard1;
    private Card holdcard2;
    
    private Card flopcard1;
    private Card flopcard2;
    private Card flopcard3;
    
    private Card turncard;
    
    private Card rivercard;
    
    private String myID;
    
    public int raisenum;
    
    public RoundMeta( String id )
    {
        this.myID = id;
    }
    
    
    public boolean firsttocall()
    {
        return pid2oder.get( myID ) == 2;
    }
    
    public void set( SeatMessage seat )
    {
        this.pid2jetton = seat.getpid2jetton();
        this.pid2money = seat.getpid2money();
        this.oder2pid = seat.getoder2pid();
        this.button = seat.getbutton();
        for( Integer i : oder2pid.keySet() )
        {
            pid2oder.put( oder2pid.get( i ), i );
        }
        this.mylastjet = 0;
    }
    
    public void setblind( BlindMessage blind )
    {
        this.Bigblind = blind.getBigblind();
        this.Smallblind = blind.getSmallblind();
        this.raisenum = 0;
    }

    public void sethold( HoldMessage hold )
    {
        this.holdcard1 = hold.getcard1();
        this.holdcard2 = hold.getcard2();
        this.cardnum = 2;
        this.raisenum = 0;
    }

    public void setflop( FlopMessage flop )
    {
        this.flopcard1 = flop.getcard1();
        this.flopcard2 = flop.getcard2();
        this.flopcard3 = flop.getcard3();
        this.cardnum = 5;
        this.raisenum = 0;
    }

    public void setturn( TurnMessage turn )
    {
        this.turncard = turn.getcard();
        this.cardnum = 6;
        this.raisenum = 0;
    }

    public void setriver( RiverMessage river )
    {
        this.rivercard = river.getcard();
        this.cardnum = 7;
        this.raisenum = 0;
    }

    public String getbutton()
    {
        return this.button;
    }
  
    public Card[] getholdcards()
    {
        return new Card[]{holdcard1,holdcard2};
    }


    public double getBigBlind()
    {
        return this.Bigblind;
    }


    public Card getholdcard1()
    {
        return holdcard1;
    }

    public Card getholdcard2()
    {
        return holdcard2;
    }
    
    public Hand getboardcards()
    {
        Hand toret = new Hand();
        if( this.cardnum == 5 )
        {
            toret.addCard( flopcard1 );
            toret.addCard( flopcard2 );
            toret.addCard( flopcard3 );
        }
        else if( this.cardnum == 6 )
        {
            toret.addCard( flopcard1 );
            toret.addCard( flopcard2 );
            toret.addCard( flopcard3 );
            toret.addCard( turncard );
        }
        else
        {
            toret.addCard( flopcard1 );
            toret.addCard( flopcard2 );
            toret.addCard( flopcard3 );
            toret.addCard( turncard );
            toret.addCard( rivercard );
        }
        return toret;
    }


    public int getplayer()
    {
        return pid2oder.size();
    }

}
