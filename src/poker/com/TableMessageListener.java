package poker.com;

public interface TableMessageListener
{
    public void processmessage( String message, TableCom tableCom  );

    public void close();

}
