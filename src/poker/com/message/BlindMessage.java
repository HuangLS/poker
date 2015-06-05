package poker.com.message;

public class BlindMessage
{

    private int bigBlind = -1;
    private int smallBlind = -1;
    private String bigBlindid;
    private String smallBlindid;
    
    public void convertmessage( String mes )
    {
        String[] lines = mes.split( "\n" );
        int[] num = new int[]{-1,-1};
        for( int i = 1; i < lines.length -1 ; i++ )
        {
            String[] s = lines[i].split( " " );
            num[i-1] = Integer.valueOf( s[1] );
        }
        if( num[1] != -1 )
        {
            bigBlind = num[0]>num[1]? num[0]:num[1];
            smallBlind = num[0]<num[1]? num[0]:num[1];
        }
        else
        {
            smallBlind = num[0];
        }
    }
    
    public int getBigblind()
    {
        return this.bigBlind;
    }
    
    public int getSmallblind()
    {
        return this.smallBlind;
    }
    
}
