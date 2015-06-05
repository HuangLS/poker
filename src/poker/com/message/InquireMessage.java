package poker.com.message;

import java.util.HashMap;
import java.util.Map;

import poker.boots.RoundMeta;
import poker.log.Log;

public class InquireMessage
{
    
    int totalpot = -1;
    Map< String, Integer > pid2jetton = new HashMap<String,Integer>();
    Map< String, Integer > pid2money = new HashMap<String,Integer>();
    Map< String, Integer > pid2bet = new HashMap<String,Integer>();
    Map< String, String > pid2action = new HashMap<String,String>();
    Map< String, Integer > pid2oder = new HashMap<String,Integer>();
    Map< Integer, String > oder2pid = new HashMap<Integer,String>();
    
    public void convertmessage( String mes )
    {
        String[] lines = mes.split( "\n" );
        for( int i = 1; i < lines.length -1; i++ )
        {
            String[] s = lines[i].split( " " );
            if( lines[i].startsWith( "total pot:" ) )
            {
                this.totalpot = Integer.valueOf( s[2] );
            }
            else
            {
                this.pid2jetton.put( s[0], Integer.valueOf( s[1] ) );
                this.pid2money.put( s[0], Integer.valueOf( s[2] ) );
                this.pid2bet.put( s[0], Integer.valueOf( s[3] ) );
                this.pid2action.put( s[0], s[4] );
                this.pid2oder.put( s[0], i );
                this.oder2pid.put( i, s[0] );
            }
        }
    }


    
    public boolean getfirsttocall( String button )
    {
        Integer b = this.pid2oder.get( button );
        if( b == null )
            return false;
        if( b == 1)
            return true;
        else
        {
            for( int i = b; i >=1; i-- )
            {
                String action = pid2action.get( oder2pid.get( i ) );
                if(!action.equals( "check" )&&!action.equals( "fold" ))
                    return false;
            }
            return true;
        }
    }

    public boolean hasallin()
    {
        for(String s :pid2action.values() )
        {
            if( s.equals( "all_in" ) )
                return true;
        }
        return false;
    }

    public double getpotwin()
    {
        return (double)pid2bet.get( oder2pid.get( 1 ) )/(double)totalpot;
    }

    public int getmyjutton( String myID)
    {
        Integer tore = pid2jetton.get( myID );
        if( tore == null )
            return -1;
        else {
            return tore + pid2money.get( myID );
        }
    }

    public int getplayer( )
    {
        int toret = 0;
        for( String id : pid2action.keySet() )
        {
            if( pid2action.get( id ).equals( "fold" ) )
                toret ++;
        }
        return toret;
    }

    public int getallinnum()
    {
        int toret = -1;
        for( String id : pid2action.keySet() )
        {
            if( pid2action.get( id ).equals( "all_in" ));
            {
                int i = pid2bet.get( id );
                if( i>toret )
                    toret = i;
            }
                
        }
        return toret;
    }

    public int tocall( String myID, Log log, int mylastjet )
    {
        int i = 1;
        while(pid2action.get(oder2pid.get( i )).equals( "fold" ))
        {
            i++;
        }
        Integer num = pid2bet.get( myID );
        if( num == null || mylastjet == 0 )
            num = 0;
        log.log( String.valueOf( pid2bet.get( oder2pid.get( i ) ) ) + " - " + String.valueOf( num ));
        return pid2bet.get( oder2pid.get( i ) ) - num;
    }

    public int getraisenum( String button )
    {
        int toret = 0;
        Integer buttonseat = pid2oder.get( button );
        if( buttonseat !=null )
        {
            for( int i = buttonseat; i>=1; i-- )
            {
                String id = oder2pid.get( i );
                if( pid2action.get( id ).equals( "raise \n" ))
                    toret++;
            }
            return toret;
        }
        else
        {
            for( int i = 1; i <=oder2pid.size(); i++ )
            {
                String id = oder2pid.get( i );
                if( pid2action.get( id ).equals( "raise \n" ))
                    toret++;
            }
            return toret;
        }
    }



    public double getpotsize()
    {
        return (double)totalpot;
    }
    
}
