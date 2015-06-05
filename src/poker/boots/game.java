package poker.boots;

import java.io.IOException;
import java.io.OutputStream;

import poker.com.MessageConverter;
import poker.com.TableCom;
import poker.log.Log;

public class game
{
    public static void main(String[] args)
    {
        String tableIP = args[0];
        String tablePort = args[1];
        String myIP = args[2];
        String myPort = args[3];
        String myID = args[4];
        
        log = new Log( myID );
        
        TableCom table = TableCom.instence( tableIP, tablePort, myIP, myPort,log );
        
        game game = new game( table, myID );
        game.register();
        game.gamestart( myID );
    }
    
    private TableCom table;
    private String myID;
    private static Log log;
    
    public game(  TableCom t, String i )
    {
        this.table = t;
        this.myID = i;
    }
    
    private void register()
    {
        OutputStream output = this.table.getOutputStream();
        String tore = "reg: " + this.myID + " hhx \n";
        byte[] tow = new byte[tore.length()];
        for( int i = 0; i < tow.length; i++ )
        {
            tow[i] = (byte)tore.charAt( i );
        }
        try
        {
            output.write( tow );
            output.flush();
        }
        catch ( IOException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    private void gamestart( String myID )
    {
        MessageConverter messageConverter = new MessageConverter( log, myID );
        this.table.startMoniterInput( messageConverter );
    }
    
    private void gameover()
    {
        this.table.cloesListening();
    }
}
