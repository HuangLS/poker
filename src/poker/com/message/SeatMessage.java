package poker.com.message;

import java.util.HashMap;
import java.util.Map;

import poker.boots.Round;

public class SeatMessage
{
    
    private Map< String, Integer > pid2jetton = new HashMap<String,Integer>();
    private Map< String, Integer > pid2money = new HashMap<String,Integer>();
    private Map< Integer, String > oder2pid = new HashMap<Integer,String>();
    
    private String button;
    private String smallBlind;
    private String bigBlind;
    
    public void convertmessage( String mes )
    {
       String[] lines = mes.split( "\n" );
       {
           String[] s = lines[1].split( " " );
           oder2pid.put( 1, s[1] );
           pid2jetton.put( s[1], Integer.valueOf( s[2] ) );
           pid2money.put( s[1], Integer.valueOf( s[3] ) );
           button = s[1];
       }
       {
           String[] s = lines[2].split( " " );
           oder2pid.put( 2, s[2] );
           pid2jetton.put( s[2], Integer.valueOf( s[3] ) );
           pid2money.put( s[2], Integer.valueOf( s[4] ) );
           smallBlind = s[2];
       }
       for( int i = 3; i < lines.length - 1; i++ )
       {
           if( lines[i].startsWith( "big blind" ) )
           {
               String[] s = lines[i].split( " " );
               oder2pid.put( i, s[2] );
               pid2jetton.put( s[2], Integer.valueOf( s[3] ) );
               pid2money.put( s[2], Integer.valueOf( s[4] ) );
               bigBlind = s[2];
               continue;
           }
           String[] s = lines[i].split( " " );
           oder2pid.put( i, s[0] );
           pid2jetton.put( s[0], Integer.valueOf( s[1] ) );
           pid2money.put( s[0], Integer.valueOf( s[2] ) );
       }
    }
    
    public Map<String,Integer> getpid2jetton()
    {
        return this.pid2jetton;
    }
    
    public Map<String,Integer> getpid2money()
    {
        return this.pid2money;
    }
    public Map<Integer,String> getoder2pid()
    {
        return this.oder2pid;
    }

    public String getbutton()
    {
        return this.button;
    }

    public boolean issmallblind( String myID )
    {
        return this.smallBlind.equals( myID );
    }
}
