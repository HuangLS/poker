package poker.boots;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.Callable;

import com.biotools.meerkat.Action;
import com.biotools.meerkat.Card;
import com.biotools.meerkat.Hand;
import com.biotools.meerkat.Holdem;

import common.handeval.klaatu.PartialStageFastEval;
import poker.cardspool.CardsUtil;
import poker.com.TableCom;
import poker.log.GlobelCount;
import poker.log.Log;
import poker.com.message.*;

public class Round
{
    
    
    private RoundMeta metainfo;
    
    private String myID;
    
    private Roundphase phase = Roundphase.NONE;
    
    private String action;
    
    private Log log;
    
    
    public Round(  Log l, String id,GlobelCount g,GlobelCount s,GlobelCount y )
    {
        this.yes = y;
        this.stupid = s;
        
        this.shamenum = g;
        log = l;
        myID = id;
        metainfo = new RoundMeta( this.myID );
    }
    
    public enum Roundphase
    {
        NONE,
        SEAT,
        BLIND,
        HOLDCARDS,
        FLOP,
        TURN,
        RIVER,
        SHOWDOWN,
        POTWIN;
    }
    
    private void takeSetAction()
    {
        switch( this.phase )
        {
            case SEAT:
            {
                //TODO
                noAction();
                break;
            }            
            case BLIND:
            {
                //TODO
                noAction();
                break;
            }           
            case HOLDCARDS:
            {
                //TODO
                noAction();
                break;
            }
            case FLOP:
            {
                //TODO
                noAction();
                break;
            }
            case TURN:
            {
                //TODO
                noAction();
                break;
            }
            case RIVER:
            {
                //TODO
                noAction();
                break;
            }
            case SHOWDOWN:
            {
                noAction();
                break;
            }
            case POTWIN:
            {
                //TODO
                noAction();
                break;
            }
        }
    }
    
    
    private GlobelCount shamenum;
    
    private GlobelCount stupid;
    
    private GlobelCount yes;

    private int plarnum;
    

    private void takeRespondAction()
    {
        switch( this.phase )
        {
            case NONE:
            {
                //TODO
                this.action = "call \n";
                sentaction();
                break;
            }
            case HOLDCARDS:
            {
                //LOG-----------------------------------
                //log.log( "HOLDLEVEL" );
                //--------------------------------------
                preflopaction();
                sentaction();
                break;
            }
            case FLOP:
            {
                //LOG-----------------------------------
                //log.log( "FLOPLEVEL" );
                //--------------------------------------
                postflopaction();
                sentaction();
                break;
            }
            case TURN:
            {
                //LOG-----------------------------------
                //log.log( "TURNLEVEL" );
                //--------------------------------------
                postflopaction();
                sentaction();
                break;
            }
            case RIVER:
            {
                //LOG-----------------------------------
                //log.log( "RIVERLEVEL" );
                //--------------------------------------
                postflopaction();
                sentaction();
                break;
            }
        }
    }

    private double potsize;

