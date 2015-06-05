package poker.com;

import poker.boots.Round;
import poker.log.GlobelCount;
import poker.log.Log;
import poker.com.message.*;

public class MessageConverter implements TableMessageListener
{
    

    private SeatMessage seat = new SeatMessage();
    private BlindMessage blind = new BlindMessage();
    private HoldMessage hold = new HoldMessage();
    private InquireMessage inquire = new InquireMessage();
    private FlopMessage flop = new FlopMessage();
    private TurnMessage turn = new TurnMessage();
    private RiverMessage river = new RiverMessage();
    private NotifyMessage notify = new NotifyMessage();
    private ShowdownMessage showdown = new ShowdownMessage();
    
    private Log log;
    
    private Round round;
    
    private String myID;
    
    private GlobelCount count = new GlobelCount();
    
    private GlobelCount count2 = new GlobelCount();
    private GlobelCount count3 = new GlobelCount();
    
    public MessageConverter( Log l, String id )
    {
        log = l;
        myID = id;
        round = new Round( log, myID ,count, count2,count3);
    }
    
    @Override
    public void processmessage( String mes, TableCom table )
    {
        if( mes.startsWith( "seat" ) )
        {
            seat.convertmessage( mes );
            round.seatmessageArrived( this.seat );
        }
        else if( mes.startsWith( "blind" ))
        {
          //LOG---------------------------------------
            //log.log( mes );
            //-------------------------------------------
            blind.convertmessage( mes );
            round.blindmessageArrived( this.blind );
        }
        else if( mes.startsWith( "hold" ))
        {
            hold.convertmessage( mes );
            //LOG---------------------------------------
            log.log( mes );
            //-------------------------------------------
            round.holdmessageArrived( hold );
        }
        else if(mes.startsWith( "inquire" ))
        {
            System.out.println( mes );
            inquire.convertmessage( mes );
            round.inquiremessageArrived( inquire );
        }
        else if( mes.startsWith( "notify" ))
        {
            notify.convertmessage( mes );
            round.notifymessageArrived( notify );
        }
        else if( mes.startsWith( "flop" ))
        {
            flop.convertmessage( mes );
          //LOG---------------------------------------
            log.log( mes );
            //-------------------------------------------
            round.flopmessageArrived( flop );
        }
        else if ( mes.startsWith( "turn" )) 
        {
            turn.convertmessage( mes );
          //LOG---------------------------------------
            log.log( mes );
            //-------------------------------------------
            round.turnmessageArrived( turn );
        }
        else if( mes.startsWith( "river" ))
        {
            river.convertmessage( mes );
          //LOG---------------------------------------
            log.log( mes );
            //-------------------------------------------
            round.rivermessageArrived( river );
        }
        else if( mes.startsWith( "showdown" ))
        {
            showdown.convertmessage( mes );
            round.showdownmessageArrived( showdown );
            round = new Round( log, myID ,count,count2,count3);
        }
        else if( mes.startsWith( "pot-win" ))
        {
            
        }
    }

    @Override
    public void close()
    {
        this.log.log( "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++" );
        this.log.log( "shame:  " + String.valueOf( this.count.get() ) );
        this.log.log( "lose:  " + String.valueOf( this.count2.get() ) );
        this.log.log( "win:   " + String.valueOf( this.count3.get() ) );
    }
}
