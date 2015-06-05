package poker.com.message;

import java.util.HashMap;
import java.util.Map;

public class NotifyMessage
{
    int totalpot = -1;
    Map< String, Integer > pid2jetton = new HashMap<String,Integer>();
    Map< String, Integer > pid2money = new HashMap<String,Integer>();
    Map< String, Integer > pid2bet = new HashMap<String,Integer>();
    Map< String, String > pid2action = new HashMap<String,String>();

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
            }
        }
    }
}