    private void postflopaction()
    {
        int np = this.plarnum;

        double toCall = this.tocall;

        double PO = toCall / (double) (this.potsize + toCall);

        EnumerateResult result = enumerateHands(this.metainfo.getholdcard1(), this.metainfo.getholdcard2(), this.metainfo.getboardcards());
        
        double HRN = Math.pow(result.HR, np - 1 + this.raisenum);

        double PPOT = 0.0;
        double NPOT = 0.0;
        if ( this.phase != Roundphase.RIVER ) 
        {
            PPOT = result.PPot;
            NPOT = result.NPot;
        }

        //LOG----------------------------------------------------------------------------------------------------------
        log.log( this.phase + " | HRn = " + Math.round(HRN * 10) / 10.0 + " PPot = " + Math.round(PPOT * 10) / 10.0 + " NPot = " + Math.round(NPOT * 10) / 10.0 + " PotOdds = " + Math.round(PO * 10)
                / 10.0);
        //-----------------------------------------------------------------------------------------------------------------
        if (HRN == 1.0) 
        {
            this.action = "raise \n";
            if( this.raisenum > 4 )
                this.action = "all_in \n";
            //TAKE ACTION  01---------------------------------------------------------
            log.log( "TAKE ACTION:01  " + this.action );
            //------------------------------------------------------------------------
            return;
        }

        
        if (toCall == 0) 
        {
            if (Math.random() < HRN * HRN)
            {
                this.action = "raise \n";
                //TAKE ACTION  02---------------------------------------------------------
                log.log( "TAKE ACTION:02  " + this.action );
                //------------------------------------------------------------------------
                return;

            }
            if (Math.random() < PPOT) 
            {
                this.action = "call \n";
                //TAKE ACTION  03---------------------------------------------------------
                log.log( "TAKE ACTION:03  " + this.action );
                //------------------------------------------------------------------------
                return;
            }
            this.action = "check \n";
            //TAKE ACTION  04---------------------------------------------------------
            log.log( "TAKE ACTION:04  " + this.action );
            //------------------------------------------------------------------------
            return;
        }
        
        
        if( HRN < 0.015 )
        {
            if (Math.random() < PPOT)
            {
                this.action = "call \n";
                //TAKE ACTION  05---------------------------------------------------------
                log.log( "TAKE ACTION:05  " + this.action );
                //------------------------------------------------------------------------
                return;
            }
            else
            {
                if( toCall <= 0 )
                    this.action = "check \n";
                else
                    this.action = "fold \n";
                //TAKE ACTION  06---------------------------------------------------------
                log.log( "TAKE ACTION:06  " + this.action );
                //------------------------------------------------------------------------
                return;
            }
        }
        
        else 
        {        
            if (Math.random() < Math.pow(HRN, 1 + this.raisenum) && Math.random() < NPOT )
            {
                this.action = "raise \n";
                //TAKE ACTION  07---------------------------------------------------------
                log.log( "TAKE ACTION:07  " + this.action );
                //------------------------------------------------------------------------
                return;
            }
            
            if (Math.random() < HRN && Math.random() < NPOT && Math.random() < (this.raisenum > 0 ? (1 - 0.5*this.raisenum):1) )
            {
                this.action = "call \n";
                //TAKE ACTION  08---------------------------------------------------------
                log.log( "TAKE ACTION:08  " + this.action );
                //------------------------------------------------------------------------
                return;
            }
            if (HRN * HRN * this.potsize > toCall || PPOT > PO && this.raisenum == 0 ) 
            {
                this.action = "call \n";
                if( toCall <= 0 )
                    this.action = "check \n";
                //TAKE ACTION  09---------------------------------------------------------
                log.log( "TAKE ACTION:09  " + this.action );
                //------------------------------------------------------------------------
                return;
            }
            if( toCall <= 0 )
                this.action = "check \n";
            else
                this.action = "fold \n";
            //TAKE ACTION  10---------------------------------------------------------
            log.log( "TAKE ACTION:10  " + this.action );
            //------------------------------------------------------------------------
            return;
        }
    }


    private double ourjetton;
    private int tocall;
    private int raisenum;
    private void preflopaction()
    {
        double toCall = this.tocall;
        Card[] c = this.metainfo.getholdcards();
        if (c[0].getRank() == c[1].getRank()) {
            if ((c[0].getRank() >= Card.TEN || c[1].getRank() == Card.TWO) && this.raisenum < 2) {
                this.action = "call \n";
                return;
            }
            if (this.raisenum < 3) {
                this.action = "call \n";
                return;
            }
        }
        
        if (c[0].getRank() >= Card.TEN && c[1].getRank() >= Card.TEN) {
            if (c[0].getSuit() == c[1].getSuit() && this.raisenum < 2) {
                this.action = "raise \n";
                return;
            }
            if (this.raisenum < 3) {
                this.action = "check \n";
                return;
            }
        }

        if ((c[0].getSuit() == c[1].getSuit())) 
        {
            if (Math.abs(c[0].getRank() - c[1].getRank()) == 1) 
            {
                this.action = "call \n";
                return;
            }
            if ((c[0].getRank() == Card.ACE && c[1].getRank() == Card.TWO) || (c[1].getRank() == Card.ACE && c[0].getRank() == Card.TWO))
            {
                if (this.raisenum == 0) 
                {
                    this.action = "call \n";
                    return;
                }
                if (this.raisenum < 2)
                {
                    this.action = "call \n";
                    return;
                }
            }
            if ((c[0].getRank() == Card.ACE || c[1].getRank() == Card.ACE)) 
            {
                this.action = "call \n";
                return;
            }
        }

        if (toCall <= this.metainfo.getBigBlind() ) 
        {
            if (Math.random() < 0.1)
            {
                this.action = "raise \n";
                return;
            }
        }
        if( toCall <= 0 )
            this.action = "check \n";
        else
        {
            this.action = "fold \n";
        }
        return;
        
    }




