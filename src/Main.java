import java.util.*;
import java.io.*;

public class Main{

    private static int seatsPerRow = 20;
    private static int noOfRows = 10;

    public static void main(String[] args) throws IOException, SeatsNotAvailableException
    {
        System.out.println("Enter the testfile path");
        Scanner in = new Scanner(System.in);
        String path = in.next();
        File file = new File(path);
        SeatBooking seatObj = new SeatBooking(getTheaterLayout(), seatsPerRow, noOfRows);
        try{
            Scanner sc = new Scanner(file);
            while(sc.hasNextLine())
            {
                seatObj.parseInputFile(sc.nextLine());
            }
            sc.close();
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File not found in the given path! Please input correct path");
        }

        in.close();
        System.out.println(seatObj.createFile());
    }

    private static HashMap<Character, ArrayList<Integer>> getTheaterLayout()
    {
        HashMap<Character, ArrayList<Integer>> layout = new HashMap<>();
        char rowId = 'A';
        for(int i=0;i<noOfRows;i++)
        {
            layout.put(rowId, populateRows());
            rowId++;
        }
        return layout;
    }

    private static ArrayList<Integer> populateRows()
    {
        ArrayList<Integer> seats = new ArrayList<>();
        for(int i=0;i<seatsPerRow;i++)
        {
            seats.add((i+1));
        }
        return seats;
    }

}