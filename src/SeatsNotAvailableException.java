

public class SeatsNotAvailableException extends Exception{
    
    public SeatsNotAvailableException(String message)
    {
        super(message);
    }

    public SeatsNotAvailableException()
    {
        super();
    }
}