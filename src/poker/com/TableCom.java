package poker.com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import poker.log.Log;


public class TableCom
{
    private String tableIP;
    private int tablePort;
    private String myIP;
    private int myPort;
    private Socket outconnect;
    
    private static TableCom instence;
    
    public static TableCom instence( String ip , String port, String ip2, String port2,Log log )
    {
        if( instence == null )
            instence = new TableCom( ip, port, ip2, port2,log );
        return instence;
    }
    
    private TableCom(  String ip , String port, String ip2, String port2,Log log )
    {
        this.tableIP = ip;
        this.tablePort = Integer.valueOf( port );
        this.myIP = ip2;
        this.myPort = Integer.valueOf( port2 );
        this.log = log;
        init();
    }
    
    private void init()
    {
        try
        {
            outconnect = new Socket();
            System.out.println(  this.myIP + this.myPort  );
            outconnect.bind( new InetSocketAddress( InetAddress.getByName( this.myIP ), this.myPort ) );
            System.out.println(  this.tableIP + this.tablePort  );
            outconnect.connect( new InetSocketAddress( InetAddress.getByName( this.tableIP ), this.tablePort ), 0 );
        }
        catch ( UnknownHostException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch ( IOException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public OutputStream getOutputStream()
    {
        try
        {
            return this.outconnect.getOutputStream();
        }
        catch ( IOException e )
        {
            e.printStackTrace();
            return null;
        }
    }
    //LOG-------------------------------------
    private Log log;
    
    //END LOG---------------------------------
    
    public void startMoniterInput( TableMessageListener listener )
    {
        BufferedReader input = null;
        try
        {
            input = new BufferedReader( new InputStreamReader( this.outconnect.getInputStream()));
        }
        catch ( IOException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        while( !this.outconnect.isClosed() )
        {
            
            try
            {
                
                String line = input.readLine();
                /*/LOG--------------------------------
                log.log( line );
                *///-----------------------------------------
                if( line != null )
                {
                    if( line.startsWith( "seat/" ) )
                    {
                        StringBuilder builder = new StringBuilder();
                        builder.append( line + "\n" );
                        String nextline = input.readLine();
                        while( !nextline.startsWith( "/seat" ) )
                        {
                            builder.append( nextline + "\n" );
                            nextline = input.readLine();
                        }
                        builder.append( nextline );
                        listener.processmessage( builder.toString(), this );
                        continue;
                    }
                    else if( line.startsWith( "blind/" ))
                    {
                        StringBuilder builder = new StringBuilder();
                        builder.append( line + "\n" );
                        String nextline = input.readLine();
                        while( !nextline.startsWith( "/blind" ) )
                        {
                            builder.append( nextline + "\n" );
                            nextline = input.readLine();
                        }
                        builder.append( nextline + "\n" );
                        listener.processmessage( builder.toString(), this );
                        continue;
                    }
                    else if( line.startsWith( "hold/" ))
                    {
                        StringBuilder builder = new StringBuilder();
                        builder.append( line + "\n" );
                        String nextline = input.readLine();
                        while( !nextline.startsWith( "/hold" ) )
                        {
                            builder.append( nextline + "\n" );
                            nextline = input.readLine();
                        }
                        builder.append( nextline );
                        listener.processmessage( builder.toString(), this );
                        continue;
                    }
                    else if(line.startsWith( "inquire/" ))
                    {
                        StringBuilder builder = new StringBuilder();
                        builder.append( line + "\n" );
                        String nextline = input.readLine();
                        while( !nextline.startsWith( "/inquire" ) )
                        {
                            builder.append( nextline + "\n" );
                            nextline = input.readLine();
                        }
                        builder.append( nextline );
                        listener.processmessage( builder.toString(), this );
                        continue;
                    }
                    else if( line.startsWith( "notify/" ))
                    {
                        StringBuilder builder = new StringBuilder();
                        builder.append( line + "\n" );
                        String nextline = input.readLine();
                        while( !nextline.startsWith( "/notify" ) )
                        {
                            builder.append( nextline + "\n" );
                            nextline = input.readLine();
                        }
                        builder.append( nextline );
                        listener.processmessage( builder.toString(), this );
                        continue;
                    }
                    else if( line.startsWith( "flop/" ))
                    {
                        StringBuilder builder = new StringBuilder();
                        builder.append( line + "\n" );
                        String nextline = input.readLine();
                        while( !nextline.startsWith( "/flop" ) )
                        {
                            builder.append( nextline + "\n" );
                            nextline = input.readLine();
                        }
                        builder.append( nextline );
                        listener.processmessage( builder.toString(), this );
                        continue;
                    }
                    else if ( line.startsWith( "turn/" )) 
                    {
                        StringBuilder builder = new StringBuilder();
                        builder.append( line + "\n" );
                        String nextline = input.readLine();
                        while( !nextline.startsWith( "/turn" ) )
                        {
                            builder.append( nextline + "\n" );
                            nextline = input.readLine();
                        }
                        builder.append( nextline );
                        listener.processmessage( builder.toString(), this );
                        continue;
                    }
                    else if( line.startsWith( "river/" ))
                    {
                        StringBuilder builder = new StringBuilder();
                        builder.append( line + "\n" );
                        String nextline = input.readLine();
                        while( !nextline.startsWith( "/river" ) )
                        {
                            builder.append( nextline + "\n" );
                            nextline = input.readLine();
                        }
                        builder.append( nextline );
                        listener.processmessage( builder.toString(), this );
                        continue;
                    }
                    else if( line.startsWith( "showdown/" ))
                    {
                        StringBuilder builder = new StringBuilder();
                        builder.append( line + "\n" );
                        String nextline = input.readLine();
                        while( !nextline.startsWith( "/showdown" ) )
                        {
                            builder.append( nextline + "\n" );
                            nextline = input.readLine();
                        }
                        builder.append( nextline );
                        listener.processmessage( builder.toString(), this );
                        continue;
                    }
                    else if( line.startsWith( "pot-win/" ))
                    {
                        StringBuilder builder = new StringBuilder();
                        builder.append( line + "\n" );
                        String nextline = input.readLine();
                        while( !nextline.startsWith( "/pot-win" ) )
                        {
                            builder.append( nextline + "\n" );
                            nextline = input.readLine();
                        }
                        builder.append( nextline );
                        listener.processmessage( builder.toString(), this );
                        continue;
                    }
                    else if( line.startsWith( "game-over" ))
                    {
                        listener.close();
                        this.cloesListening();
                    }
                    
                }
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }
        }
    }
    
    public void cloesListening()
    {
        try
        {
            this.outconnect.close();
        }
        catch ( IOException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}
