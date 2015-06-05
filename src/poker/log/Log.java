package poker.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Log
{
    private File file;
    private BufferedWriter writer;
    boolean loging = false;
    
    public Log( String name )
    {
        file = new File( name + ".txt" );
        
        try
        {
            file.createNewFile();
            writer = new BufferedWriter( new FileWriter( file ) );
        }
        catch ( IOException e )
        {
            loging = false;
            e.printStackTrace();
        }
    }
    
    public void log( String line )
    {
        if( loging )
        {
            try
            {
                writer.write( line + "\n" );
                writer.flush();
            }
            catch ( IOException e )
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    
    public void close()
    {
        try
        {
            this.writer.close();
        }
        catch ( IOException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