    private void sentaction()
    {
        TableCom table = TableCom.instence( null, null, null, null,null );
        OutputStream outputStream = table.getOutputStream();
        //LOG--------------------------------------------------------------
        //log.log( "TAKE ACTION:!!!!!!!!" + this.action );
        //-----------------------------------------------------------------
        char[] c = this.action.toCharArray();
        byte[] toout = new byte[c.length];
        for( int i = 0; i < toout.length; i++ )
            toout[i] = (byte)c[i];
        try
        {
            outputStream.write( toout );
            outputStream.flush();
        }
        catch ( IOException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    
    private void noAction()
    {
        
    }

    public void seatmessageArrived( SeatMessage seat )
    {
        metainfo.set( seat );
        this.phase = Roundphase.SEAT;
        takeSetAction();
    }

    public void blindmessageArrived( BlindMessage blind )
    {
        this.metainfo.setblind( blind );
        this.phase = Roundphase.BLIND;
        takeSetAction();
    }

    public void holdmessageArrived( HoldMessage hold )
    {
        this.metainfo.sethold( hold );
        this.phase = Roundphase.HOLDCARDS;
        takeSetAction();
    }

    public void flopmessageArrived( FlopMessage flop )
    {
        this.metainfo.setflop( flop );
        this.phase = Roundphase.FLOP;
        takeSetAction();
    }

    public void turnmessageArrived( TurnMessage turn )
    {
        this.metainfo.setturn( turn );
        this.phase = Roundphase.TURN;
        takeSetAction();
    }

    public void rivermessageArrived( RiverMessage river )
    {
        this.metainfo.setriver( river );
        this.phase = Roundphase.RIVER;
        takeSetAction();
    }

    public void inquiremessageArrived( InquireMessage inquire )
    {
        this.tocall = inquire.tocall( this.myID ,log,this.metainfo.mylastjet );
        this.metainfo.mylastjet++;
        //LOG---------------------------------------------------------------------
        this.log.log( "TOCALL:    " + String.valueOf( this.tocall ) );
        //------------------------------------------------------------------------
        this.raisenum = this.metainfo.raisenum + inquire.getraisenum(this.metainfo.getbutton());
        this.plarnum =  metainfo.getplayer()/* - inquire.getplayer()*/;
        this.potsize = inquire.getpotsize();
        this.ourjetton = inquire.getmyjutton( myID );
        takeRespondAction();
    }

    public void notifymessageArrived( NotifyMessage notify )
    {
        //TODO 
    }

    public void showdownmessageArrived( ShowdownMessage showdown )
    {
        this.phase = Roundphase.SHOWDOWN;
        if( this.action.equals( "fold \n" ))
        {
            Hand hand = this.metainfo.getboardcards();
            int[] boardIndexes = new int[5];
            for (int i = 0; i < hand.size(); i++) 
            {
                Card card = hand.getCard(i + 1);
                boardIndexes[i] = PartialStageFastEval.encode(card.getRank(), card.getSuit());
            }
            int mylevel = eval(boardIndexes, PartialStageFastEval.encode(this.metainfo.getholdcard1().getRank(), this.metainfo.getholdcard1().getSuit()),
                    PartialStageFastEval.encode(this.metainfo.getholdcard2().getRank(), this.metainfo.getholdcard2().getSuit()));
            Card card1 = showdown.getcard1();
            Card card2 = showdown.getcard2();
            int itlevel = eval(boardIndexes, PartialStageFastEval.encode(card1.getRank(), card1.getSuit()),
                    PartialStageFastEval.encode(card2.getRank(), card2.getSuit()));
            if( itlevel < mylevel )
                this.shamenum.plus();
        }
        else
        {
            if( showdown.lose( this.myID ) )
                this.stupid.plus();
            else {
                this.yes.plus();
            }
        }
        takeSetAction();
    }

    //-------------------------------------------------------------------------------------------------------
    
    
    
    public double roundToCents(double amount) {
        return (Math.round(amount * 100)) / 100D;
    }
    
    
    private int eval(int[] boardIndexes, int c1Index, int c2Index) 
    {
        if (boardIndexes.length == 5) 
        {
            return PartialStageFastEval.eval7(boardIndexes[0], boardIndexes[1], boardIndexes[2], boardIndexes[3], boardIndexes[4], c1Index, c2Index);
        } 
        else if (boardIndexes.length == 4)
        {
            return PartialStageFastEval.eval6(boardIndexes[0], boardIndexes[1], boardIndexes[2], boardIndexes[3], c1Index, c2Index);
        } 
        else 
        {
            return PartialStageFastEval.eval5(boardIndexes[0], boardIndexes[1], boardIndexes[2], c1Index, c2Index);
        }
    }
    
    public EnumerateResult enumerateHands(Card c1, Card c2, Hand bd) {
        double[][] HP = new double[3][3];
        double[] HPTotal = new double[3];
        int ourrank7, opprank;
        int index;
        int[] boardIndexes = new int[bd.size()];
        int[] boardIndexes2 = new int[bd.size() + 1];

        int c1Index;
        int c2Index;

        ArrayList<Integer> deck = new ArrayList<Integer>();
        for (int i = 0; i < 52; i++) {
            deck.add(Integer.valueOf(i));
        }
        for (int i = 0; i < bd.size(); i++) {
            Card card = bd.getCard(i + 1);
            boardIndexes[i] = PartialStageFastEval.encode(card.getRank(), card.getSuit());
            boardIndexes2[i] = PartialStageFastEval.encode(card.getRank(), card.getSuit());
            deck.remove(Integer.valueOf(boardIndexes[i]));
        }
        c1Index = PartialStageFastEval.encode(c1.getRank(), c1.getSuit());
        c2Index = PartialStageFastEval.encode(c2.getRank(), c2.getSuit());
        deck.remove(Integer.valueOf(c1Index));
        deck.remove(Integer.valueOf(c2Index));

        int ourrank5 = eval(boardIndexes, c1Index, c2Index);

        // pick first opponent card
        for (int i = 0; i < deck.size(); i++) {
            int o1Card = deck.get(i);
            // pick second opponent card
            for (int j = i + 1; j < deck.size(); j++) {
                int o2Card = deck.get(j);
                opprank = eval(boardIndexes, o1Card, o2Card);
                if (ourrank5 > opprank)
                    index = AHEAD;
                else if (ourrank5 == opprank)
                    index = TIED;
                else
                    index = BEHIND;
                HPTotal[index]++;
                if (bd.size() < 5) {

                    // tally all possiblities for next board card
                    for (int k = 0; k < deck.size(); k++) {
                        if (i == k || j == k)
                            continue;
                        boardIndexes2[boardIndexes2.length - 1] = deck.get(k);
                        ourrank7 = eval(boardIndexes2, c1Index, c2Index);
                        opprank = eval(boardIndexes2, o1Card, o2Card);
                        if (ourrank7 > opprank)
                            HP[index][AHEAD]++;
                        else if (ourrank7 == opprank)
                            HP[index][TIED]++;
                        else
                            HP[index][BEHIND]++;
                    }
                }
            }
        } /* end of possible opponent hands */

        double den1 = (45 * (HPTotal[BEHIND] + (HPTotal[TIED] / 2.0)));
        double den2 = (45 * (HPTotal[AHEAD] + (HPTotal[TIED] / 2.0)));
        EnumerateResult result = new EnumerateResult();
        if (den1 > 0) {
            result.PPot = (HP[BEHIND][AHEAD] + (HP[BEHIND][TIED] / 2.0) + (HP[TIED][AHEAD] / 2.0)) / (double) den1;
        }
        if (den2 > 0) {
            result.NPot = (HP[AHEAD][BEHIND] + (HP[AHEAD][TIED] / 2.0) + (HP[TIED][BEHIND] / 2.0)) / (double) den2;
        }
        result.HR = (HPTotal[AHEAD] + (HPTotal[TIED] / 2)) / (HPTotal[AHEAD] + HPTotal[TIED] + HPTotal[BEHIND]);

        return result;
    }
    
    
    private final static int AHEAD = 0;
    private final static int TIED = 1;
    private final static int BEHIND = 2;

    class EnumerateResult {
        double HR;
        double PPot;
        double NPot;
    }
    
}













