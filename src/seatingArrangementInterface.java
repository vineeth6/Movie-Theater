import java.io.IOException;

public interface seatingArrangementInterface {
    void parseInputFile(String s) throws SeatsNotAvailableException;
    String createFile() throws IOException;
}
